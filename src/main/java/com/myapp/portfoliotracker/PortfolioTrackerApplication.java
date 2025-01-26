package com.myapp.portfoliotracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.myapp.portfoliotracker.security.JwtUtil;

@SpringBootApplication

public class PortfolioTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioTrackerApplication.class, args);
		
		
		
	}
	 @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();

}
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 
	  
	 
}