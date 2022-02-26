package com.shashi.wirelesscardemo.application;

import com.shashi.wirelesscardemo.utility.DataFiller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = "com.shashi.wirelesscardemo")
@EnableJpaRepositories(basePackages = "com.shashi.wirelesscardemo.repository")
@EntityScan(basePackages = "com.shashi.wirelesscardemo")
@SpringBootApplication
public class WirelesscardemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WirelesscardemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		DataFiller filler=new DataFiller();
//		filler.fillData();
	}
}
