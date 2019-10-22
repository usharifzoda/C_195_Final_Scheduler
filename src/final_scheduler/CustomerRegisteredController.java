/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Classes.customerReport;
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
public class CustomerRegisteredController implements Initializable {

    @FXML
    private ComboBox<String> yearComboBox;
    @FXML
    private ComboBox<String> monthComboBox;
    @FXML
    private Label errorYear;
    @FXML
    private Label errorMonth;
    @FXML
    private TableView<customerReport> reportTable;
    @FXML
    private TableColumn<customerReport, String> monthYearCol;
    @FXML
    private TableColumn<customerReport, Integer> countCol;
    ObservableList<String> Year;
    Statement s;
    @FXML
    private Button generateBtn;
    String  []months;
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
         Year=FXCollections.observableArrayList();
        Year.addAll("2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030");
        yearComboBox.setItems(Year);
        
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
        
        countCol.setCellValueFactory(new PropertyValueFactory<customerReport,Integer>("count"));
        monthYearCol.setCellValueFactory(new PropertyValueFactory<customerReport,String>("yearMonth"));
        





        /* 
            
            Lambda Expression is used here
            Since Generate Button is used at least 3 times within the application 
            I belive it would make sense to use it. 
            Also this looks very clear and concise to anyone that would look at the code
            
        */
                
        generateBtn.setOnAction((event) ->  {
           
            generate();
        });
    }    

    ObservableList<customerReport> tableList;
    
    private void generate() 
    {
        if(!yearComboBox.getSelectionModel().isEmpty()&&!monthComboBox.getSelectionModel().isEmpty())
        {
            try
            {
                tableList=FXCollections.observableArrayList();
                s=DBConnection.conn.createStatement();
                String Stmt="";
                int YEAR=Integer.parseInt(yearComboBox.getSelectionModel().getSelectedItem().toString());
                int Month=monthComboBox.getSelectionModel().getSelectedIndex()+1;
                if(Month<10)
                    Stmt="select count(customerId) from customer where createDate like '"+YEAR+"-0"+Month+"-%'";
                else 
                    Stmt="select count(customerId) from customer where createDate like '"+YEAR+"-"+Month+"-%'";
                
                ResultSet r=s.executeQuery(Stmt);
                while(r.next())
                    tableList.add(new customerReport(YEAR+" - "+months[Month-1],r.getInt(1)));
                    
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
                errorYear.setText("Select Year");
            if(monthComboBox.getSelectionModel().isEmpty())
                errorMonth.setText("Select month");
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
