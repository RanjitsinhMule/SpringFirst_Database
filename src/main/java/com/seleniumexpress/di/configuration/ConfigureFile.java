package com.seleniumexpress.di.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureFile {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
