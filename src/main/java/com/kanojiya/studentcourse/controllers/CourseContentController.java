package com.kanojiya.studentcourse.controllers;


import com.kanojiya.studentcourse.models.CourseContentModel;
import com.kanojiya.studentcourse.models.CourseModel;
import com.kanojiya.studentcourse.models.UserModel;

import com.kanojiya.studentcourse.servicesinterfaces.CourseContentService;
import com.kanojiya.studentcourse.servicesinterfaces.CourseService;

import jakarta.servlet.http.HttpSession;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/course-content")
public class CourseContentController {

    private final CourseContentService contentService;
  
    
    @Autowired
    private CourseService courseService;

    public CourseContentController(CourseContentService contentService  ) {
        this.contentService = contentService;
        
    }

    // 
    // 🔹 LIST  iska use nahi kar raha hua jab. main "CourseController" me kar raha hua ye tarika
    @GetMapping("/coursecontent/list")
    public String list(Model model,HttpSession session) {
    	UserModel loggedUser = (UserModel) session.getAttribute("loggedUser");

        if (loggedUser == null) {                           
            return "redirect:/login";                      
        }
    	
        model.addAttribute("contents", contentService.findAll());
        return "coursecontent/list";
    }
    
    // testing adding only after i will remove
    @GetMapping("/add")
    public String addFormFromContentSide( Model model,HttpSession session) {
    	
    	UserModel loggedUser = (UserModel) session.getAttribute("loggedUser");

        if (loggedUser == null) {                           
            return "redirect:/login";                      
        }

       
     // 🔹 Sirf un courses ko lao jinka content abhi nahi bana hai
        List<CourseModel> availableCourses = courseService.findCoursesWithoutContent();

        CourseContentModel content = new CourseContentModel();

        model.addAttribute("content", content);
        model.addAttribute("courses", availableCourses);
        return "coursecontent/AddContentFromContentList";
    }
    

    // 🔹 ADD FORM
    @GetMapping("/add/{courseId}")
    public String addFormFromCourseSide(@PathVariable Long courseId, Model model,HttpSession session) {
    	
    	UserModel loggedUser = (UserModel) session.getAttribute("loggedUser");

        if (loggedUser == null) {                           
            return "redirect:/login";                      
        }

        CourseModel course = courseService.findById(courseId);

        CourseContentModel content = new CourseContentModel();
        content.setCourse(course);

        model.addAttribute("content", content);
        return "coursecontent/AddContentFromCourseList";
    }

    // 🔹 SAVE
    @PostMapping("/save")
    public String save( @ModelAttribute CourseContentModel content, HttpSession session) {
    	UserModel loggedUser = (UserModel) session.getAttribute("loggedUser");

        if (loggedUser == null) {                           
            return "redirect:/login";                      
        }
        

        
        Long courseId = content.getCourse().getId();

        CourseModel course = courseService.findById(courseId);
               

        content.setCourse(course);
        
        contentService.save(content);
        return "redirect:/home";
    }

    // 🔹 EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model,HttpSession session) {
    	UserModel loggedUser = (UserModel) session.getAttribute("loggedUser");

        if (loggedUser == null) {                           
            return "redirect:/login";                      
        }
    	
        model.addAttribute("content",
                contentService.findById(id).orElseThrow());
        return "coursecontent/EditContent";
    }

    // 🔹 UPDATE
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute CourseContentModel content,HttpSession session) {
    	UserModel loggedUser = (UserModel) session.getAttribute("loggedUser");

        if (loggedUser == null) {                           
            return "redirect:/login";                      
        }

        contentService.update(id, content);
        return "redirect:/home";
    }

    // 🔹 DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
    	UserModel loggedUser = (UserModel) session.getAttribute("loggedUser");

        if (loggedUser == null) {                           
            return "redirect:/login";                      
        }
    	
        contentService.deleteById(id);
        return "redirect:/home";
    }
}
