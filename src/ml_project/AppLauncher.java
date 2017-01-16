package ml_project;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;


public class AppLauncher {

	public static void main(String[] args) {
		// data needed for the database connection should be declared before the mainframe creation
		String url      = "jdbc:mysql://localhost:3306/ergasia";
		String username = "ml_user";
		String password = "mlproject";
		
		Database.url = url;
		Database.username = username;
		Database.password = password;
		
		// generate the main frame of the application
		MainFrame mainframe = new MainFrame("Trucks trackling app");		
		
	}

}
