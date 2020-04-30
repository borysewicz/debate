package com.example.Debate.config;

import com.example.Debate.dto.request.AddOrUpdateDebateDto;
import com.example.Debate.model.Debate;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class MappingConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        var mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        mapper.createTypeMap(AddOrUpdateDebateDto.class, Debate.class).addMapping(AddOrUpdateDebateDto::getDescription, Debate::setContent);
        return mapper;
    }


}
