package br.com.builders.mvp.andresa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MVPApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MVPApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
