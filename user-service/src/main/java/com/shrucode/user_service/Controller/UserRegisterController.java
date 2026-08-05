package com.shrucode.user_service.Controller;

import com.netflix.discovery.converters.Auto;
import com.shrucode.user_service.DTO.UserRegisterDTO;
import com.shrucode.user_service.Entity.UserRegisterEntity;
import com.shrucode.user_service.Repository.UserRegisterRepository;
import com.shrucode.user_service.Service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRegisterController {
    @Autowired
    public UserRegisterService userRegisterService;

    @Autowired
    public UserRegisterRepository userRegisterRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String defaultHomePageMethod(){
        return "Hello you r logged in";
    }

    @PostMapping("/user-register")
    public ResponseEntity<String> save(@RequestBody UserRegisterEntity userRegisterEntity){
        //HASH PASSWORD BEFORE SAVING
        userRegisterEntity.setPassword(passwordEncoder.encode(userRegisterEntity.getPassword()));

        userRegisterService.saveUser(userRegisterEntity);

        return ResponseEntity.ok("user created successfully");
    }

    @GetMapping("/details")
    public List<UserRegisterDTO> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRegisterService.getDetails();
    }

}