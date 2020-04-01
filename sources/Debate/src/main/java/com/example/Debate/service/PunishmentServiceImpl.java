package com.example.Debate.service;

import com.example.Debate.dto.PunishmentDto;
import com.example.Debate.model.Punishment;
import com.example.Debate.repository.PunishmentRepository;
import org.modelmapper.ModelMapper;

@Service
public class PunishmentServiceImpl implements PunishmentService
{
    private PunishmentRepository punishmentRepository;
    private ModelMapper modelMapper;

    public PunishmentServiceImpl(PunishmentRepository punishmentRepository, ModelMapper modelMapper) {
        this.punishmentRepository = punishmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PunishmentDto getPunishmentById(String id) {
        Punishment punishment = punishmentRepository.findById(id).get();
        return modelMapper.map(punishment,PunishmentDto.class);
    }

    public boolean addPunishment(PunishmentDto punishmentDto)
    {
        Punishment punishment = modelMapper.map(punishmentDto,Punishment.class);
        punishmentRepository.save(punishment);
        return true;
    }

}
