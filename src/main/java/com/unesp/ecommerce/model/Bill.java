package com.unesp.ecommerce.model;

public class Bill {

    String id;
    String paymentForm;
    String installmentNumber;
    String status;

    public Bill(String id, String paymentForm, String installmentNumber, String status) {
        this.id = id;
        this.paymentForm = paymentForm;
        this.installmentNumber = installmentNumber;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(String paymentForm) {
        this.paymentForm = paymentForm;
    }

    public String getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(String installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
