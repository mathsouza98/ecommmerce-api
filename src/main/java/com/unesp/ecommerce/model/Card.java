package com.unesp.ecommerce.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Card {

    @Id
    String number;
    Date expDate;
    String banner;
    String holderName;
    String secCode;

    public Card(String number, Date expDate, String banner, String holderName, String secCode) {
        this.number = number;
        this.expDate = expDate;
        this.banner = banner;
        this.holderName = holderName;
        this.secCode = secCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }
}
