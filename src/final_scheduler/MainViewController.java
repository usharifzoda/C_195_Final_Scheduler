/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
public class MainViewController implements Initializable {

    @FXML private Button addEditDeleteCustomerButton;
    @FXML private Button addEditDeleteAppointmentButton;
    @FXML private Button viewAppointmentsCalendarButton;
    @FXML private Button generateReportButton;

    public void addEditDeleteCustomerButtonPressed(ActionEvent event) throws IOException{

        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/AddEditDeleteCustomer.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
        
        
    }
    
    public void addEditDeleteAppointmentButtonPressed(ActionEvent event) throws IOException{
        System.out.println("Add Appt button Pressed");
        
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/appointmentDashboard.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
    
    public void viewAppointmentsCalendarButtonPressed(ActionEvent event) throws IOException{
        System.out.println("View Appts Pressed");
        
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/calendar.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
    
    public void generateReportButtonPressed(ActionEvent event){
        System.out.println("Generate Report Pressed");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
