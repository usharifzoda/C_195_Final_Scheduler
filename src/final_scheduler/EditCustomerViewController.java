/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;


import Classes.Customer;
import Databases.DBConnection;
import Databases.Query;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    int selectedIndex, customerIndex;
    String addressID="";
    public void initEditCustomer(Customer customer, int index){
        selectedCustomer = customer;
        selectedIndex = index;
        customerIndex = selectedCustomer.getCustomerId();
        System.out.println("**** Customer ID is: " + customerIndex);
        addressID=selectedCustomer.getAddressId().toString();
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
    
    public void saveButtonHandler (ActionEvent e) throws IOException,SQLException{
        System.out.println("Save button is clicked...");
        boolean countryFlag=false;
        boolean cityFlag=false;
        int cid=-1;
        //will check wether country name exist or not
        String stmt = "select * from country where country='"+countryField.getText()+"'";
        
        Statement s=DBConnection.conn.createStatement();
        ResultSet r=s.executeQuery(stmt);
        while(r.next())
            countryFlag=true;

        
         //will check wether city name exist or not
        stmt = "select * from city where city='"+cityField.getText()+"'";
        
        s=DBConnection.conn.createStatement();
        ResultSet res=s.executeQuery(stmt);
        while(res.next())
            cityFlag=true;
        //  if country name doesnot exit i will insert 
        
        if(countryFlag==false)
        {
            stmt="insert into country values (null,'"+countryField.getText()+"',CURDATE(),CURRENT_USER(),now(),CURRENT_USER())";
            s.executeUpdate(stmt);
        //    Query.makeQuery(stmt);
           
        }
        boolean cityChanged=false;
        
       // if cityname doesnot exist we will insert it in if section, if cityname already exist
       // we will update the country id, for both choices we need country id, so first we will get 
       //countryid 
       
       
       //we will feteh country id because we can only insert country id in city table
        ResultSet countryID=s.executeQuery("select countryid from country where country='"+countryField.getText()+"'");
        
        while(countryID.next())
        {    
            cid=countryID.getInt("countryid");
            break;
        }
        
        // we will make decision , we will update or insert new record 
        if(cityFlag==false)
        {
            /// inserting city record in city table
            stmt="insert into city values (null,'"+cityField.getText()+"',"+cid+",CURDATE(),CURRENT_USER(),now(),CURRENT_USER())";
            s.executeUpdate(stmt);
           
        }
        else
        {
            //updating city record 
            stmt="update city set countryid="+cid+" where city='"+cityField.getText()+"'";
            s.executeUpdate(stmt);
        }
        
          // update address record
          // first we need city id to update address record
            ResultSet cityID=s.executeQuery("select cityid from city where city='"+cityField.getText()+"'");
            while(cityID.next())
            {
                cid=cityID.getByte("cityid");
            }
            s.executeUpdate("update address set phone='"+phoneField.getText()+"',address='"+addressField.getText()+"',cityid="+cid+",postalCode="+zipCodeField.getText()+" where addressid="+Integer.parseInt(addressID));
        
            // Update Name after all the previous table information has been updated
            ResultSet addressId = s.executeQuery("select addressId from customer where customerId ='"+customerIndex+"'");
            
            s.executeUpdate("update customer set customerName = '"+nameField.getText()+"'" + "where customerId=" + "'"+customerIndex +"'");
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    


//    private Customer selectedCustomer;
//    int selectedIndex;
//    
//    public void initEditCustomer(Customer customer, int index){
//        selectedCustomer = customer;
//        selectedIndex = index;
//        
//        nameField.setText(selectedCustomer.getCustomerName());
////        addressField.setText(selectedCustomer.getAddressId());
////        phoneField.setText(selectedCustomer.getLastUpdate());
//        addressField.setText(selectedCustomer.getAddress());
//        phoneField.setText(selectedCustomer.getPhone());
//        zipCodeField.setText(selectedCustomer.getPostalCode());
//        cityField.setText(selectedCustomer.getCity());
//        countryField.setText(selectedCustomer.getCountry());
//        
//        System.out.println("********" +selectedCustomer.getPostalCode());
//        System.out.println("********" +selectedCustomer.getCity());
//        System.out.println("********" +selectedCustomer.getCountry());
//        
//    }
//    
//    @FXML Label nameLabel;
//    @FXML Label addressLabel;
//    @FXML Label phoneLabel;
//    @FXML Label cityLabel;
//    @FXML Label countryLabel;
//    @FXML Label zipCodeLabel;
//    
//    @FXML TextField nameField;
//    @FXML TextField addressField;
//    @FXML TextField phoneField;
//    @FXML TextField cityField;
//    @FXML TextField countryField;
//    @FXML TextField zipCodeField;
//
//    
//    @FXML Button saveButton;
//    @FXML Button cancelButton;
//    
//    public void cancelButtonHandler(ActionEvent e) throws IOException {
//        
//        System.out.println("Cancel Button Pressed. Going back to the Main View...");
//        
//        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/AddEditDeleteCustomer.fxml"));
//        Scene mainViewScene = new Scene(mainViewParent);
//            
//        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
//        window.setScene(mainViewScene);
//        window.show();
//        
//    }
//    
//    public void saveButtonHandler (ActionEvent e) throws IOException, SQLException{
//        System.out.println("Save button is clicked...");
//        
//        String stmtCountry = "update customer.customerName set customerName, ";
//        
//        // Check if Country Exists
//        if(countryExists(countryField.getText())){
//            System.out.println("Country name exists " + countryField.getText());
//        }
//        else{
//            System.out.println("Country does not exist...." + countryField.getText());  
//            System.out.println("Adding new country...");
//            
//            //
//          String insertNewCountry =  "INSERT INTO country VALUES " +  
//            "(1,'US','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test')";
//            
//            
//            
//        } 
//        
//        // Check City exists 
//        if(cityExists(cityField.getText())){
//            System.out.println("City name exists " + cityField.getText());
//        }else {
//            System.out.println("City does not Exists " + cityField.getText());
//        }
//        
//        
//        
//    }
//    
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        
//        
//    }    
//    
//    
//    public boolean countryExists(String countryName) throws SQLException{
//        
//        String stmt = "select * from country where country ='"+countryName+"'";
//        
//        Statement s = DBConnection.conn.createStatement();
//        ResultSet result = s.executeQuery(stmt);
//        
//        if(result.next() == false){
//            return false;
//        }
//        else
//            return true;
//          
//    }
//    
//    public boolean cityExists(String cityName) throws SQLException{
//        
//        String stmt = "select * from city where city ='"+cityName+"'";
//        
//        Statement s = DBConnection.conn.createStatement();
//        ResultSet result = s.executeQuery(stmt);
//        
//        if(result.next() == false){
//            return false;
//        }
//        else
//            return true;      
//    }
//    
//        
//    public boolean addressExists(String addressName) throws SQLException{
//        
//        String stmt = "select * from city where city ='"+addressName+"'";
//        
//        Statement s = DBConnection.conn.createStatement();
//        ResultSet result = s.executeQuery(stmt);
//        
//        if(result.next() == false){
//            return false;
//        }
//        else
//            return true;      
//    }
//        
}
