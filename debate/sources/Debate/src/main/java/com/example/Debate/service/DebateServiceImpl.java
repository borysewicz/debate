package com.example.Debate.service;

import com.example.Debate.common.api.SortingType;
import com.example.Debate.common.exception.BadRequestException;
import com.example.Debate.common.exception.ResourceNotFoundException;
import com.example.Debate.dto.request.AddOrUpdateDebateDto;

import com.example.Debate.dto.response.FullDebateResponseDto;
import com.example.Debate.model.Debate;
import com.example.Debate.repository.DebateRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public FullDebateResponseDto getDebateById(String id) {
        return modelMapper.map(
                debateRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Debate with id: " + id + " not found")),
                FullDebateResponseDto.class);
    }

    //TODO: Add other ways of sorting the debates, after comments and activities have been added to the platform
    @Override
    public List<FullDebateResponseDto> getDebates(SortingType sortingType, int limit, int page) {
        PageRequest request = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "creationDate"));
        return debateRepository.findAll(request).get()
                .map(mapEntity -> modelMapper.map(mapEntity, FullDebateResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FullDebateResponseDto addDebate(AddOrUpdateDebateDto debateDto, MultipartFile debateCover) {
        Debate debate = modelMapper.map(debateDto,Debate.class);
        if (debateCover != null){
            try {
                debate.setImage(new Binary(BsonBinarySubType.BINARY, debateCover.getBytes()));
            } catch (IOException e) {
                throw new BadRequestException("Server encountered error while processing image, try again");
            }
        }
        var saved = debateRepository.save(debate);
        return modelMapper.map(saved, FullDebateResponseDto.class);
    }

    @Override
    public Binary getDebateCover(String id) {
        var debate = debateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Debate  with id %s not found", id)));
        var img = debate.getImage();
        if(img == null){
            throw new ResourceNotFoundException(String.format("Debate with id %s has no cover image", id));
        }
        return img;
    }

    @Override
    public void delete(String id) {
        if(!debateRepository.existsById(id)){
            throw new ResourceNotFoundException("Debate with id: " + id + " not found");
        }
        debateRepository.deleteById(id);
    }

    @Override
    public void update(String id, AddOrUpdateDebateDto debateDto, MultipartFile debateCover) {
        var debate = debateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Debate with id: " + id + " not found"));
        debate.setTitle(debateDto.getTitle());
        debate.setContent(debateDto.getDescription());
        debate.setAllTags(debateDto.getAllTags());
        debate.setMainTags(debateDto.getMainTags());
        if (debateCover != null){
            try {
                debate.setImage(new Binary(BsonBinarySubType.BINARY, debateCover.getBytes()));
            } catch (IOException e) {
                throw new BadRequestException("Server encountered error while processing image, try again");
            }
        }
        debateRepository.save(debate);
    }

}
