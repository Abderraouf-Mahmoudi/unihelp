package unihelp.example.offres.dto;


import com.unihelp.user.entities.UserRole;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter


public class UserDTO {


    private Long id;
    private String password;
    private String email;
    private  String userRole;



}
