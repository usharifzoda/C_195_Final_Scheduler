package final_scheduler;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author usharifzoda
 */
public class DBConnection {

    private static final String databaseName = "U05yye";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + databaseName;
    private static final String username = "U05yye";
    private static final String password = "53688647379";
    private static final String driver = "com.mysql.jdbc.Driver";
    
    static Connection conn;
    
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception{
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connection Successfull!");
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception{
        conn.close();
        System.out.println("Connection Closed");
    }
    
}
