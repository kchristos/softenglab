package ml_project;


public class AppLauncher {
	// data needed for the database connection
	private final static String DBURL      = "jdbc:mysql://localhost:3306/ergasia";
	private final static String DBUSERNAME = "ml_user";
	private final static String DBPASSWORD = "mlproject";
	
	
	public static void main(String[] args) {
		Database.url      = DBURL;
		Database.username = DBUSERNAME;
		Database.password = DBPASSWORD;
		
		// start app		
		LoginFrame.main();
		
	}

}
