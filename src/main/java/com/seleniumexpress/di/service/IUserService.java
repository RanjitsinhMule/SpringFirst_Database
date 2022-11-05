package com.seleniumexpress.di.service;

import java.util.List;
import java.util.Optional;

import com.seleniumexpress.di.ResponseEntity;
import com.seleniumexpress.di.dto.LoginDto;
import com.seleniumexpress.di.dto.LogoutDto;
import com.seleniumexpress.di.dto.UserDto;
import com.seleniumexpress.di.exception.UserException;
import com.seleniumexpress.di.model.EmailDetails;
import com.seleniumexpress.di.model.UserModel;


public interface IUserService {

	ResponseEntity add(UserDto user) throws UserException;

	List<UserModel> findAll();

	UserDto register(UserDto user) throws UserException;

	ResponseEntity getUserByLogin(LoginDto loginDto) throws UserException;

	void deleteById(int id);

	UserModel updateOneUser(UserModel user, int id);

	Optional<UserModel> getUserByName(String name);

	LoginDto getUserByLogin(String email) throws UserException;

	String loginUser(LoginDto loginDTO) throws UserException;

	UserDto updateByToken(UserDto user, String token) throws UserException;

	String logoutuser(String token) throws UserException;

	UserDto updateDataByEmail(UserDto userDTO, String email) throws UserException;

	UserDto findById(int id) throws UserException;

//	UserDto getUserByName(String name);
//
//	LoginDto getUserByLogin(String email, String password);
//
//	ResponseEntity getUserByLogin(LoginDto loginDto);
//
//	UserDto getByUserLogin(LoginDto loginDto);
//
//	UserModel updateOneUser(UserModel user, int id);
//
//	void deleteById(int id);
//
//	UserDto findById(int id);
//
//	List<UserModel> findAll();
//
//	ResponseEntity add(UserModel user);
//
//	String getToken(LoginDto loginDto);
//	
//	UserDto getUserByLogin(String token);
//
//	UserModel updateUserByToken(UserModel user, String token);
//	
//	LogoutDto logoutUser(String token);
//
//	LogoutDto logoutByToken(String token);
//
//	String sendSimpleMail(EmailDetails details);
//
//	UserModel updateByToken(UserModel user, String token) throws UserException;
//
//	String logoutuser(String token) throws UserException;
//
//	String loginUser(LoginDto loginDTO) throws UserException;
//
//	UserDto updateDataByEmail(UserDto userDTO, String email) throws UserException;
//
//	UserDto register(UserDto user) throws UserException;
		
}