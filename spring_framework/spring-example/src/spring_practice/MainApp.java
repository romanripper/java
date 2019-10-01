package spring_practice;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext loggerContext = 
				new AnnotationConfigApplicationContext(MyLoggerConfig.class);
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(CarsConfig.class);
		
		Ford car = context.getBean("ford", Ford.class);
		
		System.out.println("\n" + car);
		System.out.println(car.getEngineSound() + "\n");
		
		context.close();
		loggerContext.close();
	}
}
