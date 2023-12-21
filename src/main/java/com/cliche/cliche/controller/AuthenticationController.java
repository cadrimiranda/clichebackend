package com.cliche.cliche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import com.cliche.cliche.domain.user.AuthenticationDTO;
import com.cliche.cliche.domain.user.RegisterDTO;
import com.cliche.cliche.domain.user.User;
import com.cliche.cliche.repository.UserRepository;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @MutationMapping
    public String login(@Argument("input") AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(),
                authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return authenticationDTO.email();
    }

    @MutationMapping
    public User register(@Argument("input") RegisterDTO registerDTO) throws Exception {
        if (this.userRepository.findByEmailLike(registerDTO.email()) != null) {
            throw new Exception("Username in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.email(), encryptedPassword, registerDTO.role());

        return this.userRepository.save(newUser);
    }
}