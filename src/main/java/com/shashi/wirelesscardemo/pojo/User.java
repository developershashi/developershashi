package com.shashi.wirelesscardemo.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Generated("jsonschema2pojo")
@Entity
@Table
public class User {

@SerializedName("first_name")
@Expose
private String firstName;
@SerializedName("last_name")
@Expose
private String lastName;
@Id
@SerializedName("email")
@Expose
private String email;
@SerializedName("gender")
@Expose
private String gender;
@SerializedName("birthday")
@Expose
private String birthday;

public String getFirstName() {
return firstName;
}

public void setFirstName(String firstName) {
this.firstName = firstName;
}

public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getGender() {
return gender;
}

public void setGender(String gender) {
this.gender = gender;
}

public String getBirthday() {
return birthday;
}

public void setBirthday(String birthday) {
this.birthday = birthday;
}


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}