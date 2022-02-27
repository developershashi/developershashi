package com.shashi.wirelesscardemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ComponentScan(basePackages = "com.shashi.wirelesscardemo")
@EnableJpaRepositories(basePackages = "com.shashi.wirelesscardemo.repositories")
@EntityScan(basePackages = "com.shashi.wirelesscardemo")
@SpringBootApplication
public class WirelesscardemoApplication  {

	public static void main(String[] args) {
		SpringApplication.run(WirelesscardemoApplication.class, args);
	}

}
