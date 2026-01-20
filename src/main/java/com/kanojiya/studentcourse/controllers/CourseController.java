package com.kanojiya.studentcourse.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kanojiya.studentcourse.enums.Role;
import com.kanojiya.studentcourse.models.CourseContentModel;
import com.kanojiya.studentcourse.models.CourseModel;
import com.kanojiya.studentcourse.models.UserModel;
import com.kanojiya.studentcourse.servicesinterfaces.CourseContentService;
import com.kanojiya.studentcourse.servicesinterfaces.CourseService;

import java.util.List;

@Controller
@RequestMapping("/courses")                          // Saare URLs /courses se start
public class CourseController {

    @Autowired
    private CourseService courseService;
    
    @Autowired
    private CourseContentService courseContentService;
    

	//CourseController me ek helper bana raha hua roles check karne ke liye
	private boolean isAdmin(HttpSession session) {
	    UserModel logged = (UserModel) session.getAttribute("loggedUser");
	    return logged != null && logged.getRole() == Role.ADMIN;
	}
	
	

    // (Optional) simple auth check: sirf logged user ke liye
    private boolean notLoggedIn(HttpSession session) {
        return session.getAttribute("loggedUser") == null;
    }

    // LIST - GET /courses  for ADMIN only
    @GetMapping
    public String listCourses(Model model, HttpSession session) {
        if (notLoggedIn(session)) {
            return "redirect:/login";
        }
        if (!isAdmin(session)) {              // agar admin nahi, to access deny
            return "redirect:/home";          // ya koi error page / message
        }
        
        // course ke liye
        List<CourseModel> courses = courseService.findAll();
        model.addAttribute("courses", courses);      // Thymeleaf table ke liye
        
        // content ke liye
        List<CourseContentModel> contents = courseContentService.findAll();
        model.addAttribute("contents", contents);      // Thymeleaf table ke liye
        
        
        // 🔹 Sirf un courses ko lao jinka content abhi nahi bana hai
        List<CourseModel> availableCourses = courseService.findCoursesWithoutContent();
        model.addAttribute("availableCourses", availableCourses);
        
        
        // or ( ya) 
  
/*        
        List<Long> availableCourseIds = availableCourses
        .stream()
        .map(CourseModel::getId)
        .toList();

        model.addAttribute("availableCourseIds", availableCourseIds);
        
 */    
         
        return "courses/list";                      
    }

    // SHOW CREATE FORM - GET /courses/new for ADMIN only
    @GetMapping("/new")
    public String showCreateForm(Model model, HttpSession session) {
        if (notLoggedIn(session)) {
            return "redirect:/login";
        }
        
        if (!isAdmin(session)) {              // agar admin nahi, to access deny
            return "redirect:/home";          // ya koi error page / message
        }
        
        model.addAttribute("course", new CourseModel());  // Empty object form binding
        model.addAttribute("formTitle", "Create Course");
        return "courses/courseNewForm";                       // Same form create/update ke liye
    }

    // SHOW EDIT FORM - GET /courses/edit/{id} for ADMIN only
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model,
                               HttpSession session) {
        if (notLoggedIn(session)) {
            return "redirect:/login";
        }
        if (!isAdmin(session)) {              // agar admin nahi, to access deny
            return "redirect:/home";          // ya koi error page / message
        }
        
        CourseModel course = courseService.findById(id);
        if (course == null) {
            return "redirect:/courses";              // Id galat to list pe wapas
        }
        model.addAttribute("course", course);        // Existing course form me
        model.addAttribute("formTitle", "Edit Course");
        return "courses/courseFormupdate";
    }

    // SAVE (CREATE) - POST /courses/save for ADMIN only
    @PostMapping("/save")
    public String saveCourse( @Valid @ModelAttribute("course") CourseModel course, BindingResult result,   @RequestParam("imageCloudnary") MultipartFile file,
                             HttpSession session) {
        if (notLoggedIn(session)) {
            return "redirect:/login";
        }
        
        if (!isAdmin(session)) {              // agar admin nahi, to access deny
            return "redirect:/home";          // ya koi error page / message
        }
        
        //  Course validation errors
        if (result.hasErrors()) {
          
            return "courses/courseNewForm"; // errors wapas dikhao
        }
        
      
        	 courseService.save(course,file);
		
                         
        return "redirect:/courses";
    }
    
    
    // Edit (Update) - POST  for ADMIN only
    @PostMapping("/updateCourse")
    public String updateCourse(@Valid @ModelAttribute("course") CourseModel course, BindingResult result,  @RequestParam("imageCloudnary") MultipartFile file,
                             HttpSession session) {
        if (notLoggedIn(session)) {
            return "redirect:/login";
        }
        
        if (!isAdmin(session)) {              // agar admin nahi, to access deny
            return "redirect:/home";          // ya koi error page / message
        }
        
        //  Course validation
        if (result.hasErrors()) {
           
            return "courses/courseFormupdate";
        }

        
      
        	 courseService.updateCourse(course,file);
		
                         
        return "redirect:/courses";
    }

    

    // DELETE - GET /courses/delete/{id} for ADMIN only
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id,
                               HttpSession session) {
        if (notLoggedIn(session)) {
            return "redirect:/login";
        }
        
        if (!isAdmin(session)) {              // agar admin nahi, to access deny
            return "redirect:/home";          // ya koi error page / message
        }
        
        
        courseService.deleteById(id);
        return "redirect:/courses";
    }
    
    
    
    
    
}

