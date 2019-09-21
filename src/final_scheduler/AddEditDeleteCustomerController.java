/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Databases.Query;
import Classes.Customer;
import Classes.Inventory;
import Databases.CustomerDAO;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * @author usharifzoda
 */

public class AddEditDeleteCustomerController implements Initializable {
    
    @FXML TableView<Customer> tableView;
    @FXML TableColumn<Customer, String> customerId;
    @FXML TableColumn<Customer, String> customerName;
    

    @FXML private Button addCustomerButton;
    @FXML private Button editCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button cancelButton;
    
   
    
    public void cancelActionHandler(ActionEvent e) throws IOException {
        System.out.println("Cancel Button Pressed. Going back to the Main View...");
        
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/MainView.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
            
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Inventory.allCustomers = CustomerDAO.getAllCustomers();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AddEditDeleteCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        customerId.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        
        tableView.setItems(Inventory.allCustomers);
        
//        String sqlStatement = "SELECT customerId, customerName FROM employee_tbl";
//        Query.makeQuery(sqlStatement);
        
    }    
    
}
