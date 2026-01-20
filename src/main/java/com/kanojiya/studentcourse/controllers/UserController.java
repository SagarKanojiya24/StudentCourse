package com.kanojiya.studentcourse.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kanojiya.studentcourse.enums.Role;
import com.kanojiya.studentcourse.models.AddressModel;
import com.kanojiya.studentcourse.models.CourseModel;
import com.kanojiya.studentcourse.models.ImageModel;
import com.kanojiya.studentcourse.models.UserModel;
import com.kanojiya.studentcourse.servicesinterfaces.CourseService;
import com.kanojiya.studentcourse.servicesinterfaces.ImageService;
import com.kanojiya.studentcourse.servicesinterfaces.UserCourseService;
import com.kanojiya.studentcourse.servicesinterfaces.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired                                             
    private UserService userService;
   
    @Autowired
    private  ImageService imageService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private  UserCourseService userCourseService;
    
 //  Ye default page hai(index.html)
    @GetMapping("/")                    
    public String index() {
        return "redirect:/login";      
    }


    // ---------- REGISTER GET ----------
    @GetMapping("/register")                              
    public String showRegisterForm(Model model) {
    	// yaha change kiya hua address object aur user object ke liye ok
    	UserModel user = new UserModel();
    	user.setAddress(new AddressModel());
    	
        model.addAttribute("user", user);            
        return "register";                                 
    }

    // ---------- REGISTER POST ----------
    @PostMapping("/register")                               
    public String processRegister(@Valid @ModelAttribute("user") UserModel user, BindingResult result, @RequestParam("imagelocalupload") MultipartFile file, @RequestParam("confirmPassword") String confirmPassword, Model model,  RedirectAttributes redirectAttributes) throws Exception {

 /*
//    	 STEP 1: Default validation for debuging with register.html page return only
        if (result.hasErrors()) {
        	
        	// 🔥 Print all errors to console
        	System.out.println("=== BindingResult Errors ===");
        	result.getAllErrors().forEach(System.out::println);
        	
            return "register";
        }
 
 */
    			//	or(Aur)
    	
 /*   	
     // ✅ STEP 2: Manually check password match  using global object errors.
        if (!user.isPasswordMatching()) {
            // Global error add kar diya
            result.reject("passwordMismatch", "pagal Password aur Confirm Password match nahi kar rahe");
            return "register"; // wapas form
        }

  */  	 
    					
    	
    	
    	  // 🔥 MANUAL PASSWORD MATCH CHECK
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute(
                "confirmPasswordError",
                "Password aur Confirm Password match nahi kar rahe."
            );
            return "register";
        }
    	
    	
    	// Default validation
			if (result.hasErrors()) {
            return "register";
			}
			
    	
    	
    	// YAHAN: force default USER role (form se aane nahi dena)
    	    user.setRole(Role.USER);
    	   	       	        
    	 try {
			
    		// email matching
    	        UserModel created = userService.registerUser(user,file);
    	        if (created == null) {                            
    	             model.addAttribute("error", "Email already registered");
    	             return "register";                              
    	         }
    	        
		} catch (Exception ex) {
			 model.addAttribute("imageError", ex.getMessage());
			 return "register";
			
		}
    	     
    	
        

    	 redirectAttributes.addFlashAttribute(
    		        "message",
    		        "Registration successful, please login"
    		);
        return "redirect:/login";                                     
    }

    // ---------- LOGIN GET ----------
    @GetMapping("/login")                                  
    public String showLoginForm() {
        return "login";                                     
    }

    // ---------- LOGIN POST ----------
    @PostMapping("/login")                                  
    public String processLogin(@RequestParam String email,  
                               @RequestParam String password,
                               HttpSession session,        
                               Model model) {

        UserModel user = userService.login(email, password);     

        if (user == null) {                                
            model.addAttribute("error", "Invalid email or password");
            return "login";                                
        }

        session.setAttribute("loggedUser", user);          
        return "redirect:/home";                       
    }

    // ---------- HOME ----------
    @GetMapping("/home")                                    
    public String home(HttpSession session, Model model) {
        UserModel loggedUser = (UserModel) session.getAttribute("loggedUser");

        if (loggedUser == null) {                           
            return "redirect:/login";                      
        }

        model.addAttribute("user", loggedUser);
        
        
        
//        course ke liye
        List<CourseModel> courses = courseService.findAllCourseWithFeedback();
        
        Map<Long, Boolean> courseFeedbackMap =
                courseService.getCourseFeedbackMap(
                        courses, loggedUser.getId()
                );
        
    // jo user ne course buy kiya hai uska list
        List<Long> boughtIds =userCourseService.getBoughtCourseIds(loggedUser.getId());
        
        Map<Long, Boolean> buyMap = new HashMap<>();
        for (CourseModel c : courses) {
            buyMap.put(c.getId(), boughtIds.contains(c.getId()));
        }
        
        model.addAttribute("courseBuyMap", buyMap);
        
      
        model.addAttribute("courses", courses);      // Thymeleaf table ke liye
        model.addAttribute("courseFeedbackMap", courseFeedbackMap);
        
        return "home";                                      
    }
    

    
    
    

    // ---------- LOGOUT ----------
    @GetMapping("/logout")                               
    public String logout(HttpSession session) {
        session.invalidate();                               
        return "redirect:/login";                           
    }

    // ---------- FORGOT PASSWORD GET ----------
    @GetMapping("/forgot-password")                         
    public String showForgotPasswordForm() {
        return "forgot_password";                          
    }

    // ---------- FORGOT PASSWORD POST ----------
    @PostMapping("/forgot-password")                        
    public String processForgotPassword(@RequestParam String email,
                                        @RequestParam String newPassword,
                                        Model model) {

        UserModel updated = userService.updatePassword(email, newPassword); 

        if (updated == null) {                             
            model.addAttribute("error", "Email not found");
            return "forgot_password";                       
        }

        model.addAttribute("message", "Password updated. Please login.");
        return "redirect:/login";                                    
    }
    
    
    @GetMapping("/edit-profile-image")
    public String editProfileImage(HttpSession session) {

        UserModel user = (UserModel) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        return "edit_profile_image";
    }

    // edit-profile-image
    @PostMapping("/edit-profile-image")
    public String updateProfileImage(
            @RequestParam("imagelocalupload") MultipartFile file,
            HttpSession session,
            Model model) throws Exception {

        UserModel user = (UserModel) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        // OLD IMAGE
        ImageModel oldImage = user.getImages();

        // 🔥 UPDATE IMAGE
        ImageModel newImage = imageService.updateImage(oldImage, file);

        // 🔥 SET NEW IMAGE TO USER
        user.setImages(newImage);

        // 🔥 SAVE USER
        userService.updateUser(user);   // (ensure this method exists)

        // 🔥 UPDATE SESSION
        session.setAttribute("loggedUser", user);

        return "redirect:/home";
    }

    
    //delete-profile-image
    @PostMapping("/delete-profile-image")
    public String deleteProfileImage(HttpSession session) throws Exception {

        UserModel user = (UserModel) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        ImageModel oldImage = user.getImages();

        // 🔥 DELETE IMAGE (disk + DB)
        imageService.deleteImage(oldImage);

        // 🔥 UNLINK image from user
        user.setImages(null);

        // 🔥 SAVE user
        userService.updateUser(user);

        // 🔥 UPDATE session
        session.setAttribute("loggedUser", user);

        return "redirect:/home";
    }

}
