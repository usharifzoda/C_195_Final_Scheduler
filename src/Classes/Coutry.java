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
public class Coutry {
    
    private int countryId;
    private String country, createDate,createdBy, lasUpdate, lastUpdateBy;
    
    public Coutry(){
        
    }

    public Coutry(int countryId, String createDate, String createdBy, String lasUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lasUpdate = lasUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLasUpdate(String lasUpdate) {
        this.lasUpdate = lasUpdate;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountry() {
        return country;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getLasUpdate() {
        return lasUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    

    
}
