package com.shashi.wirelesscardemo.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shashi.wirelesscardemo.enums.Gender;

public class UserRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Gender gender;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
