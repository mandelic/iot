package com.iot.detector.controller.dto;
import com.iot.detector.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserDTO {
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "Adresa e-pošte je neispravnog oblika.")
    @Size(max = 70, message = "Adresa e-pošte premašuje zadani broj znakova.")
    private String email;
    @NotBlank(message = "Ime je neispravnog oblika.")
    @Size(max = 30, message = "Ime premašuje zadani broj znakova.")
    private String firstName;
    @NotBlank(message = "Prezime je neispravnog oblika.")
    @Size(max = 30, message = "Prezime premašuje zadani broj znakova.")
    private String lastName;
    @NotBlank(message = "Lozinka je neispravnog oblika.")
    @Size(max = 120, message = "Lozinka premašuje zadani broj znakova.")
    private String password;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
    }
}