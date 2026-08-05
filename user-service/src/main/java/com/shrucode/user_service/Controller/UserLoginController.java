package com.shrucode.user_service.Controller;


import com.shrucode.user_service.Entity.UserLoginEntity;
import com.shrucode.user_service.Repository.UserLoginRepository;
import com.shrucode.user_service.Service.UserLoginService;
import com.shrucode.user_service.Utils.ImmutableUserConfiguration;
import com.shrucode.user_service.Utils.UserConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserConfiguration userConfiguration;

    @Autowired
    private ImmutableUserConfiguration immutableUserConfiguration;

    @PostMapping("/user-login")
    public ResponseEntity<String> login(@RequestBody UserLoginEntity userLoginEntity){
        //Hash the password before saving
        userLoginEntity.setPassword(passwordEncoder.encode(userLoginEntity.getPassword()));

        userLoginService.saveUser(userLoginEntity);

        return ResponseEntity.ok("User registered successfully");
    }


    @PreAuthorize("#id == authentication.principal.id")
    @GetMapping("/users/{id}")
    public UserDetails fetchUserDetails(@PathVariable Long id){
        return userLoginService.fetchUserDetails(id);
    }


    @GetMapping("/user-config")
    public void getUserConfig(){
        System.out.println("name: "+ userConfiguration.getName());
        System.out.println("age: "+userConfiguration.getAge());
        System.out.println("active: "+userConfiguration.isActive());
        System.out.println("Courses: " + userConfiguration.getCourse().get(0).getName() +":"+userConfiguration.getCourse().get(1).getName());
        System.out.println("Roles: "+ userConfiguration.getRoles().get(0)+":"+userConfiguration.getRoles().get(1));
        System.out.println("preferences: "+userConfiguration.getPreferences().get("theme"));
        System.out.println("Location: "+userConfiguration.getLocations().get("home").getCity());


        System.out.println("immutable name: "+immutableUserConfiguration.getName());
        System.out.println("immutable age: "+immutableUserConfiguration.getAge());
        System.out.println("immutable active: "+immutableUserConfiguration.isActive());
    }

}
