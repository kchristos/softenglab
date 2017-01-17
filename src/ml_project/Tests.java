package ml_project;

import static org.junit.Assert.*;

import java.sql.*;
import org.junit.Test;

public class Tests {
	// data needed for the database connection
	private final static String DBURL      = "jdbc:mysql://localhost:3306/ergasia";
	private final static String DBUSERNAME = "ml_user";
	private final static String DBPASSWORD = "mlproject";

	public ResultSet simpleQuery(String query) {
		try {
			Connection conn = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
			PreparedStatement pstm = conn.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();
			return rs;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public void closeDbResourses(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testNunberOfRows() {
		ResultSet rs1 = simpleQuery("SELECT * FROM logins WHERE username = 'admin' AND password = 'admin'");
		assertEquals("FAILURE - should be 1", 1, Database.numberOfRows(rs1));
		closeDbResourses(rs1);
		
		ResultSet rs2 = simpleQuery("SELECT * FROM logins WHERE username = 'admin' AND password = 'user'");
		assertEquals("FAILURE - should be 0", 0, Database.numberOfRows(rs2));
		closeDbResourses(rs2);
		
		// test incorrect numberOfRows
		ResultSet rs3 = simpleQuery("bla blab bla");
		assertEquals("FAILURE - should be -1", -1, Database.numberOfRows(rs3));
		closeDbResourses(rs3);
	}
	
	@Test
	public void testNumberOfColumns() {
		ResultSet rs1 = simpleQuery("SELECT * FROM logins");
		assertEquals("FAILURE - should be 3", 3, Database.numberOfColumns(rs1));
		closeDbResourses(rs1);
		
		// test incorrect numberOfColumns
		ResultSet rs2 = simpleQuery("bla blab bla");
		assertEquals("FAILURE - should be -1", -1, Database.numberOfColumns(rs2));
		closeDbResourses(rs2);
	}
	
	@Test
	public void testLogin() {
		// test correct login
		Database.url      = DBURL;
		Database.username = DBUSERNAME;
		Database.password = DBPASSWORD;
		assertEquals("FAILURE - should be TRUE", true, Database.login("admin", "admin"));
		assertEquals("FAILURE - should be FALSE", false, Database.login("admin", "user"));
		
		// test incorrect login
		Database.password = "12345";		// give the wrong password
		assertEquals("FAILURE - should be FALSE", false, Database.login("admin", "admin"));		// unable to make connection
	}
	
	@Test
	public void testAddEntry() {
		Database.url      = DBURL;
		Database.username = DBUSERNAME;
		Database.password = DBPASSWORD;
		try {
			boolean addSuccessful = false;
		
			// test correct entry
			addSuccessful = Database.addEntry("testadd", "testadd", "testadd", "testadd", "testadd");
			ResultSet rs1 = simpleQuery("SELECT * FROM test_table1 ORDER BY id DESC LIMIT 0, 1");
			rs1.next();
			int entryID = rs1.getInt(1);
			closeDbResourses(rs1);
			assertEquals("FAILURE - the ID of the new entry should be bigger", true, addSuccessful);
			Database.deleteEntry(Integer.toString(entryID));		// undo the add
			
			//test incorrect entry
			Database.password = "12345";		// give the wrong password
			addSuccessful = Database.addEntry("testadd", "testadd", "testadd", "testadd", "testadd");
			assertEquals("FAILURE - the ID of the new entry should be bigger", false, addSuccessful);
		} catch (Exception e) {
			System.out.println(e);
			assertEquals("FAILURE - the ID of the new entry should be bigger", true, false);
		}
	}
	
	@Test
	public void testUpdateEntry() {
		Database.url      = DBURL;
		Database.username = DBUSERNAME;
		Database.password = DBPASSWORD;
		try {
			boolean updateSuccessful = false;
			Database.addEntry("test", "test", "test", "test", "test");		// this entry will be updated
			ResultSet rs1 = simpleQuery("SELECT * FROM test_table1 ORDER BY id DESC LIMIT 0, 1");
			rs1.next();
			int id = rs1.getInt(1);				// get id of the last entry
			
			// test correct update
			updateSuccessful = Database.updateEntry(Integer.toString(id), "testupdate", "testupdate", "testupdate", "testupdate", "testupdate");
			closeDbResourses(rs1);
			assertEquals("FAILURE - the name of the entry did not update", true, updateSuccessful);
			Database.deleteEntry(Integer.toString(id));		// undo the add
			
			// test incorrect update
			Database.password = "12345";		// give the wrong password
			ResultSet rs2 = simpleQuery(String.format("SELECT * FROM test_table1 WHERE id = %d", id));
			rs2.next();
			updateSuccessful = Database.updateEntry(Integer.toString(id), "testupdate", "testupdate", "testupdate", "testupdate", "testupdate");
			closeDbResourses(rs2);
			assertEquals("FAILURE - the name of the entry did not update", false, updateSuccessful);
		} catch (Exception e) {
			System.out.println(e);
			assertEquals("FAILURE - the name of the entry did not update", true, false);
		}
	}
	
	@Test
	public void testDeleteEntry() {
		Database.url      = DBURL;
		Database.username = DBUSERNAME;
		Database.password = DBPASSWORD;
		try {
			boolean deleteSuccessful = false;
			Database.addEntry("test", "test", "test", "test", "test");		// this entry will be deleted
			ResultSet rs1 = simpleQuery("SELECT * FROM test_table1 ORDER BY id DESC LIMIT 0, 1");
			rs1.next();
			int id = rs1.getInt(1);				// get id of the last entry
			// test correct delete
			deleteSuccessful = Database.deleteEntry(Integer.toString(id));
			closeDbResourses(rs1);
			assertEquals("FAILURE - the entry was not deleted", true, deleteSuccessful);
			
			// test incorrect delete
			Database.password = "12345";		// give the wrong password
			ResultSet rs2 = simpleQuery(String.format("SELECT * FROM test_table1 WHERE id = %d", id));
			deleteSuccessful = Database.deleteEntry(Integer.toString(id));
			closeDbResourses(rs2);
			assertEquals("FAILURE - the entry was not deleted", false, deleteSuccessful);
		} catch (Exception e) {
			System.out.println(e);
			assertEquals("FAILURE - the entry was not deleted", false, true);
		}
	}
	
}
