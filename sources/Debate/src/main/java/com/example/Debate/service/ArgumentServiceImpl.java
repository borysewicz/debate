package com.example.Debate.service;

import com.example.Debate.dto.request.AddOrUpdateArgumentDto;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArgumentServiceImpl implements ArgumentService{
    private ArgumentRepository argumentRepository;
    private ModelMapper modelMapper;

    public ArgumentServiceImpl(ArgumentRepository argumentRepository, ModelMapper modelMapper) {
        this.argumentRepository = argumentRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ArgumentResponse> getArgumentsForDebate(String debateId, int limit, int page, Optional<String> userId) {
        var pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "rating"));
        return Stream.concat(
                argumentRepository.getArgumentsByAttitudeAndDebateId(Attitude.POSITIVE,debateId, pageRequest).get(),
                argumentRepository.getArgumentsByAttitudeAndDebateId(Attitude.NEGATIVE,debateId, pageRequest).get()
        ).map(argumentEntity -> mapToArgumentResponse(argumentEntity, userId))
                .collect(Collectors.toList());
    }

    @Override
    public ArgumentResponse addArgument(AddOrUpdateArgumentDto argumentDto, String userId) {
        var argument = new Argument(
                argumentDto.getTitle(), argumentDto.getAttitude(), argumentDto.getDebateId(),
                argumentDto.getContent(), userId
        );
        argument.ratePost(userId, Vote.POSITIVE);
        return mapToArgumentResponse(argumentRepository.save(argument), Optional.of(userId));
    }

    @Override
    public ArgumentResponse getArgumentById(String id, Optional<String> userId) {
        return argumentRepository.findById(id).map(arg -> mapToArgumentResponse(arg, userId))
                .orElseThrow(ResourceNotFoundException::new);
    }

    private ArgumentResponse mapToArgumentResponse(Argument argument, Optional<String> userId){
        return new ArgumentResponse(
                argument.get_id(),
                argument.getTitle(),
                argument.getContent(),
                argument.getUserVote(userId).toString(),
                argument.getAttitude().toString(),
                argument.getAuthor(),
                argument.getUpvotes(),
                argument.getDownvotes(),
                argument.getLastEditionTime(),
                argument.getDebateId()
        );
    }

}
