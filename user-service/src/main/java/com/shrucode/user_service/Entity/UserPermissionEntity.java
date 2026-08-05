package com.shrucode.user_service.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_permission")
public class UserPermissionEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
