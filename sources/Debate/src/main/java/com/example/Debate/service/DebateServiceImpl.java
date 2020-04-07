package com.example.Debate.service;

import com.example.Debate.common.exception.ResourceNotFoundException;
import com.example.Debate.dto.DebateDto;
import com.example.Debate.model.Debate;
import com.example.Debate.repository.DebateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DebateServiceImpl implements DebateService{
    private DebateRepository debateRepository;
    private ModelMapper modelMapper;

    @Autowired
    public DebateServiceImpl(DebateRepository debateRepository, ModelMapper modelMapper) {
        this.debateRepository = debateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DebateDto getDebateById(String id) {
        return modelMapper.map(
                debateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Debate with id: " + id + " not found")),
                DebateDto.class);
    }

    @Override
    public List<DebateDto> getAllDebates() {
        return debateRepository.findAll().stream().map(mapEntity -> modelMapper.map(mapEntity, DebateDto.class)).collect(Collectors.toList());
    }

    @Override
    public boolean addDebate(DebateDto debateDto) {
        Debate debate = modelMapper.map(debateDto,Debate.class);
        debateRepository.save(debate);
        return true;
    }
}
