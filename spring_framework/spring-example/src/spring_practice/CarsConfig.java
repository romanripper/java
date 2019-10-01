package spring_practice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:cars.properties")
public class CarsConfig {

	@Bean
	public Engine v8_Engine() {
		return new V8_Engine();
	}
	
	@Bean
	public Car ford() {
		return new Ford();
	}
}
