package com.cliche.cliche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import com.cliche.cliche.domain.user.AuthenticationDTO;
import com.cliche.cliche.domain.user.LoginResponseDTO;
import com.cliche.cliche.domain.user.RegisterDTO;
import com.cliche.cliche.domain.user.User;
import com.cliche.cliche.infra.security.TokenService;
import com.cliche.cliche.repository.UserRepository;

@Controller
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @QueryMapping
    public LoginResponseDTO login(@Argument("input") AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(),
                authenticationDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }

    @MutationMapping
    public User register(@Argument("input") RegisterDTO registerDTO) throws Exception {
        System.out.println(registerDTO.email() + "" + registerDTO.password());
        if (!userRepository.findByEmailLike(registerDTO.email()).isEmpty()) {
            throw new Exception("Username in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.email(), encryptedPassword, registerDTO.userRole());

        return userRepository.save(newUser);
    }
}
