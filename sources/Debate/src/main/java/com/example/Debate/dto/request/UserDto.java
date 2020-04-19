package com.example.Debate.dto.request;


import com.example.Debate.model.Role;

public class UserDto {
    private String login;
    private String password;
    private String email;
    private Role role;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

}
