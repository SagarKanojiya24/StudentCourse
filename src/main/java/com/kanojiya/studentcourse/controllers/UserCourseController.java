package com.kanojiya.studentcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kanojiya.studentcourse.models.CourseContentModel;
import com.kanojiya.studentcourse.models.CourseModel;
import com.kanojiya.studentcourse.models.UserModel;

import com.kanojiya.studentcourse.servicesinterfaces.CourseContentService;
import com.kanojiya.studentcourse.servicesinterfaces.CourseService;
import com.kanojiya.studentcourse.servicesinterfaces.UserCourseService;

import jakarta.servlet.http.HttpSession;


@RequestMapping("/fromUserCourse")
@Controller
public class UserCourseController {
	@Autowired
	 private  UserCourseService  userCourseService;
	
	@Autowired
	private  CourseService courseService;
	
	@Autowired
	private CourseContentService courseContentService;
	

	@PostMapping("/buy/{courseId}")
    public String buyCourse(@PathVariable Long courseId,
                            HttpSession session) {

        UserModel user = (UserModel) session.getAttribute("loggedUser");
        
        if (user == null) {                           
            return "redirect:/login";                      
        }
        
        userCourseService.buyCourse(user.getId(), courseId);
        

        return "redirect:/home";
    }

	
	
	 @GetMapping("/details/{courseId}")
	    public String courseDetails(@PathVariable Long courseId,
	                                Model model,
	                                HttpSession session) {

	        UserModel user = (UserModel) session.getAttribute("loggedUser");
	        
	        if (user == null) {                           
	            return "redirect:/login";                      
	        }
	        

	        if (!userCourseService.isCourseBought(user.getId(), courseId)) {
	            return "redirect:/home";
	        }

	        CourseModel course = courseService.findById(courseId); 
	        
	        CourseContentModel content = courseContentService.getContentByCourseId(courseId);
	        

	        model.addAttribute("course", course);
	        model.addAttribute("content", content);
	        return "courses/user-course-details";
	    }
	
}
