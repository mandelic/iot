package com.iot.detector.controller;

import com.iot.detector.controller.dto.LoginDTO;
import com.iot.detector.controller.dto.TokenDTO;
import com.iot.detector.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/v1/login")
@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<TokenDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        TokenDTO tokenDTO = userService.verifyLogin(loginDTO);
        if (tokenDTO.getMessage().equals("Wrong email or password.")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(tokenDTO);
        String token = getJWTToken(loginDTO.getEmail(), tokenDTO.getRole());
        tokenDTO.setToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
    }

    private String getJWTToken(String username, String role) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(role);

        String token = Jwts
                .builder()
                .setId("iotJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256,
                        "ZY58fu2rj9edGVGtxMjiPrBHhzGB59r1E3rHEW1q810".getBytes()).compact();

        return "Bearer " + token;
    }


}