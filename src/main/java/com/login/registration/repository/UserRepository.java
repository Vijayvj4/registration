package com.login.registration.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.login.registration.model.User;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	public User findByEmailid(String email);
	public User findByEmailidAndPassword(String email, String password);
	public List <User> findByUsername(String username);
	public List <User> findUserById(int id);


}
