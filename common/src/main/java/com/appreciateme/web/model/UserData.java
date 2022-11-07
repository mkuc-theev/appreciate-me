package com.appreciateme.web.model;

import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.usersservice.model.Sex;
import com.appreciateme.usersservice.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.appreciateme.usersservice.model.Sex.MALE;

public class UserData {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final int age;
    private final Sex sex;
    private final List<Opinion> incomingOpinions;

    private final List<Opinion> outgoingOpinions;
    private final List<User> reviewers;

    private final List<User> reviewedUsers;

    public UserData(User userData, List<Opinion> outgoingOpinionData, List<Opinion> incomingOpinionData, List<User> reviewerData, List<User> reviewedUserData) {
        id = userData.getId();
        firstName = userData.getFirstName();
        lastName = userData.getLastName();
        email = userData.getEmail();
        age = userData.getAge();
        sex = userData.getSex();
        incomingOpinions = incomingOpinionData;
        outgoingOpinions = outgoingOpinionData;
        reviewers = reviewerData;
        reviewedUsers = reviewedUserData;
    }

    public UserData() {
        id = "ERROR";
        firstName = "Nonexistent";
        lastName = "User";
        email = "placeholder@example.com";
        age = 18;
        sex = MALE;
        incomingOpinions = new ArrayList<>();
        outgoingOpinions = new ArrayList<>();
        reviewers = new ArrayList<>();
        reviewedUsers = new ArrayList<>();
    }

    public String getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public int getAge() {
        return age;
    }
    public Sex getSex() {
        return sex;
    }
    public List<User> getReviewers() {
        return reviewers;
    }
    public List<Opinion> getIncomingOpinions() {
        return incomingOpinions;
    }
    public List<Opinion> getOutgoingOpinions() {
        return outgoingOpinions;
    }
    public List<User> getReviewedUsers() {
        return reviewedUsers;
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
                ", incomingOpinions=" + incomingOpinions +
                ", outgoingOpinions=" + outgoingOpinions +
                ", reviewers=" + reviewers +
                ", reviewedUsers=" + reviewedUsers +
                '}';
    }
}
