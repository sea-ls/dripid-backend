package ru.seals;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.javamoney.moneta.Money;
import ru.seals.delivery.dto.MoneyDTO;

@SpringBootApplication
public class DeliveryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeliveryServiceApplication.class, args);
	}

	static {
		SpringDocUtils.getConfig().replaceWithClass(Money.class, MoneyDTO.class);
	}
}
