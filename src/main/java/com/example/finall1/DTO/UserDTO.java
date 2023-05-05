
package com.example.finall1.DTO;


import com.google.gson.Gson;



public class UserDTO {
    int id;
    String email;
    String password;
    String name;
    String gender;
    String birth;
    public UserDTO(String email, String password, String name, String gender, String birth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }

    public UserDTO(int id, String email, String password, String name, String gender, String birth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;

    }
    


    public UserDTO(String email, String pass) {
        this.email = email;
        this.password = pass;
    }

    public int getUserID() {
        return id;
    }

    public void setUserID(int id) {
        this.id = id;
    }

    public String getEmail() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    
    

    @Override
    public String toString() {
        return "UserDTO{" + "id=" + id + ", email=" + email + ", password=" + password + ", name=" + name + ", gender=" + gender + ", birth=" + birth +  '}';
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }
}
