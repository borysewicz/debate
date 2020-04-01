package com.example.Debate.dto;


import com.example.Debate.model.PunishmentSeverity;
import com.example.Debate.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
public class PunishmentDto {
    private long beginDate;
    private long endDate;
    private PunishmentSeverity severity;
    private User sentenced;
    private User prosecutor;
}
