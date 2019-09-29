package Classes;

/**
 *
 * @author usharifzoda
 */
public class Customer {

    private int customerId;
    private String customerName, addressId, createDate, createdBy, lastUpdate, lastUpdatedBy;
    private boolean active;

    // Address Related Field
    private int cityId;
    private String address, address2, postalCode, phone;
    
    // City and Country field
    private String city, country;

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

    public Customer(int customerId, String customerName, String addressId, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, 
            boolean active, int cityId, String address, String address2, String postalCode, String phone, String city, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.active = active;
        this.cityId = cityId;
        this.address = address;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;
        this.country = country;
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

    // Address Related Field
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCityId() {
        return cityId;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    // City and Country Getters and Setters 

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
    
    
}
