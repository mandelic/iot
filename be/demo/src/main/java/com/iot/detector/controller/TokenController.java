package com.iot.detector.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/token")
@CrossOrigin("*")
public class TokenController {

    @GetMapping
    public String getToken() {
        return getJWTToken();
    }

    private String getJWTToken() {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ADMIN");

        String token = Jwts
                .builder()
                .setId("iotJWT")
                .setSubject("admin")
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
