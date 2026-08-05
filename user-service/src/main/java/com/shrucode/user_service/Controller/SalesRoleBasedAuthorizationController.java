package com.shrucode.user_service.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SalesRoleBasedAuthorizationController {

    @GetMapping("/sales-readRoleBased")
    @PreAuthorize("hasAuthority('SALES_READ)")
    public ResponseEntity<String> sales(){
        return ResponseEntity.ok("All sales fetched successfully");
    }
}
