/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author usharifzoda
 */
public class Address {
    
    private int addressId, cityId;
    private String address, address2, postalCode, phone, createDate, createdBy, lastUpdateDate, lastUpdateBy;

    public Address(int addressId, int cityId, String address, String address2, String postalCode, String phone, String createDate, String createdBy, String lastUpdateDate, String lastUpdateBy) {
        this.addressId = addressId;
        this.cityId = cityId;
        this.address = address;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

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

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getAddressId() {
        return addressId;
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

    public String getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    
    
    
}
