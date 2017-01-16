package ml_project;

import java.sql.*;
import java.util.Vector;

public class Database {
	static String url;
	static String username;
	static String password;
	
	public static ResultSet executeQuery(String query) {
		ResultSet rs = null;
		try {
			// get a connection to database
			Connection conn = DriverManager.getConnection(url, username, password);
			// create a statement
			Statement stmt = conn.createStatement();
			// execute SQL query
			rs = stmt.executeQuery(query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void updateQuery(String query) {
		try {
			// get a connection to database
			Connection conn = DriverManager.getConnection(url, username, password);
			// create a statement
			Statement stmt = conn.createStatement();
			// execute SQL query
			stmt.executeUpdate(query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// this will be used to populate the JTable that is why the return type is Vector
	public static Vector<String> tableLabels(ResultSet rs) {
		int numOfColumns = numberOfColumns(rs);
		Vector<String> columnNames = new Vector<String>(numOfColumns);	
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= numOfColumns; i++)
				columnNames.add(metaData.getColumnLabel(i));
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
		return columnNames;
	}
	
	public static int numberOfColumns(ResultSet rs) {
		int columns = 0;
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			columns = metaData.getColumnCount();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return columns;
	}
	
	public static int numberOfRows(ResultSet rs) {
		int rows = 0;
		try {
			rs.last();
			rows = rs.getRow();
			rs.beforeFirst();	// reset the cursor
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}
	
	// this will be used to populate the JTable that is why the return type is Vector
	public static Vector<Vector<String>> tableContent(ResultSet rs) {
		int columns = numberOfColumns(rs);
		Vector<Vector<String>> table = new Vector<Vector<String>>();
		Vector<String> row = null;
		try {
			while (rs.next()) {
				row = new Vector<String>(columns);
				for (int i = 1; i <= columns; i++)
					row.add(rs.getString(i));
				table.add(row);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
}
