package com.shrucode.user_service.Service;

import com.shrucode.user_service.DTO.UserRegisterDTO;
import com.shrucode.user_service.Entity.UserRegisterEntity;
import com.shrucode.user_service.Repository.UserRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRegisterService implements UserDetailsService {

    @Autowired
    private UserRegisterRepository userRegisterRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRegisterRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("User name not found"));
    }

    public UserDetails saveUser(@RequestBody UserRegisterEntity userRegisterEntity){
         return userRegisterRepository.save(userRegisterEntity);
    }
    public List<UserRegisterDTO> getDetails(){
        return userRegisterRepository.findAll().stream().map(UserRegisterDTO::fromEntity).collect(Collectors.toList());
    }
}