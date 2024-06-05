package com.iot.detector.service;


import com.iot.detector.controller.dto.LoginDTO;
import com.iot.detector.controller.dto.TokenDTO;
import com.iot.detector.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User addUser(User user);
    TokenDTO verifyLogin(LoginDTO loginDTO);
    User findById(Long id);
    User changeRole(Long id, String role);
    User deleteUser(Long id);
    List<User> findAllDeleted();
}