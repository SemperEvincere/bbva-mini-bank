package com.bbva.minibank;

import com.bbva.minibank.application.repository.IClientRepository;
import com.bbva.minibank.domain.models.Client;
import com.bbva.minibank.infrastructure.mappers.ClientMapper;
import com.bbva.minibank.infrastructure.repositories.ClientRepositoryImpl;
import com.bbva.minibank.infrastructure.repositories.springdatajpa.IClientSpringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiniBankApplication {


	public static void main(String[] args) {
		SpringApplication.run(MiniBankApplication.class, args);
	}

}
