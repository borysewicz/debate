package com.example.Debate.common.api;

import com.example.Debate.model.enums.Vote;
import org.springframework.core.convert.converter.Converter;

public class StringToVoteConverter implements Converter<String, Vote> {
    @Override
    public Vote convert(String source) {
        return Vote.valueOf(source.toUpperCase());
    }
}
