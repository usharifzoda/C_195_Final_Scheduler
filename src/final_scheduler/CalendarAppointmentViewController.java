/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Classes.appointment;
import Databases.DBConnection;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.event.ChangeListener;

/**
 * FXML Controller class
 *
 * @author DG sahib
 */
public class CalendarAppointmentViewController implements Initializable 
{    
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
    public void initialize(URL url, ResourceBundle rb) {
       
        
    }    
    
    
    
    public void initCalendarAppointmentView(int year,int month,String day)
    {
        System.out.println(day);
        String m;
        if(month<10)
        {
            m="0"+Integer.toString(month);
        }
        else
            m=Integer.toString(month);
        
        try
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
        
            s=DBConnection.conn.createStatement();
            
            System.out.println("in view");
            System.out.println(year+"-"+m+"-"+day);
            String stmt="select c.customerName,a.title,a.description,a.location,a.contact,a.start,a.end,a.type from customer c, appointment a where c.customerId=a.customerId and start like '"+year+"-"+m+"-"+day+"%'";
            ResultSet r=s.executeQuery(stmt);
            while(r.next())
            {
                appList.add(new appointment(r.getString("c.customerName"),r.getString("a.title"),r.getString("a.description"),r.getString("a.location"),r.getString("a.contact"),r.getString("a.start"),r.getString("a.end"),r.getString("a.type")));
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
        
        System.out.println("inside back");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
        System.out.println("exiting close");
    }
    
}
