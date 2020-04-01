package com.example.Debate.dto;

import com.example.Debate.model.Post;
import com.example.Debate.model.Punishment;
import com.example.Debate.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String login;
    private String eMail;
    private Role role;

}
