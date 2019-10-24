/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Classes.appointment;
import Classes.logTable;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author usharifzoda
 */
public class LogFileWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ObservableList<logTable> tableList;
    @FXML private TableView<logTable> tableView;
    @FXML private TableColumn<logTable,String> timeStampCol;
    @FXML private TableColumn<logTable,String> userCol;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
        timeStampCol.setCellValueFactory(new PropertyValueFactory<logTable,String>("timeStamp"));
        userCol.setCellValueFactory(new PropertyValueFactory<logTable,String>("user"));
        
        String logFile="log.txt";
        tableList=FXCollections.observableArrayList();
        try 
        {
            
        // FileReader reads text files in the default encoding.
            FileReader reader=new FileReader(logFile);
            BufferedReader buffReader=new BufferedReader(reader);
            String line="";
            Date date= new Date();

            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            String st;
            while((st = buffReader.readLine()) != null)
            {
                String []logTable=st.split(",");
                tableList.add(new logTable(logTable[0],logTable[1]));
            }
            tableView.getItems().addAll(tableList);
                
            
                
        // Always close files.
            buffReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                logFile + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + logFile + "'");      
        }
        
    }    
    @FXML
    public void back(ActionEvent event)throws IOException
    {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/reportsWindow.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show(); 
    }
}
