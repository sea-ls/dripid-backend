package ru.seals.delivery.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.seals.delivery.util.MonetaryAmountConverter;

@Configuration
@EnableFeignClients(basePackages = {"ru.seals.delivery.controller.clients"})
public class ApplicationConfig {
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    @Bean
    public MonetaryAmountConverter samplePropertyConverter(){
        return new MonetaryAmountConverter();
    }
}
