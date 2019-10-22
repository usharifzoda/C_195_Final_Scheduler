/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Databases.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    Statement s;
    public void addEditDeleteCustomerButtonPressed(ActionEvent event) throws IOException{

        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/AddEditDeleteCustomer.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
        
        
    }
    
    public void addEditDeleteAppointmentButtonPressed(ActionEvent event)throws IOException{
        System.out.println("Add Appt button Pressed");
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/appointmentDashboard.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
    
    public void viewAppointmentsCalendarButtonPressed(ActionEvent event)throws IOException{
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/calendar.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
    
    public void generateReportButtonPressed(ActionEvent event)throws IOException{
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/reportsWindow.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try
        {
            String timeNow = LocalTime.now().toString(); 
            String []timeNowParts=timeNow.split(":");
            System.out.println(timeNowParts[0]+":"+timeNowParts[1]);
            s=DBConnection.conn.createStatement();
            
            String stmt="select c.customerName, a.start from appointment a,customer c  where a.customerId=c.customerId and userId=1 and start like '"+timeNowParts[0]+":"+timeNowParts[1]+"'";
            ResultSet r=s.executeQuery(stmt);
            while(r.next())
            {
                if(isAppointmentTime(r.getString(2)))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Notification");
                    alert.setHeaderText("You have appointment in next 15 minutes with "+r.getString(1)+" at "+r.getString(2));
                    Optional<ButtonType> result = alert.showAndWait();
                }
                
            }
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }    
    
    public boolean isAppointmentTime(String time)
    {
       String timeNow = LocalTime.now().toString(); 
       String []timeNowParts=timeNow.split(":");
       String []timeParts=time.split(" ");
       int[] currentTime= new int[2];
       int[] DBTime= new int[2];
       currentTime[0]=Integer.parseInt(timeNowParts[0]);
       currentTime[1]=Integer.parseInt(timeNowParts[1]);
       DBTime[0]=Integer.parseInt(timeParts[0]);
       DBTime[1]=Integer.parseInt(timeParts[1]);
       
       if(currentTime[1]==DBTime[0])
       {
           if(DBTime[1]-currentTime[1]==15)
               return true;
       }
       else if(currentTime[1]==DBTime[0]+1)
       {
           if((60-currentTime[1])+DBTime[1]==15)
               return true; 
      }
       
        return false;
    }    
}
