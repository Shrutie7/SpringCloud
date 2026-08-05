package com.shrucode.user_service.Repository;

import com.shrucode.user_service.Entity.UserRegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegisterRepository extends JpaRepository<UserRegisterEntity,Long> {
    Optional<UserDetails> findByUserName(String userName);
}