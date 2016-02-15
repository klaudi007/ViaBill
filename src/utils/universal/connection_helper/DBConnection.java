package universal.connection_helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * @author Musa Aliyev
 */

public class DBConnection{
	
	private String url;
	private static DBConnection instance;

	private DBConnection()
	{
    	String driver = null;
		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			url = "jdbc:mysql://localhost/directory?user=root";
            ResourceBundle bundle = ResourceBundle.getBundle("databases");
            driver = bundle.getString("jdbc.driver");
            Class.forName(driver);
            url=bundle.getString("jdbc.url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			instance = new DBConnection();
			System.out.println(" Connection  - - - - - - - -  New DBConnection created");
		}
		try {
			return DriverManager.getConnection(instance.url);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public static void close(Connection connection)
	{
		try {
			if (connection != null) {
				connection.close();
				System.out.println("Connection Closed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}