package com.enets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;

@SpringBootApplication
public class EnetsPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnetsPocApplication.class, args);
	}
	
	public Gson gson() {
		return new Gson();
	}
	
}

