package com.example.Debate.service;

import com.example.Debate.dto.response.DebateDto;
import com.example.Debate.model.Debate;
import com.example.Debate.repository.DebateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Debate> debateOptional = debateRepository.findById(id);
        return debateOptional.isEmpty() ? null : modelMapper.map(debateOptional.get(),DebateDto.class);
    }

    @Override
    public List<DebateDto> getAllDebates() {
        List<Debate> debateList = debateRepository.findAll();
        List<DebateDto> debateDtoList = new ArrayList<>();
        if(debateList.size() != 0) {
            for (Debate debate : debateList) {
                debateDtoList.add(modelMapper.map(debate, DebateDto.class));
            }
        }
        return debateDtoList;
    }

    @Override
    public boolean addDebate(DebateDto debateDto) {
        Debate debate = modelMapper.map(debateDto,Debate.class);
        debateRepository.save(debate);
        return true;
    }
}
