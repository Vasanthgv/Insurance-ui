package com.insurance.gateway;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
	@Bean
	ApplicationRunner r(ApplicationContext ctx) {
	  return args -> ctx.getBeansOfType(GatewayFilterFactory.class).keySet().forEach(System.out::println);
	}
//	@Bean
//	ApplicationRunner runner(ApplicationContext ctx) {
//	  return args -> ctx.getBeansOfType(org.springframework.cloud.gateway.filter.GatewayFilterFactory.class)
//	                    .keySet().forEach(System.out::println);
//	}

}
