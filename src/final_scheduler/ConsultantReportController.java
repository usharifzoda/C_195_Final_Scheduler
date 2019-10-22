/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Classes.consultantReport;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class///..
 *
 * @author DG sahib
 */
public class ConsultantReportController implements Initializable 
{

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private DatePicker date;
    @FXML
    private Label errorDate;
    @FXML
    private TableView<consultantReport> consultantTable;
    @FXML
    private TableColumn<consultantReport,String> nameCol;
    @FXML
    private TableColumn<consultantReport,String> startCol;
    @FXML
    private TableColumn<consultantReport,String> endCol;
    @FXML
    private TableColumn<consultantReport,String> typeCol;
    
    
    ObservableList<consultantReport> tableList;
    @FXML
    private Button generateBtn;
    Statement s;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        typeCol.setCellValueFactory(new PropertyValueFactory<consultantReport,String>("type"));
        nameCol.setCellValueFactory(new PropertyValueFactory<consultantReport,String>("name"));
        startCol.setCellValueFactory(new PropertyValueFactory<consultantReport,String>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<consultantReport,String>("end"));
        
        





        //////// lambda Expression is used here
        
        
        
        
        generateBtn.setOnAction((event) ->  {
           
            generateReport();
        });
         
         
    }    
    
    @FXML
    private void generateReport()
    {
        if(date.getValue()!=null)
        {
            String d=date.getValue().toString();
            System.out.println(date);
            String []dateParts=d.split("-");
            System.out.println(dateParts[0]);
            System.out.println(dateParts[1]);
            System.out.println(dateParts[2]);
            
            try
            {
                
                String stmt="select c.customerName,a.start,a.end,a.type from appointment a, customer c where c.customerId=a.customerId and userId=1 and start like '"+dateParts[0]+"-"+dateParts[1]+"-"+dateParts[2]+"%'";
                try
                {
                    tableList=FXCollections.observableArrayList();
                    s=DBConnection.conn.createStatement();
                    ResultSet r=s.executeQuery(stmt);
                    System.out.println("before whle");
                    while(r.next())
                    {
                        System.out.println("after whle 1");
                        tableList.add(new consultantReport(r.getString(1),r.getString(2),r.getString(3),r.getString(4)));
                        System.out.println("after whle 2");
                    }
                    consultantTable.setItems(tableList);
                    System.out.println("333333333");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            if(date.getValue()==null)
                errorDate.setText("Choose Date");
            
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
