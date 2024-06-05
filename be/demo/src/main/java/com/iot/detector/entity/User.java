package com.iot.detector.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "email must not be blank")
    @Size(max = 70)
    String email;

    @NotBlank(message = "first name must not be blank")
    @Size(max = 30)
    String firstName;

    @NotBlank(message = "last name must not be blank")
    @Size(max = 30)
    String lastName;

    @NotBlank(message = "password must not be blank")
    @Size(max = 120)
    String password;

    private String role;

    public User(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = "ROLE_USER";
    }
}