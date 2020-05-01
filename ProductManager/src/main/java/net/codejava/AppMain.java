package net.codejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:application.properties"})
public class AppMain {
	public static void main(String[] args) {
		SpringApplication.run(AppMain.class, args);
	}
}
