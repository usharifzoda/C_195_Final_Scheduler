/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Classes.appointment;
import Databases.DBConnection;
import Databases.Query;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author usharifzoda
 */
public class AppointmentDashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    // GUI variables
    
    @FXML TableView<appointment> appointmentTable;
    @FXML TableColumn<appointment, String> appointmentCName;
    @FXML TableColumn<appointment, String> appointmentTitle;
    @FXML TableColumn<appointment, String> appointmentDes;
    @FXML TableColumn<appointment, String> appointmentLoc;
    @FXML TableColumn<appointment, String> appointmentCon;
    @FXML TableColumn<appointment, String> appointmentStart;
    @FXML TableColumn<appointment, String> appointmentEnd;
    @FXML TableColumn<appointment, String> appointmentType;
    ObservableList<appointment> appList;
    
    private Statement s;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    { 
        appointmentCName.setCellValueFactory(new PropertyValueFactory<appointment, String>("name"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<appointment, String>("title"));
        appointmentDes.setCellValueFactory(new PropertyValueFactory<appointment, String>("description"));
        appointmentLoc.setCellValueFactory(new PropertyValueFactory<appointment, String>("location"));
        appointmentCon.setCellValueFactory(new PropertyValueFactory<appointment, String>("contact"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<appointment, String>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<appointment, String>("end"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<appointment, String>("type"));
        
        appList=FXCollections.observableArrayList();
        try
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            s=DBConnection.conn.createStatement();
            String stmt="select c.customerName,a.title,a.description,a.location,a.contact,a.start,a.end,a.type from customer c, appointment a where c.customerId=a.customerId";
            ResultSet r=s.executeQuery(stmt);
            while(r.next())
            {
                String StartTime= r.getString("a.start");
                String EndTime= r.getString("a.end");
                
                StartTime = StartTime.substring(0,19);
                EndTime = EndTime.substring(0,19);
                
                LocalDateTime startLocalDateTime=LocalDateTime.parse(StartTime,formatter);
                ZonedDateTime startUTCTime=ZonedDateTime.of(startLocalDateTime, ZoneId.of("UTC"));
                ZonedDateTime startZonedTime = startUTCTime.withZoneSameInstant(ZoneId.systemDefault());
                String STARTTIME=startZonedTime.toString();
                STARTTIME=STARTTIME.replace('T', ' ');
                
                
                
                LocalDateTime endLocalDateTime=LocalDateTime.parse(EndTime,formatter);
                ZonedDateTime endUTCTime=ZonedDateTime.of(endLocalDateTime, ZoneId.of("UTC"));
                ZonedDateTime endZonedTime = endUTCTime.withZoneSameInstant(ZoneId.systemDefault());
                String ENDTIME=endZonedTime.toString();
                ENDTIME=ENDTIME.replace('T', ' ');
                
                
                appList.add(new appointment(r.getString("c.customerName"),r.getString("a.title"),r.getString("a.description"),r.getString("a.location"),r.getString("a.contact"),STARTTIME,ENDTIME,r.getString("a.type")));
            }
            appointmentTable.setItems(appList);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
   
    public void addAppointment(ActionEvent event)throws IOException
    {
        
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/addAppointment.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
    
    public void deleteAppointment(ActionEvent e)throws IOException
    {
         System.out.println("Delete Button has been pressed...");
        
        if(appointmentTable.getSelectionModel().getSelectedItem() == null){
            System.out.println("Please select a customer to Delete...");
            
        }else {
            boolean answer = confirmDelete();
            
            if(answer)
            {
                appointment selectAppointment;
                ObservableList<appointment> allAppointments;
                allAppointments = appointmentTable.getItems();
                selectAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                
                System.out.println("Customer to be Deleted: " + selectAppointment.getTitle() + " " + selectAppointment.getName());
                int appointmentID=-1;
                try
                {
                    Statement S=DBConnection.conn.createStatement();
                    String query="select customerId from customer where customerName='"+selectAppointment.getName()+"'";
                    ResultSet r=S.executeQuery(query);
                    int cid=-1;
                    while(r.next())
                        cid=r.getInt(1);
                    
                    
                    String localTimeString=(selectAppointment.getStart()).substring(0, 16);
                    localTimeString=localTimeString+":00";
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    LocalDateTime LDateTime = LocalDateTime.parse(localTimeString, formatter);
                    

                    ZonedDateTime startZonedDateAndTime=ZonedDateTime.of(LDateTime, ZoneId.systemDefault());
                    String startUTCTime = startZonedDateAndTime.withZoneSameInstant(ZoneOffset.UTC).toString();
                     
//                    ZonedDateTime startZonedDateAndTime=ZonedDateTime.of(sDateTime, ZoneId.of(zoneOfAppointment));
//                    String startUTCTime = startZonedDateAndTime.withZoneSameInstant(ZoneOffset.UTC).toString();
                    String stmt="select appointmentId from appointment where customerId="+cid+" && start='"+startUTCTime+"'";
                    
                    r=S.executeQuery(stmt);

                    while(r.next())
                        appointmentID=r.getInt(1);

                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                String stmt = "delete from appointment where appointmentId =" + appointmentID;
                
                Query.makeQuery(stmt);
                // Load the tableView again after Query to make refresh
                Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/appointmentDashboard.fxml"));
                Scene mainViewScene = new Scene(mainViewParent);
        
                Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
                window.setScene(mainViewScene);
                window.show(); 
            }   
                
        }
            
    }
    
    
    public void editAppointment(ActionEvent event) throws IOException
    {
        if(appointmentTable.getSelectionModel().getSelectedItem() == null ){
            System.out.println("Please select a customer to Edit");
            
        }else {
            System.out.println("Edit Customer button pressed...");
        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXMLViews/editAppointment.fxml"));
            Parent editAppointmentView = loader.load();
            
            Scene editAppointmentScene = new Scene(editAppointmentView);
            EditAppointmentController controller = loader.getController();
            controller.initEditAppointment(appointmentTable.getSelectionModel().getSelectedItem(), 
                                        appointmentTable.getSelectionModel().getSelectedIndex());
            
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(editAppointmentScene);
            window.show();
            
        }
    }
    
    
    
    public static boolean confirmDelete()
    {
        boolean deleteAnswer = true;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure you want to DELETE?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            return deleteAnswer;
        }

        return false;
    }    

}
