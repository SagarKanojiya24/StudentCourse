package com.kanojiya.studentcourse.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;

import com.kanojiya.studentcourse.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {


 UserModel findByEmail(String email);

 

 UserModel findByEmailAndPassword(@Param("email") String email, @Param("password") String password);


}