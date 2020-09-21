package com.unesp.ecommerce.model;

public class PhysicalUser extends User {
    String cpf;

    public PhysicalUser(String username, String password, Contact contact, String name, String cpf) {
        super(username, password, contact, name);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
