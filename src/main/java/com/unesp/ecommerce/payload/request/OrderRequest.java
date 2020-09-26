package com.unesp.ecommerce.payload.request;

public class OrderRequest {

    private String addressId;
    private String paymentCardId;
    private long installmentNumber;

    public OrderRequest(String addressId, String paymentCardId, long installmentNumber) {
        this.addressId = addressId;
        this.paymentCardId = paymentCardId;
        this.installmentNumber = installmentNumber;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPaymentCardId() {
        return paymentCardId;
    }

    public void setPaymentCardId(String paymentCardId) {
        this.paymentCardId = paymentCardId;
    }

    public long getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(long installmentNumber) {
        this.installmentNumber = installmentNumber;
    }
}
