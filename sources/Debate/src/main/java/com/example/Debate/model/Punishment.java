package com.example.Debate.model;

import com.example.Debate.model.enums.PunishmentSeverity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Punishment {

    @Id
    private String _id;
    private long beginDate;
    private long endDate;
    private PunishmentSeverity severity;
    @DBRef
    private User sentenced;
    @DBRef
    private User prosecutor;


}
