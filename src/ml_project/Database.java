package ml_project;

import java.sql.*;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.dbutils.DbUtils;

public class Database {

	// variables used for database connection
	public static String url;
	public static String username;
	public static String password;
	
	// variables used for queries
	private static Connection conn;
	private static PreparedStatement pstm;
	private static ResultSet  rs;
	
	/*
	 * Calculates the number of rows of the result set
	 */
	public static int numberOfRows(ResultSet rs) {
		int rows = 0;
		try {
			rs.last();
			rows = rs.getRow();
			rs.beforeFirst();	// reset the cursor
			rs.next();
			return rows;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	/*
	 * Calculates the number of columns of the result set
	 */
	public static int numberOfColumns(ResultSet rs) {
		int columns = 0;
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			columns = metaData.getColumnCount();
			return columns;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	/*
	 * Called for logging in
	 */
	public static boolean login(String user, String pass) {
		try {
			String query = "SELECT * FROM logins WHERE username = ? AND password = ?";
			conn = DriverManager.getConnection(url, username, password);
			pstm = conn.prepareStatement(query);
			pstm.setString(1, user);
			pstm.setString(2, pass);
			rs = pstm.executeQuery();
			
			boolean matchFound = (numberOfRows(rs) == 1) ? true : false;	
			return matchFound;
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, e);
			return false;
		} finally {
		    DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(pstm);
		    DbUtils.closeQuietly(conn);
		}
	}
	
	/*
	 * Fills the jtable with the entries of the table from the database.
	 * If label is not null and word is not null then in the query will be added "WHERE label = word"
	 * else all the entries of the table will be loaded 
	 */
	public static void fillJTable(JTable table, String label, String word) {
		try {
			String query = (label == null && word == null) ? "SELECT * FROM test_table1" : buildQuery(label, word);
			conn = DriverManager.getConnection(url, username, password);
			pstm = conn.prepareStatement(query);
			rs   = pstm.executeQuery();
			table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
		    DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(pstm);
		    DbUtils.closeQuietly(conn);
		}
	}
	
	/*
	 * Fills the jcombobox with the names of the table's columns
	 */
	public static void fillJComboBox(JComboBox<String> cbox) {
		try {
			conn = DriverManager.getConnection(url, username, password);
			pstm = conn.prepareStatement("SELECT * FROM test_table1");
			rs   = pstm.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int numOfColumns = metaData.getColumnCount();
			for (int i = 1; i <= numOfColumns; i++) {
				cbox.addItem(metaData.getColumnLabel(i));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
		    DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(pstm);
		    DbUtils.closeQuietly(conn);
		}
	}
	
	public static String buildQuery(String label, String word) {
		String query = String.format("SELECT * FROM test_table1 WHERE %s = '%s'", label, word);
		return query;
	}
	
	public static boolean addEntry(String plate, String cargo, String name, String surname, String location) {
		try {
			String query = "INSERT INTO test_table1 (plate_number, cargo, name, surname, location) VALUES (?, ?, ?, ?, ?)";
			conn = DriverManager.getConnection(url, username, password);
			pstm = conn.prepareStatement(query);
			pstm.setString(1, plate);
			pstm.setString(2, cargo);
			pstm.setString(3, name);
			pstm.setString(4, surname);
			pstm.setString(5, location);
			pstm.execute();
			return true;
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, e);
			return false;
		} finally {
		    DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(pstm);
		    DbUtils.closeQuietly(conn);
		}
	}
	
	public static boolean updateEntry(String inputID, String plate, String cargo, String name, String surname, String location) {
		try {
			String query = "UPDATE test_table1 SET plate_number = ?, cargo = ?, name = ?, surname = ?, location = ? WHERE id = ?";
			conn = DriverManager.getConnection(url, username, password);
			pstm = conn.prepareStatement(query);
			pstm.setString(1, plate);
			pstm.setString(2, cargo);
			pstm.setString(3, name);
			pstm.setString(4, surname);
			pstm.setString(5, location);
			pstm.setString(6, inputID);
			pstm.execute();
			return true;
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, e);
			return false;
		} finally {
		    DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(pstm);
		    DbUtils.closeQuietly(conn);
		}
	}
	
	public static boolean deleteEntry(String inputID) {
		try {
			String query = "DELETE FROM test_table1 WHERE id = ?";
			conn = DriverManager.getConnection(url, username, password);
			pstm = conn.prepareStatement(query);
			pstm.setString(1, inputID);
			pstm.execute();
			return true;
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, e);
			return false;
		} finally {
		    DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(pstm);
		    DbUtils.closeQuietly(conn);
		}
	}
	
	/*
	 * Fills the text fields of the tab with the values of the entry with id = input_id
	 */
	public static void fillTextfields(String inputID, JTextField plate, JTextField cargo, JTextField name, JTextField surname, JTextField location) {
		try {
			String query = buildQuery("id", inputID);
			conn = DriverManager.getConnection(url, username, password);
			pstm = conn.prepareStatement(query);
			rs   = pstm.executeQuery();
			if (numberOfRows(rs) == 1) {
				plate.setText(rs.getString(2));
				cargo.setText(rs.getString(3));
				name.setText(rs.getString(4));
				surname.setText(rs.getString(5));
				location.setText(rs.getString(6));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
		    DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(pstm);
		    DbUtils.closeQuietly(conn);
		}
	}	
}
