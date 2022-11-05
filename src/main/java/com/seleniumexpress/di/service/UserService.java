package com.seleniumexpress.di.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.seleniumexpress.di.ResponseEntity;
import com.seleniumexpress.di.dto.LoginDto;
import com.seleniumexpress.di.dto.UserDto;
import com.seleniumexpress.di.exception.UserException;
import com.seleniumexpress.di.model.EmailDetails;
import com.seleniumexpress.di.model.UserModel;
import com.seleniumexpress.di.repository.IUserRepository;
import com.seleniumexpress.di.utility.JavaMailService;
import com.seleniumexpress.di.utility.JwtTokenUtil;

@Service
public class UserService implements IUserService {

	@Autowired
	IUserRepository userRepo;

	@Autowired
	ModelMapper model;

	@Autowired
	JwtTokenUtil tokenutil;

	@Autowired
	JavaMailService javaEmailService;

	@Override
	public ResponseEntity add(UserDto user) throws UserException {
		String userName = user.getFirstName();
		if (userRepo.findByFirstName(userName).isPresent()) {
			throw new UserException("User already exist");
		} else {
			String token = tokenutil.generateToken(user.getEmail(), user.getPassword());
			javaEmailService.sendSimpleMail(user.getEmail(), token, "Verification");
			// resttemp.getForObject("http://localhost:8080/token", String.class);
			UserModel userModel= model.map(user, UserModel.class);
			userModel.setIsVerified(true);
			userRepo.save(userModel);
			UserDto addUser = model.map(userModel, UserDto.class);
			return new ResponseEntity(addUser, "One user added");
		}
	}

	@Override
	public List<UserModel> findAll() {
		List<UserModel> users = userRepo.findAll();
		return users;
	}

	@Override
	public UserDto findById(int id) throws UserException {
		Optional<UserModel> findById = userRepo.findById(id);

		if (findById.isEmpty()) {
			throw new UserException("Object/user doesn't exist");
		}
		UserDto user = model.map(findById.get(), UserDto.class);
		return user;

	}

	@Override
	public UserDto register(UserDto user) throws UserException {
		Optional<UserModel> userModel = userRepo.findByEmail(user.getEmail());
		if (userModel.isPresent()) {
			throw new UserException(" User is exist");
		}
		UserModel registeredUser = model.map(user, UserModel.class);
		userRepo.save(registeredUser);
		System.out.println("Successfully registered");
		return user;
	}

	@Override
	public ResponseEntity getUserByLogin(LoginDto loginDto) throws UserException {
		System.out.println(loginDto.getEmail());
		System.out.println(loginDto.getPassword());

		if (userRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword()).isPresent()) {

			UserModel loginUser = userRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword()).get();
			UserDto loginUserDto = model.map(loginUser, UserDto.class);
			return new ResponseEntity(loginUserDto, "login succeded");

		} else {
			throw new UserException("User not found/Enter valid email id");
		}
	}

	@Override
	public void deleteById(int id) {
		userRepo.deleteById(id);
	}

	@Override
	public UserModel updateOneUser(UserModel user, int id) {
		if (userRepo.existsById(id)) {
			user.setId(id);
			userRepo.save(user);
		}
		return user;
	}

	@Override
	public Optional<UserModel> getUserByName(String name) {
		Optional<UserModel> findByName = userRepo.findByFirstName(name);
		return findByName;

	}

	@Override
	public LoginDto getUserByLogin(String email) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}
//	@Override
//	public UserModel getUserDetailsByToken(String token) {
//		int Userid = tokenutil.decodeToken(token);
//        Optional<UserModel> existingData = userRepo.findById(Userid);
//        if(existingData.isPresent()){
//            return existingData.get();
//        }else
//            return null;
//    }
//}

	@Override
	public String loginUser(LoginDto loginDTO) throws UserException {
		Optional<UserModel> userDetails = userRepo.findByEmail(loginDTO.getEmail());
		String token = tokenutil.generateToken(userDetails.get().getEmail(),userDetails.get().getPassword());
		if (userDetails != null) {
			if (userDetails.get().getPassword().equals(loginDTO.getPassword())) {
				userDetails.get().setLogin(true);
				userRepo.save(userDetails.get());
				return token;
			} else
				return "wrong password";
			// throw new UserException("Wrong Password!!!");
		} else {

			throw new UserException("Login Failed, Entered wrong email or password!!!");

		}
	}

	@Override
	public UserDto updateByToken(UserDto user, String token) throws UserException {
		LoginDto loggedInUser = tokenutil.deCode(token);
		UserModel users = model.map(user, UserModel.class);
		if (userRepo.findByEmailAndPassword(loggedInUser.getEmail(),loggedInUser.getPassword()).isPresent()&&userRepo.findByEmailAndPassword(loggedInUser.getEmail(),loggedInUser.getPassword()).get().isLogin()) {
			users.setId(userRepo.findByEmailAndPassword(loggedInUser.getEmail(), loggedInUser.getPassword()).get().getId());
			users.setIsVerified(true);
			users.setLogin(true);
			userRepo.save(users);
			return user;
		}

		else {
			throw new UserException("Not logged In");
			// return null;
		}

	}

	@Override
	public String logoutuser(String token) throws UserException {
		LoginDto loggedInUser = tokenutil.deCode(token);
		Optional<UserModel> userDetails = userRepo.findByEmailAndPassword(loggedInUser.getEmail(),loggedInUser.getPassword());
		if (userDetails.isPresent()) {
			userDetails.get().setLogin(false);
			userRepo.save(userDetails.get());
			return "logout sucessful";
		} else
			throw new UserException("invalid token");
	}

	public String isUser() {

		return null;
	}
//	@Override
//	public String sendSimpleMail(EmailDetails  details) {
//		return javaEmailService.sendSimpleMail(details);
//	}
//	

	@Override
	public UserDto updateDataByEmail(UserDto userDTO, String email) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

}