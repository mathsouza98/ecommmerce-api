package com.unesp.ecommerce.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Contact {

    String homePhone;
    String commercialPhone;
    String cellPhone;
    String email;

    public Contact(String homePhone, String commercialPhone, String cellPhone, String email) {
        this.homePhone = homePhone;
        this.commercialPhone = commercialPhone;
        this.cellPhone = cellPhone;
        this.email = email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCommercialPhone() {
        return commercialPhone;
    }

    public void setCommercialPhone(String commercialPhone) {
        this.commercialPhone = commercialPhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
