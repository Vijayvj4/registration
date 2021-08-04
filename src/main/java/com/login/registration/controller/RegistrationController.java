package com.login.registration.controller;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.login.registration.UserExcelExporter;
import com.login.registration.model.User;
import com.login.registration.model.UserPage;
import com.login.registration.service.RegistrationService;


@CrossOrigin("http://localhost:4200")
@RestController

public class RegistrationController {
	
	@Autowired
	private  RegistrationService service;

	
	@PostMapping("/registeruser")
	public User registerUser(@RequestBody User user) throws Exception {
		
		String tempEmailid = user.getEmailid();
		if (tempEmailid != null && !"".equals(tempEmailid)){
			User userObj = service.fetchUserByEmailid(tempEmailid);
			if(userObj !=null) {
				throw new Exception("user with "+tempEmailid+" already exist");
			}
		}
		User userObj = null;
		userObj = service.saveUser(user);
		return userObj;	

	}
	
	@PostMapping("/login")
	public User loginUser(@RequestBody User user) throws Exception {
		String tempEmailid = user.getEmailid();
		String tempPass = user.getPassword();
		User userObj = null;
		if(tempEmailid != null && tempPass != null) {
			userObj = service.fetchUserByEmailidAndPassword(tempEmailid, tempPass);
		}
		if(userObj == null) {
			throw new Exception("User doesnt exist");
		}
		return userObj;
				
	}
	
	@GetMapping("/users")
	public List<User> findAllUsers() {
		return service.getUsers();
		
	}
	@GetMapping("/user/{username}")
	public List<User> findUserByUsername(@PathVariable String username) {
		return service.getUserByUsername(username);
		
	}
	@GetMapping("/userby/{id}")
	public List<User> findUserById(@PathVariable int id) {
		return service.getUserById(id);
		
	}
	@DeleteMapping("/delete/{id}")
	public List<User> deleteUser(@PathVariable int id) {
		return service.deleteUser(id);
	}
	@PutMapping("/update ")
	public User updateUser(@RequestBody User user) {
		System.out.println(user);
		return service.saveUser(user);
	}
	@GetMapping("/sort")
		public ResponseEntity<Page<User>> getUsers1(UserPage userPage){
			return new ResponseEntity<>(service.getUsers1(userPage), HttpStatus.OK);
		}
	@GetMapping("/users/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<User> listUsers = service.listAll();
         
        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
         
        excelExporter.export(response);    
    }  
	}
	

