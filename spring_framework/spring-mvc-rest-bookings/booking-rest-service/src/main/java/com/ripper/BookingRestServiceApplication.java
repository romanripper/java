package com.ripper;

import javax.annotation.PostConstruct;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ripper.deserializer.StringWithoutSpaceDeserializer;

@SpringBootApplication
public class BookingRestServiceApplication {
	
	public static void main(String[] args) {
		// SpringApplication.run(BookingRestServiceApplication.class, args);

		SpringApplicationBuilder builder = new SpringApplicationBuilder(BookingRestServiceApplication.class);
		builder.headless(false);
		builder.run(args);
		
	}

	@Bean
	@Autowired
	public HttpMessageConverter<Object> httpMessageConverter(MappingJackson2HttpMessageConverter converter) {
		
		ObjectMapper mapper = converter.getObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(String.class, new StringWithoutSpaceDeserializer(String.class));
		mapper.registerModule(module);

		return converter;
	}

	@PostConstruct
	public void startDBManager() {
		DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:mydb", "--user", "sa", "--password", "" });
	}

}