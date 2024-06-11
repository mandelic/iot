package com.iot.detector.service.impl;

import com.iot.detector.controller.dto.LoginDTO;
import com.iot.detector.controller.dto.TokenDTO;
import com.iot.detector.entity.User;
import com.iot.detector.entity.UserGroup;
import com.iot.detector.exceptions.CustomMessageException;
import com.iot.detector.exceptions.EntityIdNotFoundException;
import com.iot.detector.exceptions.UserIdNotFoundException;
import com.iot.detector.repository.UserGroupRepository;
import com.iot.detector.repository.UserRepository;
import com.iot.detector.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserGroupRepository userGroupRepository;

    public List<User> findAll() {
        List<User> all = userRepository.findAll();
        all.removeAll(userRepository.findAllByRole("ROLE_DELETED"));
        return all;
    }

    public List<User> findAllDeleted() {
        return userRepository.findAllByRole("ROLE_DELETED");
    }

    public User addUserGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException(userId));
        UserGroup userGroup = userGroupRepository.findById(groupId).orElseThrow(() -> new EntityIdNotFoundException("UserGroup", groupId));
        user.addUserGroup(userGroup);
        return userRepository.save(user);
    }

    public User addUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public TokenDTO verifyLogin(LoginDTO loginDTO) {
        if (userRepository.existsByEmail(loginDTO.getEmail())){
            User user = userRepository.findByEmail(loginDTO.getEmail());
            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) return new TokenDTO("User authenticated.", user.getRole(), "", user.getId());
        }
        return new TokenDTO("Wrong email or password.", "", "", null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException(id));
    }

    @Override
    public User changeRole(Long id, String role) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException(id));
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN", "ROLE_DELETED");
        if (!roles.contains(role)) throw new CustomMessageException("Role does not exist.", 2);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException(id));
        user.setRole("ROLE_DELETED");
        return userRepository.save(user);
    }

}