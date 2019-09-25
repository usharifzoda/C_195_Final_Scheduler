/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Classes.Customer;
import Classes.Inventory;
import Databases.CustomerDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author usharifzoda
 */
public class EditCustomerViewController implements Initializable {

    private Customer selectedCustomer;
    int selectedIndex;
    
    public void initEditCustomer(Customer customer, int index){
        selectedCustomer = customer;
        selectedIndex = index;
        
        nameField.setText(selectedCustomer.getCustomerName());
        addressField.setText(selectedCustomer.getAddressId());
        phoneField.setText(selectedCustomer.getLastUpdate());
        
    }
    
    @FXML Label nameLabel;
    @FXML Label addressLabel;
    @FXML Label phoneLabel;
    
    @FXML TextField nameField;
    @FXML TextField addressField;
    @FXML TextField phoneField;

    
    @FXML Button saveButton;
    @FXML Button cancelButton;
    
    public void cancelButtonHandler(ActionEvent e) throws IOException {
        
        System.out.println("Cancel Button Pressed. Going back to the Main View...");
        
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/AddEditDeleteCustomer.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
            
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
