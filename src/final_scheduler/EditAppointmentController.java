/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Classes.appointment;
import Databases.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
public class EditAppointmentController implements Initializable 
{

    /**
     * Initializes the controller class.
     */
    Statement s;
    @FXML
    private TextField editCName;
    @FXML
    private TextField editTitle;
    
    @FXML
    private TextField editContact;
    @FXML
    private TextField editStartTime;
    @FXML
    private TextField editEndTime;
    @FXML
    private TextArea editDesc;
    @FXML
    private DatePicker editAppointmentDate;
    @FXML
    private ComboBox editAppointmentType;
    @FXML
    public ComboBox TimeZone;
    int appointmentID;
    @FXML
    private Label errorTitle;
    
    @FXML
    private Label errorContact;
    @FXML
    private Label errorStartTime;
    @FXML
    private Label errorEndTime;
    @FXML
    private Label errorDesc;
    @FXML
    private Label errorType;
    @FXML
    private Label errorDate;
    @FXML
    public Label errorTimeZone;
    String date;
    ObservableList<String> typeList;
    String intialTime="";
    String finalTime="";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    public void initEditAppointment(appointment app, int index)
    {
        editCName.setText(app.getName());
        editTitle.setText(app.getTitle());
        TimeZone.getSelectionModel().select(app.getLocation());
        editContact.setText(app.getContact());
        
        String st=app.getStart();
        String et=app.getEnd();
        
        //getting substring
        
        st=st.substring(0, 16);
        et=et.substring(0, 16);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startLocalDateTime=LocalDateTime.parse(st,formatter);
        LocalDateTime endLocalDateTime=LocalDateTime.parse(et,formatter);
        
        ZonedDateTime startDefaultZonedTime=ZonedDateTime.of(startLocalDateTime, ZoneId.systemDefault());
        ZonedDateTime endDefaultZonedTime=ZonedDateTime.of(endLocalDateTime, ZoneId.systemDefault());
        
        
        ZonedDateTime startUTCTime = startDefaultZonedTime.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTCTime = endDefaultZonedTime.withZoneSameInstant(ZoneId.of("UTC"));
         
        
        ZonedDateTime startZonedTime = startUTCTime.withZoneSameInstant(ZoneId.of(app.getLocation()));
        ZonedDateTime endZonedTime = endUTCTime.withZoneSameInstant(ZoneId.of(app.getLocation()));
        
        
        String sstt=startZonedTime.toString();
        String eett=endZonedTime.toString();
        
        
        sstt=sstt.substring(0, 16);
        eett=eett.substring(0, 16);
        
        String startTimeParts[]=sstt.split("T");
        String endTimeParts[]=eett.split("T");
        
        editStartTime.setText(startTimeParts[1]);
        editEndTime.setText(endTimeParts[1]);
        
        intialTime=startTimeParts[1];
        finalTime=endTimeParts[1];
        
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate DATE=LocalDate.parse(startTimeParts[0],format1);
        
        editAppointmentDate.setValue(DATE);
        editDesc.setText(app.getDescription());
        
        
        typeList=FXCollections.observableArrayList("Introduction Checkup","Brief Checkup","Full Checkup");
        
        TimeZone.setItems(FXCollections.observableArrayList("America/Phoenix","America/New_York","Europe/London"));
        
        
        editAppointmentType.setItems(typeList);
        editAppointmentType.getSelectionModel().select(app.getType());
        
        try
        {
            Statement S=DBConnection.conn.createStatement();
            
            String query="select customerId from customer where customerName='"+editCName.getText()+"'";
            
            ResultSet r=S.executeQuery(query);
            int cid=-1;
            while(r.next())
                cid=r.getInt(1);
            String stmt="select appointmentId from appointment where customerId="+cid+" && start = '"+DATE+" "+startTimeParts[1]+":00'";
            
            r=S.executeQuery(stmt);
            
            while(r.next())
                appointmentID=r.getInt(1);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        editAppointmentType.setItems(FXCollections.observableArrayList("Introduction Checkup","Brief Checkup","Full Checkup"));
        
    }
   
    @FXML
    public void updateAppointment(ActionEvent event)
    {
        resetLabels();
        if(editTitle.getText().isEmpty()||TimeZone.getSelectionModel().isEmpty()||editContact.getText().isEmpty()||editStartTime.getText().isEmpty()||editEndTime.getText().isEmpty()||editDesc.getText().isEmpty()||editAppointmentType.getSelectionModel().isEmpty())
        {
            if(editTitle.getText().isEmpty())
                errorTitle.setText("Title Cannot be empty");
            if(editContact.getText().isEmpty())
                errorContact.setText("Contact Cannot be empty"); 
            if(TimeZone.getSelectionModel().isEmpty())
                errorTimeZone.setText("Location Cannot be empty");
            if(editStartTime.getText().isEmpty())
                errorStartTime.setText("Start Time Cannot be empty");
            if(editEndTime.getText().isEmpty())
                errorEndTime.setText("End Time Cannot be empty");
            if(editDesc.getText().isEmpty())
                errorDesc.setText("Description Time Cannot be empty");
            if(editAppointmentType.getSelectionModel().isEmpty())
                errorType.setText("Select Type");
            
        }
        else
        {
            
            if(intialTime.equals(editStartTime.getText())&&finalTime.equals(editEndTime.getText()))
            {
                try
                {
                    Statement s=DBConnection.conn.createStatement();
                    String stmt="update appointment set title='"+editTitle.getText()+"', location='"+TimeZone.getSelectionModel().getSelectedItem().toString()+"',contact='"+editContact.getText()+"',type='"+editAppointmentType.getSelectionModel().getSelectedItem().toString()+"' where appointmentId="+appointmentID;
                    s.executeUpdate(stmt);
                    System.out.print("update done");
                    
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            
            }
            else 
            {
                String startDateTime=editAppointmentDate.getValue().toString()+" "+editStartTime.getText()+":00";
                System.out.println(startDateTime);
                String endDateTime=editAppointmentDate.getValue().toString()+" "+editEndTime.getText()+":00";
                System.out.println(endDateTime);
                String zoneOfAppointment=TimeZone.getSelectionModel().getSelectedItem().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                LocalDateTime sDateTime = LocalDateTime.parse(startDateTime, formatter);
                LocalDateTime eDateTime = LocalDateTime.parse(endDateTime, formatter);

                ZonedDateTime startZonedDateAndTime=ZonedDateTime.of(sDateTime, ZoneId.of(zoneOfAppointment));
                String startUTCTime = startZonedDateAndTime.withZoneSameInstant(ZoneOffset.UTC).toString();
                startUTCTime=startUTCTime.substring(0, startUTCTime.length()-1);
                startUTCTime+=":00";
                startUTCTime=startUTCTime.replace('T', ' ');

                System.out.println(startUTCTime);

                ZonedDateTime endZonedDateAndTime=ZonedDateTime.of(eDateTime, ZoneId.of(zoneOfAppointment));
                String endUTCTime = endZonedDateAndTime.withZoneSameInstant(ZoneOffset.UTC).toString();
                endUTCTime=endUTCTime.substring(0, endUTCTime.length()-1);
                endUTCTime+=":00";
                endUTCTime=endUTCTime.replace('T', ' ');
                
                
                if(checkTime(editStartTime.getText().toString())&&checkTime(editEndTime.getText().toString()))
                {
                    if(checkTimeHours(editStartTime.getText().toString())&&checkTimeHours(editEndTime.getText().toString())&&checkTimeMin(editStartTime.getText().toString())&&checkTimeMin(editEndTime.getText().toString()))
                    {
                        if(checkTimeAvailability())
                        {
                            System.out.println("already exist in the table");
                            errorStartTime.setText("time slot not available");
                            errorEndTime.setText("time slot not available");
                        }
                        else
                        { 

                            try
                            {
                                Statement s=DBConnection.conn.createStatement();
                                String stmt="update appointment set title='"+editTitle.getText()+"', location='"+TimeZone.getSelectionModel().getSelectedItem().toString()+"',contact='"+editContact.getText()+"',type='"+editAppointmentType.getSelectionModel().getSelectedItem().toString()+"',start='"+startUTCTime+"', end='"+endUTCTime+"' where appointmentId="+appointmentID;
                                s.executeUpdate(stmt);
                                System.out.print("update done");

                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        if(!checkTimeMin(editStartTime.getText().toString()))
                            errorStartTime.setText("invalid Mins");
                        if(!checkTimeMin(editEndTime.getText().toString()))
                            errorEndTime.setText("invalid Mins");
                        if(!checkTimeHours(editStartTime.getText().toString()))
                            errorStartTime.setText("Time Out of working Hours");
                        if(!checkTimeHours(editEndTime.getText().toString()))
                            errorEndTime.setText("Time Out of working Hours");

                    }
                }
                else
                {
                     if(editStartTime.getText().isEmpty())
                        errorStartTime.setText("Invalid Format");
                    if(editEndTime.getText().isEmpty())
                        errorEndTime.setText("Invalid Format"); 
                    
                }
                
                
            }
        }
    }
    @FXML
    public void cancel(ActionEvent e) throws IOException
    {
        System.out.println("Cancel Button Pressed. Going back to the Main View...");
        
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/appointmentDashboard.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
            
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }
    
    
    public boolean checkTime(String s)
    {
        if(s.length()==5)
        {
            if((s.charAt(0)>='0'&&s.charAt(0)<='9')&&(s.charAt(1)>='0'&&s.charAt(1)<='9')&&(s.charAt(2)>=':')&&
                    (s.charAt(3)>='0'&&s.charAt(3)<='9')&&(s.charAt(4)>='0'&&s.charAt(4)<='9'))
                return true;
            else 
                return false;
        }
        else 
            return false;
    }
    
    
    public boolean checkTimeAvailability()
    {
        boolean FOUND=false;
       
        String st=editStartTime.getText().toString();
        String et=editEndTime.getText().toString();
        LocalDateTime [][]times;
        int total=0;
        try
        {
            String stmt="select start,end from appointment where start like '"+editAppointmentDate.getValue().toString()+"%'";
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
            String sTime=editStartTime.getText();//ST.s
            String []startTimeParts=sTime.split(":");
            String eTime=editEndTime.getText();//ST.e
            String []endTimeParts=eTime.split(":");
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
                if(empty&&(Integer.parseInt(startTimeParts[0])>=9&&Integer.parseInt(startTimeParts[0])<=16))
                {
                    FOUND=false;
                }    
                if(Integer.parseInt(startTimeParts[0])>=9&&Integer.parseInt(startTimeParts[0])<=16)
                {

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
    public boolean checkTimeMin(String s)
    {
        String []timeParts=s.split(":");
        
        if(Integer.parseInt(timeParts[1])>=0&&Integer.parseInt(timeParts[1])<=59)
        {
            return true;
        }
        else return false;
    }
    
    
    public boolean checkTimeHours(String time)
    {
        String[] timeParts=time.split(":");
        if(Integer.parseInt(timeParts[0])<9||Integer.parseInt(timeParts[0])>16)
            return false;
        else 
            return true;
    }
    
    
    public void resetLabels()
    {
        errorTitle.setText("");
        errorContact.setText(""); 
        errorTimeZone.setText("");
        errorStartTime.setText("");
        errorEndTime.setText("");
        errorDesc.setText("");
        errorType.setText("");
    }
    
}
