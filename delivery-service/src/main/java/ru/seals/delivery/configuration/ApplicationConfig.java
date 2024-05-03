package ru.seals.delivery.configuration;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.seals.delivery.dto.OrderSaveDTO;
import ru.seals.delivery.model.Order;
import ru.seals.delivery.model.Product;
import ru.seals.delivery.util.Converter;

@Configuration
@EnableFeignClients(basePackages = {"ru.seals.delivery.controller.clients"})
@RequiredArgsConstructor
public class ApplicationConfig {
    private final Converter converter;

    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<OrderSaveDTO, Order>() {
            @Override
            protected void configure() {
                map().setProducts(converter.mapListDTOIntoEntity(source.getProducts(), Product.class));
            }
        });
        return new ModelMapper();
    }
}
