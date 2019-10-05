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
 * FXML Controller class
 *
 * @author DG sahib
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
    public TextField customerCity;
    @FXML
    public TextField customerZipCode;
    @FXML
    public TextField customerCountry;
    @FXML
    public Label errorLabel;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    public void save() throws SQLException
    {
//        System.out.println(customerName.getText().toString());
//        System.out.println(customerPhone.getText().toString());
//        System.out.println(customerAddress.getText().toString());
//        System.out.println(customerCity.getText().toString());
//        System.out.println(customerZipCode.getText().toString());
//        System.out.println(customerCountry.getText().toString());
        
        /* in order to add customer details in DB, first we will make sure that the text fields are not empty and and have valid values
        for example, county name, city name, customer name can only have alphabets, it should not contain any number or special character.
        in first IF block i am checking that wether the text fields are empty or not if not empty than we will check weather the values in the textfiels
        are valid or not in second IF block we are checking this,
        
        */
        if(customerName.getText().isEmpty()|| customerPhone.getText().isEmpty()||customerAddress.getText().isEmpty()||customerCity.getText().isEmpty()||customerZipCode.getText().isEmpty()||customerCountry.getText().isEmpty())
        {
            errorLabel.setText("Empty Field not allowed");
        }
        else
        {
            if(isAlphabet(customerName.getText().toString().trim())&&isAlphabet(customerCity.getText().toString().trim())&&isAlphabet(customerCountry.getText().toString().trim())&&isNumber(customerZipCode.getText().toString().trim())&&isNumber(customerPhone.getText().toString().trim()))  
            {
                //now here we will add values to db, but first we will check wwether country name already exit or not if not exist we will add 
                //country name than we will check city name exist or not if not exist we will add city , similiarly we will check address too
                
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
                   stmt="select cityid from city where where city='"+customerCity.getText()+"'";
                   ResultSet cityResult=s.executeQuery(stmt);
                   while (cityResult.next())
                   {
                       cityID=cityResult.getInt(0);
                   }
                }
                catch(Exception e)
                {
                   e.printStackTrace();
                }
                 
                        //add address than custoer
                
                   // update address record
                   // first we need city id to update address record
                   
                   
                try
                {
                   stmt="insert into address values( null,'"+customerAddress.getText().toString()+"',null,"+cityID+",'"+customerZipCode.getText().toString()+"','"+customerPhone.getText().toString()+"',CURDATE(),CURRENT_USER(),now(),CURRENT_USER())";
                   s.executeUpdate(stmt);
                   
                }
                catch(Exception e)
                {
                   e.printStackTrace();
                }
                        
                 
            }        
            else
                errorLabel.setText("Invalid Values");
        }
        
        
        
        
        
    }
    

    boolean isAlphabet(String s)
    {
        for (int i=0;i<s.length();i++)
        {
            
            if((s.charAt(i)>='A'&& s.charAt(i)<='Z')||(s.charAt(i)>='a'&& s.charAt(i)<='z'))
            {
                continue;
            }
            else return false;
        }
        return  true;
    }
    
    
       boolean isNumber(String s)
    {
        for (int i=0;i<s.length();i++)
        {
            
            if((s.charAt(i)>='0'&& s.charAt(i)<='9') || s.charAt(i) == '-')
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
