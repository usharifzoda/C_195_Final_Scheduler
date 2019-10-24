/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author usharifzoda
 */

public class ReportsWindowController implements Initializable 
{

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
        
    }    
    
    
    
    
    @FXML
    public void generateAppointmentReport(ActionEvent event)  throws IOException
    {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/appointmentReport.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
    
    
    
    @FXML
    public void generateConsultantScheduleReport(ActionEvent event) throws IOException
    {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/consultantReport.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
    
    
    @FXML
    public void generateCustomerRegisteredReport(ActionEvent event) throws IOException
    {
        
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/customerRegistered.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
    
    @FXML
    public void generateLogFile(ActionEvent event) throws IOException
    {
        
//        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/logFileWindow.fxml"));
//        Scene mainViewScene = new Scene(mainViewParent);
//        
//        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
//        window.setScene(mainViewScene);
//        window.show(); 
        
        File file = new File("log.txt");
        if(file.exists()) {
            if(Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    System.out.println("Error Opening Log File: " + e.getMessage());
                }
            }
        }
        
    }
    
    @FXML
    public void back(ActionEvent event)throws IOException
    {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/MainView.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
    
}
