package com.iot.detector.controller.dto;

import com.iot.detector.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
    Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<UserGroupDetailsDTO> groups;
    private String role;

    public UserDetailsDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.groups = user.getUserGroups().stream().map(UserGroupDetailsDTO::new).collect(Collectors.toSet());
        this.role = user.getRole();
    }

}