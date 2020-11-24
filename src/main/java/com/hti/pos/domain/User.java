package com.hti.pos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravuthz
 * Date : 11/23/2020, 1:27 PM
 * Email : ravuthz@gmail.com,
 * yovannaravuth@gmail.com
 */

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7721781417455120512L;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty
    @JsonIgnore
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private boolean enabled;

    private Integer failedLoginAttempts = 0;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roles = new ArrayList<>();

    public User(User user) {
        this.email = user.email;
        this.username = user.username;
        this.password = user.password;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.enabled = user.enabled;
        this.failedLoginAttempts = user.failedLoginAttempts;
//        this.roles = user.roles;
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static User staticUser(String firstName, String lastName) {
        User user = new User(firstName, lastName);
        user.setEmail(firstName + "@gmail.com");
        user.setUsername(firstName);
        user.setPassword("123123");
        user.setEnabled(true);
        return user;
    }

    public void setFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @JsonIgnore
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enabled=" + enabled +
                ", failedLoginAttempts=" + failedLoginAttempts +
                '}';
    }
}
