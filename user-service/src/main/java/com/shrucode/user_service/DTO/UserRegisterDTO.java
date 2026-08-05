package com.shrucode.user_service.DTO;


import com.shrucode.user_service.Entity.UserRegisterEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    public String username;
    public String role;
    public String email;
    public String provider;

    public static UserRegisterDTO fromEntity(UserRegisterEntity entity){
        return new UserRegisterDTO(
                entity.getUsername(),
                entity.getRole(),
                entity.getEmail(),
                entity.getProvider()
        );
    }

}
