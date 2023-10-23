package com.example.doanqlsv.Model;

public class Admin {
    public String getEmailAdmin() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }
public Admin()
{

}
    String email;
    String password;
}
