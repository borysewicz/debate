package com.example.Debate.service;


import com.example.Debate.dto.PunishmentDto;

public interface PunishmentService
{
    public PunishmentDto getPunishmentById(String id);
    public boolean addPunishment(PunishmentDto punishmentDto);
}
