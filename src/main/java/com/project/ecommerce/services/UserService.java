package com.project.ecommerce.services;

import java.util.Arrays;
import java.util.List;

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
	
	public void saveAdmin(UserEntity userEntity) {
		UserEntity user = new UserEntity();
		user.setUsername(userEntity.getUsername());
		user.setEmail(userEntity.getEmail());
		user.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		user.setName(userEntity.getName());
		Role role = roleRepository.findByName("ADMIN");
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
	}
	
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
	
	public UserEntity getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public UserEntity getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public List<UserEntity> getUsersByRole(String roleName) {
		Role role = roleRepository.findByName(roleName);
		return userRepository.findByRoles(Arrays.asList(role));
	}
	
	public void updateUser(UserEntity user, Long id) {
		UserEntity u = userRepository.findById(id).orElse(null);
		
		if (u != null) {
			u.setUsername(user.getUsername());
			u.setEmail(user.getEmail());
			u.setPassword(user.getPassword());
			u.setName(user.getName());
			userRepository.save(u);
		} else {
			System.out.println("There is no user matches with given id.");
		}
	}
	
	public void deleteUser(Long id) {
		UserEntity user = userRepository.findById(id).orElse(null);
		
		user.getRoles().clear();
		userRepository.delete(user);
	}
}
