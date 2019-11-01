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
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author usharifzoda
 */
public class AddAppointmentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public ComboBox customerName;
    @FXML
    public ComboBox type;
    @FXML
    public DatePicker appointmentDate;
    @FXML
    public TextField startTime;
    @FXML
    public TextField endTime;
    @FXML
    public TextField url;
    @FXML
    public TextField title;
    @FXML
    public TextField contact;
    @FXML
    public TextArea description;
    
    @FXML
    public ComboBox TimeZone;
    @FXML
    public Label errorCustomerName;
    @FXML
    public Label errorAppointmentDate;
    @FXML
    public Label errorStartTime;
    @FXML
    public Label errorEndTime;
    @FXML
    public Label errorUrl;
    @FXML
    public Label errorTitle;
    @FXML
    public Label errorContact;
    @FXML
    public Label errorDescription;
    @FXML
    public Label errorType;
    
    @FXML
    public Label errorTimeZone;
    Statement s;
    private ObservableList<String> cnameList;
    
    
    String startT;
    String endT;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        type.setItems(FXCollections.observableArrayList("Introduction Checkup","Brief Checkup","Full Checkup"));
        TimeZone.setItems(FXCollections.observableArrayList("America/Phoenix","America/New_York","Europe/London"));
        cnameList=FXCollections.observableArrayList();
        String stmt ="select customerName from customer";
        try
        {
            s=DBConnection.conn.createStatement();
            ResultSet r=s.executeQuery(stmt);
            while(r.next())
            {
                cnameList.add(r.getString(1));
                
            }
            
            customerName.setItems(cnameList);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        
        
        
    }    
    
    @FXML 
    public void addAppointment()
    {
      
        setLabelsEmpty();
        if(customerName.getSelectionModel().isEmpty()||appointmentDate.getValue()==null||startTime.getText().isEmpty()||
                endTime.getText().isEmpty()||url.getText().isEmpty()||title.getText().isEmpty()||contact.getText().isEmpty()||
                description.getText().isEmpty()||type.getSelectionModel().isEmpty()||TimeZone.getSelectionModel().isEmpty())
        {
            
            System.out.println("errro");
            if(type.getSelectionModel().isEmpty())
                errorType.setText("Select appoint type");
            if(customerName.getSelectionModel().isEmpty())
                errorCustomerName.setText("Customer Name cannot be empty");
            if(appointmentDate.getValue()==null)
                errorAppointmentDate.setText("Select Date");
            if(startTime.getText().isEmpty())
                errorStartTime.setText("Start Time Cannot be empty");
            if(endTime.getText().isEmpty())
                errorEndTime.setText("End Time Cannot be empty");
            if(url.getText().isEmpty())
                errorUrl.setText("URL Cannot be empty");
            if(title.getText().isEmpty())
                errorTitle.setText("Title Cannot be empty");
            if(contact.getText().isEmpty())
                errorContact.setText("Contact Cannot be empty");
            if(description.getText().isEmpty())
                errorDescription.setText("Description Cannot be empty");
            if(TimeZone.getSelectionModel().isEmpty())
                errorTimeZone.setText("Select Location");

        }
        else
        {
            String dateStart=appointmentDate.getValue().toString();
            String startDateTime=dateStart+" "+startTime.getText()+":00";
            String endDateTime=dateStart+" "+endTime.getText()+":00";
            String zoneOfAppointment=TimeZone.getSelectionModel().getSelectedItem().toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime sDateTime = LocalDateTime.parse(startDateTime, formatter);
            LocalDateTime eDateTime = LocalDateTime.parse(endDateTime, formatter);

            ZonedDateTime startZonedDateAndTime=ZonedDateTime.of(sDateTime, ZoneId.of(zoneOfAppointment));
            String startUTCTime = startZonedDateAndTime.withZoneSameInstant(ZoneOffset.UTC).toString();
            startUTCTime=startUTCTime.substring(0, startUTCTime.length()-1);
            startUTCTime+=":00";
            startUTCTime=startUTCTime.replace('T', ' ');

            System.out.println("startUTCTime");
            System.out.println(startUTCTime);

            ZonedDateTime endZonedDateAndTime=ZonedDateTime.of(eDateTime, ZoneId.of(zoneOfAppointment));
            String endUTCTime = endZonedDateAndTime.withZoneSameInstant(ZoneOffset.UTC).toString();
            endUTCTime=endUTCTime.substring(0, endUTCTime.length()-1);
            endUTCTime+=":00";
            endUTCTime=endUTCTime.replace('T', ' ');

            System.out.println("end UTCTime");
            System.out.println(endUTCTime);
            startT=startUTCTime;
            endT=endUTCTime;
                            
            if(checkTime(startTime.getText().toString())&&checkTime(endTime.getText().toString()))
            {
                if(checkTimeHours(startTime.getText().toString())&&checkTimeHours(endTime.getText().toString())&&checkTimeMin(startTime.getText().toString())&&checkTimeMin(endTime.getText().toString()))
                {
                    if(checkTimeAvailability())
                    {
                        errorStartTime.setText("Time Slot Not Available");
                        errorEndTime.setText("Time Slot Not Available");
                    }
                    else
                    {   
                        int cid=-1;
                        String customerQuery="select customerId from customer where customerName ='"+customerName.getSelectionModel().getSelectedItem().toString()+"'";
                        try
                        {
                            ResultSet res=s.executeQuery(customerQuery);
                            while(res.next())
                            {
                                cid=res.getInt(1);
                            }
                            
                            //user id
                            String stmt="insert into appointment values(null,"+cid+",1,'"+title.getText()+"','"+description.getText()+"','"+
                                TimeZone.getSelectionModel().getSelectedItem().toString()+"','"+contact.getText()+"','"+type.getSelectionModel().getSelectedItem().toString()+"','"+url.getText()+"','"+startUTCTime+"','"+endUTCTime+"',CURDATE(),CURRENT_USER(),now(),CURRENT_USER())";
                            //s.executeQuery(stmt);
                            s.executeUpdate(stmt);
                            setLabelsEmpty();
                            setFieldsEmpty();
                            System.out.println("Added");
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    if(!checkTimeMin(startTime.getText().toString()))
                        errorStartTime.setText("invalid Mins");
                    if(!checkTimeMin(endTime.getText().toString()))
                        errorEndTime.setText("invalid Mins");
                    if(!checkTimeHours(startTime.getText().toString()))
                        errorStartTime.setText("Time Out of working Hours");
                    if(!checkTimeHours(endTime.getText().toString()))
                        errorEndTime.setText("Time Out of working Hours");
                    
                }
                
            }
            else
            {
                System.out.println("error inside");
                if(!checkTime(startTime.getText().toString()))
                    errorStartTime.setText("invalid Time Format");
                if(!checkTime(endTime.getText().toString()))
                    errorEndTime.setText("invalid Time Format");
                
            }
        }
    }
    
    public boolean checkTimeAvailability()
    {
        boolean FOUND=false;
       
        String startT=startTime.getText().toString();
        String endT=endTime.getText().toString();
//        String st=
//        String et=
        String sTime=startTime.getText();//ST.s
        String []startTimeParts=sTime.split(":");
        String eTime=endTime.getText();//ST.e
        String []endTimeParts=eTime.split(":");
        LocalDateTime [][]times;
        int total=0;
        try
        {
            String stmt="select start,end from appointment where start like '"+appointmentDate.getValue().toString()+"%'";
            ResultSet res=s.executeQuery(stmt);
            res.afterLast();
            res.last();
            total = res.getRow();
            res.beforeFirst();
            times=new LocalDateTime[total][2];
            int i=0;
            while(res.next())
            {
               times[i][0]=res.getTimestamp(1).toLocalDateTime();
               times[i][1]=res.getTimestamp(2).toLocalDateTime();
               i++;
            }
            boolean empty=false;
           
            for(int j=0;j<i;j++)
            {
                String time=times[j][0].toString();
                String []t1=time.split("T");
                String []t1Parts=t1[1].split(":");//ET.s
                time=times[j][1].toString();
                String []t2=time.split("T");
                String []t2Parts=t2[1].split(":");
                if(i==0)
                    empty=true;
                if(empty&&(Integer.parseInt(startTimeParts[0])>=9&&Integer.parseInt(startTimeParts[0])<=23))
                {
                    FOUND=false;
                } 
                else if(Integer.parseInt(startTimeParts[0])<9||Integer.parseInt(startTimeParts[0])>23)
                {
                    errorStartTime.setText("Time Out of Office Hours");
                
                    errorEndTime.setText("Time Out of Office Hours");
                    System.out.println("insde Time Out of Office Hours");
                    
                    FOUND=true;
                    break;
                }
                if(Integer.parseInt(startTimeParts[0])>=9&&Integer.parseInt(startTimeParts[0])<=23)
                {
                        //09:00-10:00  09:00   10:00 
                    if((Integer.parseInt(startTimeParts[0])==Integer.parseInt(t1Parts[0])&&Integer.parseInt(startTimeParts[1])>=Integer.parseInt(t1Parts[1]))&&
                        ((Integer.parseInt(endTimeParts[0])==Integer.parseInt(t2Parts[0])&&Integer.parseInt(endTimeParts[1])<=Integer.parseInt(t2Parts[1])))||(Integer.parseInt(endTimeParts[0])==Integer.parseInt(t1Parts[0])&&Integer.parseInt(endTimeParts[1])<=59))
                    {
                        FOUND=true;
                        break;
                    }        
                    else if((Integer.parseInt(startTimeParts[0])==Integer.parseInt(t1Parts[0])&&Integer.parseInt(startTimeParts[1])>=Integer.parseInt(t1Parts[1]))&&
                            ((Integer.parseInt(startTimeParts[0])==Integer.parseInt(t1Parts[0]) &&Integer.parseInt(startTimeParts[1])<=59)||
                            (Integer.parseInt(startTimeParts[0])==Integer.parseInt(t2Parts[0]) &&Integer.parseInt(startTimeParts[1])<=Integer.parseInt(t2Parts[1]))
                            ))
                    {

                        System.out.println("insde 2222");
                        FOUND=true;
                        break;
                    }
                    else if ((Integer.parseInt(endTimeParts[0])==Integer.parseInt(t1Parts[0])&&Integer.parseInt(endTimeParts[1])>=Integer.parseInt(t1Parts[1]))&&
                            ((Integer.parseInt(endTimeParts[0])==Integer.parseInt(t1Parts[0]) &&Integer.parseInt(endTimeParts[1])<=59)||
                            (Integer.parseInt(endTimeParts[0])==Integer.parseInt(t2Parts[0]) &&Integer.parseInt(endTimeParts[1])<=Integer.parseInt(t2Parts[1]))
                            ))
                    {
                        System.out.println("insde 333333333");
                        FOUND=true;
                        break;
                    }
    //                   
                }
                
                
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
         System.out.flush();
        return FOUND;        
    }
    
    public boolean checkTime(String s)
    {
        if(s.length()==5)
        {
            if((s.charAt(0)>='0'&&s.charAt(0)<='9')&&(s.charAt(1)>='0'&&s.charAt(1)<='9')&&(s.charAt(2)==':')&&
                    (s.charAt(3)>='0'&&s.charAt(3)<='9')&&(s.charAt(4)>='0'&&s.charAt(4)<='9'))        
                return true;
            else 
                return false;
        }
        else 
            return false;
    }
    public boolean checkTimeMin(String s)
    {
        String []timeParts=s.split(":");
        
        if(Integer.parseInt(timeParts[1])>=0&&Integer.parseInt(timeParts[1])<=59)
        {
            return true;
        }
        else return false;
    }
    
    public void setLabelsEmpty()
    {
        errorCustomerName.setText("");
        errorAppointmentDate.setText("");
        errorStartTime.setText("");
        errorEndTime.setText("");
        errorUrl.setText("");
        errorTitle.setText("");
        errorContact.setText("");
        errorDescription.setText("");
        errorType.setText("");
        errorTimeZone.setText("");
    }
    
    
    public void setFieldsEmpty()
    {
        customerName.getSelectionModel().clearSelection();
        type.getSelectionModel().clearSelection();
        appointmentDate.setValue(null);
        startTime.clear();
        endTime.clear();
        url.clear();
        title.clear();
        contact.clear();
        description.clear();
        TimeZone.getSelectionModel().clearSelection();
    }
    public boolean checkTimeHours(String time)
    {
        String[] timeParts=time.split(":");
        if(Integer.parseInt(timeParts[0])<9||Integer.parseInt(timeParts[0])>23)
            return false;
        else 
            return true;
    }
    @FXML
    public void cancel(ActionEvent event)throws IOException
    {
        System.out.println("Add Appt button Pressed");
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/appointmentDashboard.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
}
