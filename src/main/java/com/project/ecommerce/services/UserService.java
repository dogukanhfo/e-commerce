package com.project.ecommerce.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.ecommerce.models.Role;
import com.project.ecommerce.models.UserEntity;
import com.project.ecommerce.repositories.RoleRepository;
import com.project.ecommerce.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void saveUser(UserEntity userEntity) {
		UserEntity user = new UserEntity();
		user.setUsername(userEntity.getUsername());
		user.setEmail(userEntity.getEmail());
		user.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		user.setName(userEntity.getName());
		Role role = roleRepository.findByName("USER");
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
	}
}
