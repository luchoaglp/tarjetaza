package com.tarjetaza.domain.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "{username.notblank}")
    @Size(min = 5, max = 15)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Size(min = 2, max = 100)
    @Email
    private String email;

    @JsonIgnore
    @NotBlank(message = "{password.notblank}")
    @Size(max = 60)
    private String password;

    @JsonProperty("first_name")
    @NotBlank(message = "{firstName.notblank}")
    @Size(min = 2, max = 75, message =  "{firstName.size}")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "{lastName.notblank}")
    @Size(min = 2, max = 75, message =  "{lastName.size}")
    private String lastName;

    @JsonProperty("created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-03:00")
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonProperty("last_modified_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-03:00")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name="user_role",
            joinColumns = { @JoinColumn(name = "user_id")},
            inverseJoinColumns = { @JoinColumn(name ="role_id")})
    private List<Role> roles = new ArrayList<>();

    @NotNull
    private Boolean active;

    public User() {
        this.active = true;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public User(String username, String password) {
        this.active = true;
        this.username = username;
        this.password = password;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }


    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    /*
    public void addRoles(List<Role> roles) {
        for(Role role : roles) {
            roles.add(role);
            role.getUsers().add(this);
        }
    }
    */

    public String rolesToString() {
        return roles
                .stream()
                .map(role -> role.getName().split("_")[1])
                .collect(Collectors.joining( ", " ));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}