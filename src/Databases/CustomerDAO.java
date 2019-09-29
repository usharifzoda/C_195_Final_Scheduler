
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
    
    public static ObservableList<Customer> getAllCustomerInformation() throws SQLException {
        
        String stmt = "select * from customer AS c join address AS a ON c.addressId = a.addressId join city ON a.cityId = city.cityId "
                                                                             + "join country ON city.countryId = country.countryId;";
        Query.makeQuery(stmt);
        ObservableList<Customer> allCustomersInformation = FXCollections.observableArrayList();
        Customer customerInformationResult;
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
            String address = result.getString("address");
            String phone = result.getString("phone");
            String cityId = result.getString("cityId");
            String addressId = result.getString("addressId");
            String postalCode = result.getString("postalCode");
            String city = result.getString("city");
            String country = result.getString("country");
            
            customerInformationResult = new Customer(customerid, customerName, addressId, createDate, createdBy, lastUpdate, lastUpdateBy, true, 
                                                    active, address, address, postalCode, phone, city, country);
            allCustomersInformation.add(customerInformationResult);
        }
            
        return allCustomersInformation;
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
