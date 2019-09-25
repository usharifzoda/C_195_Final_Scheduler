
package Databases;

import Classes.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author usharifzoda
 */
public class CustomerDAO {
    
    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        String stmt = "SELECT * FROM customer";
        Query.makeQuery(stmt);
        ObservableList<Customer> allCustomers=FXCollections.observableArrayList();    
        Customer customerResult;
        ResultSet result = Query.getResult();
        while(result.next()){
            int customerid = result.getInt("customerId");
            String customerName = result.getString("customerName");
            int addresdId = result.getInt("addressId");
            int active = result.getInt("active");
            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            
            customerResult = new Customer(customerid, customerName, createdBy, createDate, createdBy, lastUpdate, lastUpdateBy, true);
            allCustomers.add(customerResult);
//            return customerResult;
        }
        
        return allCustomers;
  
    }
 
    public static Customer getCustomer(int customerID) throws SQLException {
        String stmt = "SELECT * FROM customer";
        Query.makeQuery(stmt);
        Customer customerResult;
        ResultSet result = Query.getResult();
        while(result.next()){
            int customerid = result.getInt("customerId");
            String customerName = result.getString("customerName");
            int addresdId = result.getInt("addressId");
            int active = result.getInt("active");
            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            
            customerResult = new Customer(customerid, customerName, createdBy, createDate, createdBy, lastUpdate, lastUpdateBy, true);

            return customerResult;
        
         }
        return null;
    }

}
