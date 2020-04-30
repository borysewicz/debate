package com.example.Debate.dto.response;


import com.example.Debate.model.Role;
import lombok.Value;

@Value
public class UserDto {
    private String login;
    private String eMail;
    private Role role;

}
