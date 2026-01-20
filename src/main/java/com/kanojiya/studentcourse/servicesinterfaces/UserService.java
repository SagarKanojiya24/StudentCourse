package com.kanojiya.studentcourse.servicesinterfaces;

import org.springframework.web.multipart.MultipartFile;

import com.kanojiya.studentcourse.models.UserModel;

public interface UserService {                           

    UserModel registerUser(UserModel user,MultipartFile file) throws Exception;                        

    UserModel login(String email, String password);           

    UserModel findByEmail(String email);                     

    UserModel updatePassword(String email, String newPassword); 
    //edit-profile-image
    UserModel updateUser(UserModel user);

	UserModel findById(Long id);

}

