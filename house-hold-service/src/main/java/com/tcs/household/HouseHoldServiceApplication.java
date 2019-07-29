package com.tcs.household;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication(scanBasePackages = {"com.tcs"})
@MapperScan({"com.tcs.household"})
public class HouseHoldServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseHoldServiceApplication.class, args);
	}

}
