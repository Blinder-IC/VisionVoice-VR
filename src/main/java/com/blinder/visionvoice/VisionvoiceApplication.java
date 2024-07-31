package com.blinder.visionvoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.blinder.visionvoice")
public class VisionvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisionvoiceApplication.class, args);
	}

}
