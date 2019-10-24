/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import Databases.DBConnection;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author usharifzoda
 */

public class AddCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public TextField customerName;
    @FXML
    public TextField customerPhone;
    @FXML
    public TextField customerAddress;
    @FXML
    public TextField customerAddress2;
    @FXML
    public TextField customerCity;
    @FXML
    public TextField customerZipCode;
    @FXML
    public TextField customerCountry;
     @FXML
    public Label errorCustomerName;
    @FXML
    public Label errorCustomerPhone;
    @FXML
    public Label errorCustomerAddress;
    @FXML
    public Label errorCustomerCity;
    @FXML
    public Label errorCustomerZipCode;
    @FXML
    public Label errorCustomerCountry;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    public void save() throws SQLException
    {
        
        /* in order to add customer details in DB, first we will make sure that the text fields are not empty and and have valid values
        for example, county name, city name, customer name can only have alphabets, it should not contain any number or special character.
        in first IF block i am checking that wether the text fields are empty or not if not empty than we will check weather the values in the textfiels
        are valid or not in second IF block we are checking this,
        
        */
        
        System.out.println(customerName.getText().toString().trim());
        //here i mistakenly called reset() function instead of resetLables()
        resetLabels();
        if(customerName.getText().isEmpty()|| customerPhone.getText().isEmpty()||customerAddress.getText().isEmpty()||customerCity.getText().isEmpty()||customerZipCode.getText().isEmpty()||customerCountry.getText().isEmpty())
        {
           // errorLabel.setText("Empty Field not allowed");
            System.out.println(customerName.getText().toString().trim());
            if(customerName.getText().isEmpty())
                errorCustomerName.setText("Customer Name cannot be empty");
            if(customerPhone.getText().isEmpty())
                errorCustomerPhone.setText("Customer Phone Number cannot be empty");
            if(customerAddress.getText().isEmpty())
                errorCustomerAddress.setText("Customer Address cannot be empty");
            if(customerCity.getText().isEmpty())
                errorCustomerCity.setText("Customer City cannot be empty");
            if(customerZipCode.getText().isEmpty())
                errorCustomerZipCode.setText("Customer Zip Code cannot be empty");
            if(customerCountry.getText().isEmpty())
                errorCustomerCountry.setText("Customer Country cannot be empty");
            
            
            
            
        }
        else
        {
            if(isAlphabet(customerName.getText().toString().trim())&&isAlphabet(customerCity.getText().toString().trim())&&isAlphabet(customerCountry.getText().toString().trim())&&isNumber(customerZipCode.getText().toString().trim())&&isPhoneNumber(customerPhone.getText().toString().trim()))  
            {
                //now here we will add values to db, but first we will check wwether country name already exit or not if not exist we will add 
                //country name than we will check city name exist or not if not exist we will add city , similiarly we will check address too
                System.out.println(customerName.getText().toString().trim());
                boolean countryFlag=false;
                boolean cityFlag=false;
                int cid=-1;
                Statement s=DBConnection.conn.createStatement();
                //will check wether country name exist or not
                String stmt = "select * from country where country='"+customerCountry.getText()+"'";
                try
                {
                    
                    ResultSet r=s.executeQuery(stmt);
                    while(r.next())
                        countryFlag=true;
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
        
                //will check wether city name exist or not
               stmt = "select * from city where city='"+customerCity.getText()+"'";
               
               try
               {
                    s=DBConnection.conn.createStatement();
                    ResultSet res=s.executeQuery(stmt);
                    while(res.next())
                        cityFlag=true;
                    //  if country name doesnot exit i will insert 
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
                if(countryFlag==false)
                {
                    System.out.println("Inside insert into Country");
                    stmt="insert into country values (null,'"+customerCountry.getText()+"',CURDATE(),CURRENT_USER(),now(),CURRENT_USER())";
                    s.executeUpdate(stmt);
                    System.out.println("Left insert Country");

                }
                
               
                boolean cityChanged=false;

                // if cityname doesnot exist we will insert it in if section, if cityname already exist
                // we will update the country id, for both choices we need country id, so first we will get 
                //countryig 


                //we will feteh country id because we can only insert country id in city table
                try{
                    ResultSet countryID=s.executeQuery("select countryid from country where country='"+customerCountry.getText()+"'");

                    while(countryID.next())
                    {    
                        cid=countryID.getInt("countryid");
                        break;
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                 // we will make decision , we will update or insert new record 
                 if(cityFlag==false)
                 {
                     /// inserting city record in city table
                     try
                     {
                        System.out.println("Inside Insert into City 156");
                        stmt="insert into city values (null,'"+customerCity.getText()+"',"+cid+",CURDATE(),CURRENT_USER(),now(),CURRENT_USER())";
                        s.executeUpdate(stmt);
                        System.out.println("Left insert into the City - 158");
                     }
                     catch(Exception e)
                     {
                         e.printStackTrace();
                     }

                 }
                 else
                 {
                     //updating city record 
                     
                     try
                     {
                         //'"+cityField.getText()+"'
                        stmt="update city set countryid="+cid+" where city='"+customerCity.getText()+"'";
                         s.executeUpdate(stmt);
                     }
                     catch(Exception e)
                     {
                         e.printStackTrace();
                     }
                     
                 }
                
                 
                 int cityID=-1;
                 
                try
                {
                   stmt="select cityid from city where city='"+customerCity.getText()+"'";
                   ResultSet cityResult=s.executeQuery(stmt);
                   while (cityResult.next())
                   {
                       cityID=cityResult.getInt(1);
                   }
                }
                catch(Exception e)
                {
                   e.printStackTrace();
                }
                 
                        //add address than custoer
                
                   // update address record
                   // first we need city id to update address record
                   
                int add=-1;
                try
                {
                   String add2="";
                   if(customerAddress2.getText().isEmpty())
                       add2=" ";
                   else
                       add2=customerAddress2.getText().toString();
                   stmt="insert into address values( null,'"+customerAddress.getText().toString()+"','"+add2+"',"+cityID+",'"+customerZipCode.getText().toString()+"','"+customerPhone.getText().toString()+"',CURDATE(),CURRENT_USER(),now(),CURRENT_USER())";
                   System.out.println(stmt);
                   s.executeUpdate(stmt);
                   stmt ="select addressid from address where address='"+customerAddress.getText().toString()+"'";
                   ResultSet r=s.executeQuery(stmt);
                   while (r.next())
                   {
                       add=r.getInt("addressid");
                   }
                }
                catch(Exception e)
                {
                   e.printStackTrace();
                }
                
                try
                {
                    
                   stmt="insert into customer values( null,'"+customerName.getText().toString()+"',"+add+",1,CURDATE(),CURRENT_USER(),now(),CURRENT_USER())";
                   s.execute(stmt);
                   reset();
                   errorCustomerCountry.setText("Customer Added Successfully to the Database");
                   
                }
                catch(Exception e)
                {
                   e.printStackTrace();
                }
              
            }        
            else
            {
                //here was the error i just forget to but ! 
                if(!isAlphabet(customerName.getText().toString().trim()))
                    errorCustomerName.setText("Invalid Customer Name");
                if(!isPhoneNumber(customerPhone.getText().toString().trim()))
                    errorCustomerPhone.setText("Invalid Customer Phone");
                if(!isAlphabet(customerCity.getText().toString().trim()))
                    errorCustomerCity.setText("Invalid Customer City");
                if(!isNumber(customerZipCode.getText().toString().trim()))
                    errorCustomerZipCode.setText("Invalid Customer Zip Code");
                if(!isAlphabet(customerCountry.getText().toString().trim()))
                    errorCustomerCountry.setText("Invalid Customer Country");
            
            }
        }
        
        
        
        
        
    }
    void reset()
    {
        
        resetLabels();
        
        customerName.clear();
        customerPhone.clear();
        customerAddress.clear();
        customerCity.clear();
        customerZipCode.clear();
        customerCountry.clear();
    }
    
    void resetLabels()
    {
        
        errorCustomerName.setText("");
        errorCustomerPhone.setText("");
        errorCustomerAddress.setText("");
        errorCustomerCity.setText("");
        errorCustomerZipCode.setText("");
        errorCustomerCountry.setText("");
        
        
    }

    
    
    
    

    boolean isAlphabet(String s)
    {System.out.println(s);
        for (int i=0;i<s.length();i++)
        {
            System.out.println(s.charAt(i));
            if((s.charAt(i)>='A'&& s.charAt(i)<='Z')||(s.charAt(i)>='a'&& s.charAt(i)<='z')||(s.charAt(i)==' '))
            {
                System.out.println("inside isalphabet true");
                continue;
            }
            else 
            {
                System.out.println("inside isalphabet false");
                return false;
            }
        }
        return  true;
    }
    
    
    boolean isNumber(String s)
    {
        System.out.println(s);
        for (int i=0;i<s.length();i++)
        {
            System.out.println(s.charAt(i));
            if((s.charAt(i)>='0'&& s.charAt(i)<='9'))
            {
                continue;
            }
            else return false;
        }
        return  true;
    }
    boolean isPhoneNumber(String s)
    {
        for (int i=0;i<s.length();i++)
        {
            
            if((s.charAt(i)>='0'&& s.charAt(i)<='9') || s.charAt(i) == '-'||s.charAt(i)==' ')
            {
                continue;
            }
            else return false;
        }
        return  true;
    }
    
    // Going back to the Add Edit Delete Customer View   
    
    public void cancel(ActionEvent e) throws IOException 
    {

        System.out.println("Cancel Button Pressed. Going back to the Main View...");

        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/AddEditDeleteCustomer.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();

    
    }
    
    
}
