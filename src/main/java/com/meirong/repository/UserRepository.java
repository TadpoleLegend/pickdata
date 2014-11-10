package com.meirong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.meirong.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> , JpaSpecificationExecutor<User>{
	List<User> findByName(String name);
	List<User> findByUsernameAndPassword(String name, String password);
}
