package com.example.Debate.service;

import com.example.Debate.common.exception.UnauthorizedAccessException;
import com.example.Debate.dto.request.AddOrUpdateArgumentDto;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.ArgumentResponse;
import com.example.Debate.model.Argument;
import com.example.Debate.model.Attitude;
import com.example.Debate.model.Vote;
import com.example.Debate.repository.ArgumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArgumentServiceImpl implements ArgumentService {
    private ArgumentRepository argumentRepository;
    private ModelMapper modelMapper;

    public ArgumentServiceImpl(ArgumentRepository argumentRepository, ModelMapper modelMapper) {
        this.argumentRepository = argumentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ArgumentResponse> getArgumentsForDebate(String debateId, int limit, int page, Optional<String> userLogin) {
        var pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "rating"));
        return Stream.concat(
                argumentRepository.getArgumentsByAttitudeAndDebateId(Attitude.POSITIVE, debateId, pageRequest).get(),
                argumentRepository.getArgumentsByAttitudeAndDebateId(Attitude.NEGATIVE, debateId, pageRequest).get()
        ).map(argumentEntity -> modelMapper.map(argumentEntity, ArgumentResponse.class)
                    .withUserVote(argumentEntity.getUserVote(userLogin)))
                .collect(Collectors.toList());
    }

    @Override
    public ArgumentResponse addArgument(AddOrUpdateArgumentDto argumentDto, String userId) {
        var argument = new Argument(
                argumentDto.getTitle(), argumentDto.getAttitude(), argumentDto.getDebateId(),
                argumentDto.getContent(), userId
        );
        argument.ratePost(userId, Vote.POSITIVE);
        return modelMapper.map(argumentRepository.save(argument), ArgumentResponse.class)
                .withUserVote(Vote.POSITIVE);
    }

    @Override
    public ArgumentResponse getArgumentById(String id, Optional<String> userLogin) {
        return argumentRepository.findById(id).map(entity -> modelMapper.map(entity, ArgumentResponse.class)
                .withUserVote(entity.getUserVote(userLogin)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteArgument(String id, Principal principal) {
        var argument = argumentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Argument with id: " + id + " not found"));
        if (argument.isAuthorized(principal)) {
            argumentRepository.deleteById(id);
        } else throw new UnauthorizedAccessException("You are not allowed to modify this resource");
    }

    @Override
    public void updateArgument(String id, AddOrUpdateArgumentDto argumentDto, Principal principal) {
        var argument = argumentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Argument with id: " + id + " not found"));
        if (argument.isAuthorized(principal)){
            argument.saveEdit();
            argument.setTitle(argumentDto.getTitle());
            argument.setContent(argumentDto.getContent());
            argumentRepository.save(argument);
        }
        else throw new UnauthorizedAccessException("You are not allowed to modify this resource");
    }

    @Override
    public ActivityHistoryResponse getArgumentHistory(String id) {
        var debate = argumentRepository.findById(id).orElseThrow(() ->
                new com.example.Debate.common.exception.ResourceNotFoundException("Debate with id : " + id + " + not found"));
        return new ActivityHistoryResponse(debate.getEditHistory());
    }

}
