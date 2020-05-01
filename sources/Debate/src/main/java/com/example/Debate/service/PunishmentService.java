package com.example.Debate.service;


import com.example.Debate.dto.response.PunishmentDto;

public interface PunishmentService
{
    PunishmentDto getPunishmentById(String id);
    boolean addPunishment(PunishmentDto punishmentDto);
}
