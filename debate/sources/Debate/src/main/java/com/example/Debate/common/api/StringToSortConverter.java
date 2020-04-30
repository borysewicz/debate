package com.example.Debate.common.api;

import org.springframework.core.convert.converter.Converter;

public class StringToSortConverter implements Converter<String, SortingType> {

    @Override
    public SortingType convert(String source) {
        return SortingType.valueOf(source.toUpperCase());
    }
}
