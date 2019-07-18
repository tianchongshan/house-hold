package com.tcs.household;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class HouseHoldServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseHoldServiceApplication.class, args);
	}

}
