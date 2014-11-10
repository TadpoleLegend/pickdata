package com.meirong.service;

import java.util.List;
import java.util.Set;

import com.meirong.entity.User;

public interface UserService {
	List<User> findUserByName(String name);
	Set<String> findAllAccounts();
	
	User findUser(String username, String password);
}
