package com.appreciateme.web.model;

import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.usersservice.model.Sex;
import com.appreciateme.usersservice.model.User;

import java.util.List;

public class UserData {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private Sex sex;
    private List<Opinion> opinions;

    private List<User> reviewers;

    public UserData(User userData, List<Opinion> opinionData, List<User> reviewerData) {
        id = userData.getId();
        firstName = userData.getFirstName();
        lastName = userData.getLastName();
        email = userData.getEmail();
        age = userData.getAge();
        sex = userData.getSex();
        opinions = opinionData;
        reviewers = reviewerData;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public List<Opinion> getOpinions() {
        return opinions;
    }

    public void setOpinions(List<Opinion> opinions) {
        this.opinions = opinions;
    }

    public List<User> getReviewers() {
        return reviewers;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", opinions=" + opinions +
                '}';
    }
}
