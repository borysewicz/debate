package com.example.Debate.service;

import com.example.Debate.dto.DebateDto;
import com.example.Debate.model.Debate;
import com.example.Debate.repository.DebateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DebateServiceImpl implements DebateService{
    private DebateRepository debateRepository;
    private ModelMapper modelMapper;

    public DebateServiceImpl(DebateRepository debateRepository, ModelMapper modelMapper) {
        this.debateRepository = debateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DebateDto getDebateById(String id) {
        Debate debate = debateRepository.findById(id).get();
        return modelMapper.map(debate,DebateDto.class);
    }

    @Override
    public boolean addDebate(DebateDto debateDto) {
        Debate debate = modelMapper.map(debateDto,Debate.class);
        debateRepository.save(debate);
        return true;
    }
}
