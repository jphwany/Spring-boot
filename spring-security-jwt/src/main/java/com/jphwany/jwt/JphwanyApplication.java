package com.jphwany.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JphwanyApplication {

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	} // BCryptPasswordEncoder 빈 등록
	// 순환참조 막기 위한 JphwanyApplication 에 @Bean 등록

	public static void main(String[] args) {
		SpringApplication.run(JphwanyApplication.class, args);
	}

}
