/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Classes.Customer;
import Classes.Inventory;
import Databases.CustomerDAO;
import Databases.Query;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * @author usharifzoda
 */

public class AddEditDeleteCustomerController implements Initializable {
    
    private Customer selectedCustomer;
    
    @FXML TableView<Customer> tableView;
    @FXML TableColumn<Customer, String> customerId;
    @FXML TableColumn<Customer, String> customerName;
    @FXML TableColumn<Customer, String> customerAddress;
    @FXML TableColumn<Customer, String> customerPhone;
    @FXML TableColumn<Customer, String> customerCity;
    @FXML TableColumn<Customer, String> customerCountry;
    @FXML TableColumn<Customer, String> customerZipCode;
    

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
    
    public void editCustomerButtonHandler(ActionEvent e) throws IOException {
        
        if(tableView.getSelectionModel().getSelectedItem() == null ){
            System.out.println("Please select a customer to Edit");
            
        }else {
            System.out.println("Edit Customer button pressed...");
        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXMLViews/EditCustomerView.fxml"));
            Parent editCustomerView = loader.load();
            
            Scene editCustomerScene = new Scene(editCustomerView);
            EditCustomerViewController controller = loader.getController();
            controller.initEditCustomer(tableView.getSelectionModel().getSelectedItem(), 
                                        tableView.getSelectionModel().getSelectedIndex());
            
            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.setScene(editCustomerScene);
            window.show();
            
        }
        
    }
    
    // Method to delete the Customer from the table View
    public void deleteCustomerButtonHandler(ActionEvent e) throws SQLException, IOException {
        System.out.println("Delete Button has been pressed...");
        
        if(tableView.getSelectionModel().getSelectedItem() == null){
            System.out.println("Please select a customer to Delete...");
            
        }else {
            boolean answer = confirmDelete();
            
            if(answer){
                ObservableList<Customer> allCustomers;
                allCustomers = tableView.getItems();
                selectedCustomer = tableView.getSelectionModel().getSelectedItem();
                
                System.out.println("Customer to be Deleted: " + selectedCustomer.getCustomerName() + " " + selectedCustomer.getCustomerId());
                int deleteId = selectedCustomer.getCustomerId();
                String stmt = "delete from customer where customerId =" + deleteId;
                
                Query.makeQuery(stmt);
                
                // Load the tableView again after Query to make refresh
                Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/AddEditDeleteCustomer.fxml"));
                Scene mainViewScene = new Scene(mainViewParent);
        
                Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
                window.setScene(mainViewScene);
                window.show(); 
                
                
            }
            
            
        } 
        
    }
    
    public void addCustomerButtonHandler(ActionEvent e) throws SQLException, IOException {
            
        System.out.println("Add Customer Button Pressed. Going to Add Customer View");
        
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/addCustomer.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
            
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
//            Inventory.allCustomers = CustomerDAO.getAllCustomers();
              Inventory.allCustomers = CustomerDAO.getAllCustomerInformation();
              
            
        } catch (SQLException ex) {
            Logger.getLogger(AddEditDeleteCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        customerId.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("Phone"));
//        customerCity.setCellValueFactory(new PropertyValueFactory<Customer, String>("City"));
//        customerZipCode.setCellValueFactory(new PropertyValueFactory<Customer, String>("Zip"));
//        customerCountry.setCellValueFactory(new PropertyValueFactory<Customer, String>("Country"));
        
        tableView.setItems(Inventory.allCustomers);
        

        
    }

    // Confirm Delete Dialog Popup
    public static boolean confirmDelete(){
        boolean deleteAnswer = true;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure you want to DELETE?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            return deleteAnswer;
        }

    return false;
    }    

    
}
