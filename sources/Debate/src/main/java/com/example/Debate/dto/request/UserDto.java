package com.example.Debate.dto.request;


import com.example.Debate.model.enums.Role;
import lombok.Value;
import lombok.With;

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

    public void setPassword(String password) {
        this.password = password;
    }
}
