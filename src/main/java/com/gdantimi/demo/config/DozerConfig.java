package com.gdantimi.demo.config;

import com.gdantimi.demo.mapper.UserConverter;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
public class DozerConfig {

    @Autowired
    private UserConverter userConverter;

    @Bean
    public DozerBeanMapperFactoryBean dozerBeanMapper(@Value("classpath*:dozer*.xml") Resource[] mappings){
        DozerBeanMapperFactoryBean dozerBeanMapper = new DozerBeanMapperFactoryBean();
        dozerBeanMapper.setMappingFiles(mappings);
        return dozerBeanMapper;
    }
}
