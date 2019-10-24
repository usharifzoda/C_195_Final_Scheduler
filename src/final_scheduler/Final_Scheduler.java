/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Databases.Query;
import Databases.DBConnection;
import com.mysql.jdbc.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static Databases.DBConnection.conn;
import java.sql.ResultSet;

/**
 * FXML Controller class
 *
 * @author usharifzoda
 */
public class Final_Scheduler extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLViews/FXMLDocument.fxml"));
        
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
            launch(args);
            DBConnection.closeConnection();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        
    }
    
}
