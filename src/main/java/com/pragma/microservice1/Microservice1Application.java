package com.pragma.microservice1;

import com.pragma.microservice1.domain.api.IRoleServicePort;
import com.pragma.microservice1.domain.api.IUserServicePort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Microservice1Application implements CommandLineRunner {

	private final IRoleServicePort roleServicePort;

	public Microservice1Application (IUserServicePort userServicePort, IRoleServicePort roleServicePort ) {
		this.roleServicePort = roleServicePort;
	}

	public static void main(String[] args) {
		SpringApplication.run(Microservice1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		roleServicePort.createDefaultRoles("ADMIN");
		roleServicePort.createDefaultRoles("OWNER");
		roleServicePort.createDefaultRoles("EMPLOYEE");
		roleServicePort.createDefaultRoles("CLIENT");
	}
}
