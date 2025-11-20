package com.demo.spring_boot.controller.profile;

import com.demo.spring_boot.exception.ResourceNotFoundException;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.response.ApiResponse;
import com.demo.spring_boot.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProfileRestController {

    private final AuthorRepository repository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ProfileRestController(AuthorRepository repository, JwtTokenProvider jwtTokenProvider) {
        this.repository = repository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/profile")
    ResponseEntity<?> profile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        }
        Claims claims = jwtTokenProvider.getClaimsFromJWT(token);
        final String email = claims.get("email")
                                   .toString();
        return repository.findByEmail(email)
                         .map(data -> ResponseEntity.ok(ApiResponse.builder()
                                                                   .data(data)
                                                                   .build()))
                         .orElseThrow(() -> new ResourceNotFoundException("Email", email));

    }
}
