package com.iot.detector.controller.dto;

import com.iot.detector.entity.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserGroupDetailsDTO {
    private Long id;
    private String name;

    public UserGroupDetailsDTO(UserGroup userGroup) {
        this.id = userGroup.getId();
        this.name = userGroup.getName();
    }
}
