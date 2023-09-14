package com.jverter.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jverter.auth.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsernameIgnoreCase(String username);

	@Query("update User usr set usr.password = :password where usr.username = :username")
	int updatePassword(@Param("password") String password, @Param("username") String username);

}