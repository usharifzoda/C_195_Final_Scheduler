/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Classes.appointment;
import Classes.appointmentReport;
import Databases.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DG sahib
 */
public class AppointmentReportController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    ObservableList<String> Year;
    
    @FXML
    private ComboBox yearComboBox;
    @FXML
    private ComboBox monthComboBox;
    
    @FXML
    private Label errorYearLabel;
    @FXML
    private Label errorMonthLabel;
    
    @FXML
    private TableView <appointmentReport>reportTable;
    
    @FXML
    private TableColumn <appointmentReport,String> typeCol;
    @FXML
    private TableColumn <appointmentReport,Integer> countCol;
    
    @FXML
    private Button generateBtn;
    private Button generate;
    String  []months;
    ObservableList<appointmentReport> tableList;
    
    Statement s;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Year=FXCollections.observableArrayList();
        Year.addAll("2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030");
        yearComboBox.setItems(Year);
        
        
        typeCol.setCellValueFactory(new PropertyValueFactory<appointmentReport,String>("appointmentType"));
        countCol.setCellValueFactory(new PropertyValueFactory<appointmentReport,Integer>("count"));
        
       
        months=new String[12];
        months[0]="January";
        months[1]="February";
        months[2]="March";
        months[3]="April";
        months[4]="May";
        months[5]="June";
        months[6]="July";
        months[7]="August";
        months[8]="September";
        months[9]="October";
        months[10]="November";
        months[11]="December";
        monthComboBox.getItems().clear();
        monthComboBox.getItems().addAll(months);
        
        
        





        /* 
            
            Lambda Expression is used here
            Since Generate Button is used at least 3 times within the application 
            I belive it would make sense to use it. 
            Also this looks very clear and concise to anyone that would look at the code
        
        */
        
        
        generateBtn.setOnAction((event) ->  {
            System.out.println("inside ");
            generateReport();
        });
     
        
    }

    
    public void generateReport()
    {
        
        if (!yearComboBox.getSelectionModel().isEmpty()&&!monthComboBox.getSelectionModel().isEmpty())
        {
            
            String date;
            
            int m=monthComboBox.getSelectionModel().getSelectedIndex()+1;
            if(m<10)
                date=yearComboBox.getSelectionModel().getSelectedItem().toString()+"-0"+m;
            else
                date=yearComboBox.getSelectionModel().getSelectedItem().toString()+"-"+m;
            
            try
            {
                tableList=FXCollections.observableArrayList();
                s=DBConnection.conn.createStatement();
                String stmt="select count(appointmentId) from appointment where start like '"+date+"-%' and type='Introduction Checkup'"; 
                ResultSet r= s.executeQuery(stmt);
                while(r.next())
                    tableList.add(new appointmentReport("Introduction Checkup",r.getInt(1)));
                stmt="select count(appointmentId) from appointment where start like '"+date+"-%' and type='Brief Checkup'"; 
                
                r= s.executeQuery(stmt);
                while(r.next())
                    tableList.add(new appointmentReport("Brief Checkup",r.getInt(1)));
                stmt="select count(appointmentId) from appointment where start like '"+date+"-%' and type='Full Checkup'"; 
                
                r= s.executeQuery(stmt);
                while(r.next())
                    tableList.add(new appointmentReport("Full Checkup",r.getInt(1)));
                
                
                reportTable.setItems(tableList);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            if(yearComboBox.getSelectionModel().isEmpty())
                errorYearLabel.setText("Select Year");
            if(monthComboBox.getSelectionModel().isEmpty())
                errorMonthLabel.setText("Select Month");
        }
    }
    
    @FXML
    public void back(ActionEvent event) throws IOException
    {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/reportsWindow.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }

  
 
    
}
