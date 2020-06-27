package ru.alfabattle.kukolev.config;

import ma.glasnost.orika.*;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.*;
import ru.alfabattle.kukolev.domain.Branch;
import ru.alfabattle.kukolev.dto.BranchDto;

@Configuration
public class OrikaConfig {

    @Bean
    public MapperFacade mapperFacade() {
        var mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Branch.class, BranchDto.class)
                .byDefault()
                .register();
        return mapperFactory.getMapperFacade();
    }
}
