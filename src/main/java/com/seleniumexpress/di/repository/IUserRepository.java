package com.seleniumexpress.di.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seleniumexpress.di.model.UserModel;


public interface IUserRepository extends JpaRepository<UserModel, Integer> {

	Optional<UserModel> findByFirstName(String name);
	
	Optional<UserModel> findByEmail(String email);

	Optional<UserModel> findByEmailAndPassword(String email, String password);

	Optional<UserModel> findByPassword(String password);
}
