package br.com.builders.mvp.andresa.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.builders.mvp.andresa.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
