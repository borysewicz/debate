package com.example.Debate.dto.response;


import com.example.Debate.model.enums.PunishmentSeverity;
import com.example.Debate.model.User;
import lombok.Value;

@Value
public class PunishmentDto {
    private long beginDate;
    private long endDate;
    private PunishmentSeverity severity;
    private User sentenced;
    private User prosecutor;
}
