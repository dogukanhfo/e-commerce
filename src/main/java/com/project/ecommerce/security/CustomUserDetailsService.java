package com.project.ecommerce.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.ecommerce.models.UserEntity;
import com.project.ecommerce.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findFirstByUsername(username);
		if(user != null) {
			User authUser = new User(
					user.getUsername(),
					user.getPassword(),
					user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
					.collect(Collectors.toList())
			);
			return authUser;
		} else {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
	}
}
