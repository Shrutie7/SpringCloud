package com.shrucode.user_service.Service;


import com.shrucode.user_service.Entity.UserLoginEntity;
import com.shrucode.user_service.Repository.UserLoginRepository;
import com.shrucode.user_service.Repository.UserRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserLoginService implements UserDetailsService {

    @Autowired
    private UserLoginRepository userLoginRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         return userLoginRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("User not found "));
    }

    public UserDetails saveUser(UserLoginEntity userLoginEntity){
         return userLoginRepository.save(userLoginEntity);
    }

    public UserLoginEntity fetchUserDetails(Long id){
        return userLoginRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
