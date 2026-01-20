package com.kanojiya.studentcourse.servicesimplementaitons;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kanojiya.studentcourse.models.ImageModel;
import com.kanojiya.studentcourse.models.UserModel;
import com.kanojiya.studentcourse.repositorys.UserRepository;
import com.kanojiya.studentcourse.servicesinterfaces.ImageService;
import com.kanojiya.studentcourse.servicesinterfaces.UserService;

import jakarta.transaction.Transactional;

@Service                                                     
public class UserServiceImpl implements UserService {

	  private final UserRepository userRepository;
	    private final ImageService imageServices;

	    public UserServiceImpl(UserRepository userRepository, ImageService imageServices) {
	        this.userRepository = userRepository;
	        this.imageServices = imageServices;
	    }
	
   

    // register
	@Transactional
    @Override                                               
    public UserModel registerUser(UserModel user, MultipartFile file) throws Exception {
       
        UserModel existing = userRepository.findByEmail(user.getEmail());
        if (existing != null) {                            
            return null;                                     
        }
        
//        image ka Service call kiya
        ImageModel savedImage  = imageServices.saveImage(file);
        
        
     // Null safe check before linking
        if (savedImage != null) {
        // yaha ImageModel  ka object UseModel me add kar raha hua taki realtionship ban sake
        user.setImages(savedImage);
        
//         yaha UseModel  ka object  ImageModel me add kar raha hua taki realtionship ban sake
        savedImage.setUser(user);
        }
        
       
        return userRepository.save(user);                    
    }
    
//login
    @Override
    public UserModel login(String email, String password) {
       
        return userRepository.findByEmailAndPassword(email, password);
    }

    //find email
    @Override
    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email);            
    }

    //update password after login
    @Transactional
    @Override
    public UserModel updatePassword(String email, String newPassword) {
        UserModel user = userRepository.findByEmail(email);       
        if (user == null) {                                  
            return null;
        }
        user.setPassword(newPassword);                       
        return userRepository.save(user);                    
    }
    
    
   // edit-profile-image
    @Transactional
    @Override
    public UserModel updateUser(UserModel user) {
        return userRepository.save(user);
    }



	@Override
	public UserModel findById(Long id) {
		// TODO Auto-generated method stub
		return  userRepository.findById(id).get();
	}

}
