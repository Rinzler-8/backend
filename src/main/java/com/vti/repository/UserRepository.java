package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.vti.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
//	public User findByUsername(String username);

	public boolean existsByUsername(String username);

	public boolean existsByEmail(String email);

	public User findByEmail(String email);
}
