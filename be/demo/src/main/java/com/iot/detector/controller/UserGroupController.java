package com.iot.detector.controller;

import com.iot.detector.controller.dto.UserGroupDTO;
import com.iot.detector.controller.dto.UserGroupDetailsDTO;
import com.iot.detector.controller.dto.UserGroupUsersDto;
import com.iot.detector.entity.UserGroup;
import com.iot.detector.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user-groups")
public class UserGroupController {
    @Autowired
    private UserGroupRepository userGroupRepository;

    @GetMapping
    public List<UserGroupUsersDto> getUserGroups() {
        return userGroupRepository.findAll().stream().map(UserGroupUsersDto::new).toList();
    }

    @PostMapping
    public UserGroup createUserGroup(@RequestBody UserGroupDTO userGroup) {
        return userGroupRepository.save(new UserGroup(userGroup.getName()));
    }

}
