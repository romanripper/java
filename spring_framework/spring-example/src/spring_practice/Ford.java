package spring_practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

public class Ford implements Car {
	
	@Value("${model}")
	private String model;
	
	@Value("${year}")
	private int year;
	
	@Autowired
	@Qualifier("v8_Engine")
	private Engine engine;

	@Override
	public String getEngineSound() {
		return engine.getSound();
	}

	@Override
	public String toString() {
		return "Ford [model=" + model + ", year=" + year + "]";
	}

}
