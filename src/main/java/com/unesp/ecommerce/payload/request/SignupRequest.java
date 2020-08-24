package com.unesp.ecommerce.payload.request;

import java.util.Set;
 
public class SignupRequest {

    private String username;
    private String name;
    private Set<String> roles;
    private String password;
    private String cpf;
    private String cnpj;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRoles() {
      return this.roles;
    }
    
    public void setRole(Set<String> roles) {
      this.roles = roles;
    }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCnpj() { return cnpj; }

    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
