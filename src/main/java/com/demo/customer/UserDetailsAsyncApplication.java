package com.demo.customer;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserDetailsAsyncApplication {
	


	public static void main(String[] args) {
		SpringApplication.run(UserDetailsAsyncApplication.class, args);
		
		
	}

	@Bean
	public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	}
	
	
	

}
