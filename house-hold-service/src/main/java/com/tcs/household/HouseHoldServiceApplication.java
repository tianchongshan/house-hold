package com.tcs.household;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.tcs"})
@MapperScan({"com.tcs.household"})
public class HouseHoldServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseHoldServiceApplication.class, args);
	}

}
