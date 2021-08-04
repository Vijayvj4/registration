package com.login.registration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.login.registration.model.User;
import com.login.registration.model.UserPage;
import com.login.registration.repository.UserRepository;

@Service
public class RegistrationService {

	@Autowired
	private UserRepository repo;
	
	public User saveUser(User user) {
		return repo.save(user);
	}
	
	public User fetchUserByEmailid(String email) {
		return repo.findByEmailid(email);
	}
	public User fetchUserByEmailidAndPassword(String email, String password) {
		return repo.findByEmailidAndPassword(email, password);
	}
	public List<User> getUsers() {
		return (List<User>) repo.findAll();
	}
	public List<User> getUserByUsername(String username) {
		return repo.findByUsername(username);
	}
	public List<User> getUserById(int id) {
		return repo.findUserById(id);
	}
	public List<User> deleteUser(int id) {
		repo.deleteById(id);
		return (List<User>) repo.findAll();
	}
	public User updateUser(User user) {
		User existingUser=repo.findById(user.getId()).orElse(null);
		existingUser.setEmailid(user.getEmailid());
		existingUser.setUsername(user.getUsername());
		existingUser.setPassword(user.getPassword());
        return repo.save(existingUser);
		
	}
	public Page<User> getUsers1(UserPage userPage){
		Sort sort = Sort.by(userPage.getSortDirection(), userPage.getSortBy());
		Pageable pageable = PageRequest.of(userPage.getPageNumber(), userPage.getPageSize(),sort);
		return repo.findAll(pageable);
	}
	 public List<User> listAll() {
	        return (List<User>) repo.findAll(Sort.by("id").ascending());
	    }
	 
}
