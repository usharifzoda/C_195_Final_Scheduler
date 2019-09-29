/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;


import Classes.Customer;
import Databases.Query;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
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
//        addressField.setText(selectedCustomer.getAddressId());
//        phoneField.setText(selectedCustomer.getLastUpdate());
        addressField.setText(selectedCustomer.getAddress());
        phoneField.setText(selectedCustomer.getPhone());
        zipCodeField.setText(selectedCustomer.getPostalCode());
        cityField.setText(selectedCustomer.getCity());
        countryField.setText(selectedCustomer.getCountry());
        
        System.out.println("********" +selectedCustomer.getPostalCode());
        System.out.println("********" +selectedCustomer.getCity());
        System.out.println("********" +selectedCustomer.getCountry());
        
    }
    
    @FXML Label nameLabel;
    @FXML Label addressLabel;
    @FXML Label phoneLabel;
    @FXML Label cityLabel;
    @FXML Label countryLabel;
    @FXML Label zipCodeLabel;
    
    @FXML TextField nameField;
    @FXML TextField addressField;
    @FXML TextField phoneField;
    @FXML TextField cityField;
    @FXML TextField countryField;
    @FXML TextField zipCodeField;

    
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
    
    public void saveButtonHandler (ActionEvent e) throws IOException{
        System.out.println("Save button is clicked...");
        
        String stmt = "update customer.customerName set customerName, ";
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
    
//    public boolean countryExists(String countryName){
//        
//        String stmt = "select * from country where country ='"+countryName+"'";
//        
//        Query.makeQuery(stmt);
//        
//        
//    }
    
}
