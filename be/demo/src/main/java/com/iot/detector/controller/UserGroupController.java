package com.iot.detector.controller;

import com.iot.detector.controller.dto.BasicUserDTO;
import com.iot.detector.controller.dto.UserGroupDTO;
import com.iot.detector.controller.dto.UserGroupDetailsDTO;
import com.iot.detector.controller.dto.UserGroupUsersDto;
import com.iot.detector.entity.User;
import com.iot.detector.entity.UserGroup;
import com.iot.detector.exceptions.EntityIdNotFoundException;
import com.iot.detector.repository.UserGroupRepository;
import com.iot.detector.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/user-groups")
@CrossOrigin(origins = "*")
public class UserGroupController {
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserGroupUsersDto> getUserGroups() {
        return userGroupRepository.findAll().stream().map(UserGroupUsersDto::new).toList();
    }

    @PostMapping
    public UserGroup createUserGroup(@RequestBody UserGroupDTO userGroup) {
        return userGroupRepository.save(new UserGroup(userGroup.getName()));
    }

    @PostMapping("/{id}")
    public ArrayList<BasicUserDTO> addUsersToUserGroup(@PathVariable Long id, @RequestBody ArrayList<Long> userIds) {
        UserGroup userGroup = userGroupRepository.findById(id).orElseThrow(() -> new EntityIdNotFoundException("User group with id " + id + " not found.", 0L));
        ArrayList<BasicUserDTO> addedUsers = new ArrayList<>();
        for (Long userId : userIds) {
            User user = userService.findById(userId);
            userGroup.addUser(user);
            addedUsers.add(new BasicUserDTO(user));
            userService.updateUser(user);
        }
        userGroupRepository.save(userGroup);
        return addedUsers;
    }

}
