/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import com.mysql.jdbc.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static final_scheduler.DBConnection.conn;
import java.sql.ResultSet;

/**
 *
 * @author usharifzoda
 */
public class Final_Scheduler extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            
            DBConnection.makeConnection();
            Statement stmt = (Statement) conn.createStatement();
            
            String sqlStatement = "SELECT * FROM employee_tbl";
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();
            
            while(result.next()){
                System.out.print(result.getInt("EmployeeID") + " | ");
                System.out.print(result.getString("EmployeeName") + " | ");
                System.out.print(result.getString("Department") + " | ");
                System.out.print(result.getDate("HireDate") + " ");
                System.out.println(result.getTime("HireDate") + "\n");
            }
            
            launch(args);
            DBConnection.closeConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        
    }
    
}
