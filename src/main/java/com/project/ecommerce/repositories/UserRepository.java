package com.project.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.models.Role;
import com.project.ecommerce.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findByUsername(String username);
	UserEntity findFirstByUsername(String username);
	List<UserEntity> findByRoles(List<Role> roles);
}
