package com.iot.detector.controller.dto;

import com.iot.detector.entity.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class UserGroupUsersDto {
    private Long id;
    private String name;
    private List<BasicUserDTO> users;

    public UserGroupUsersDto(UserGroup userGroup) {
        this.id = userGroup.getId();
        this.name = userGroup.getName();
        this.users = userGroup.getUsers().stream().map(BasicUserDTO::new).toList();
    }
}
