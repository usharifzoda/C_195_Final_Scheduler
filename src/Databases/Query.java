package Databases;

import java.sql.ResultSet;
import java.sql.Statement;
import static Databases.DBConnection.conn;

/**
 *
 * @author usharifzoda
 */
public class Query {
    
    private static String query;
    private static Statement stmt;
    private static ResultSet result;
    
    public static void makeQuery(String q){
        query = q;
        
        try {
            
            // Create Statement Object 
            stmt = conn.createStatement();
            
            // Determine Query Execution whether it is a SELECT or UPDATE
            if(query.toLowerCase().startsWith("select")){
                result = stmt.executeQuery(query);
            }if(query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update")){
                stmt.executeUpdate(query);
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public static ResultSet getResult(){
        return result;
    }
    
}
