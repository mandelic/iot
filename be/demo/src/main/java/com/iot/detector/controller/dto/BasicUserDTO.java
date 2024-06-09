package com.iot.detector.controller.dto;

import com.iot.detector.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class BasicUserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public BasicUserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

}
