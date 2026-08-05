package com.shrucode.user_service.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_login")
public class UserLoginEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    private String role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserPermissionEntity> permissions = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //Granted authorities inside authentication object got filled up bcoz of this itself has list [ROLE_USER , ORDER_READ, SALES_CREATE]
        // so we put ROLES AND PERMISSION in getAuthorities overrided method
        Set<GrantedAuthority> authorities = new HashSet<>();

        //ADD ROLE
        authorities.add(new SimpleGrantedAuthority(role));

        //ADD PERMISSION
        permissions.forEach(permission->
        authorities.add(new SimpleGrantedAuthority(permission.getName()))
        );

        return authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<UserPermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<UserPermissionEntity> permissions) {
        this.permissions = permissions;
    }
}
