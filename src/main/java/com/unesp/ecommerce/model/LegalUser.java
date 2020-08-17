package com.unesp.ecommerce.model;

public class LegalUser extends User {
    String cnpj;

    public LegalUser(String username, String password, String cnpj) {
        super(username, password);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
