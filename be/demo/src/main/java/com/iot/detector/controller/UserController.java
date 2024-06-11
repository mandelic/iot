package com.iot.detector.controller;

import com.iot.detector.controller.dto.RoleDTO;
import com.iot.detector.controller.dto.UserDTO;
import com.iot.detector.controller.dto.UserDetailsDTO;
import com.iot.detector.entity.User;
import com.iot.detector.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/v1/users")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll().stream().map(UserDetailsDTO::new).collect(Collectors.toList()));
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/deleted")
    public ResponseEntity<List<UserDetailsDTO>> findAllDeleted() {
        return ResponseEntity.ok(userService.findAllDeleted().stream().map(UserDetailsDTO::new).collect(Collectors.toList()));
    }

    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new UserDetailsDTO(userService.findById(id)));
    }


    @PostMapping
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
        User newUser = userService.addUser(new User(userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(newUser));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/change-role")
    public ResponseEntity<UserDTO> changeUserRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(new UserDTO(userService.changeRole(id, roleDTO.getRole())));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(new UserDetailsDTO(userService.deleteUser(id)));
    }

    @PostMapping("/{id}/group/{groupId}")
    public ResponseEntity<UserDetailsDTO> addGroup(@PathVariable Long id, @PathVariable Long groupId) {
        return ResponseEntity.ok(new UserDetailsDTO(userService.addUserGroup(id, groupId)));
    }

}