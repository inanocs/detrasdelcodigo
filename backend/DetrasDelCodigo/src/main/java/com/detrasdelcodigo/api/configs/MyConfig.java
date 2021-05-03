package com.detrasdelcodigo.api.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig {

	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		
		return new WebMvcConfigurer() {
		
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				
				registry.addMapping("/**").allowedOriginPatterns("http://localhost:9001")
				.allowedMethods("GET","POST","PUT","DELETE").maxAge(1800);
			}
		};
	}

}
