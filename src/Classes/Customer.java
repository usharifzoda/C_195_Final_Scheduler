package Classes;

/**
 *
 * @author usharifzoda
 */
public class Customer {
 
    private int customerId;
    private  String customerName, addressId, createDate, createdBy, lastUpdate, lastUpdatedBy;
    private boolean active;
    

    public Customer(int customerID, String customerName, String addressId, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, boolean active) {
        this.customerId = customerID;
        this.customerName = customerName;
        this.addressId = addressId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.active = active;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    
    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddressId() {
        return addressId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public boolean isActive() {
        return active;
    }
    
    
    

}
