package com.kanojiya.studentcourse.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kanojiya.studentcourse.models.CourseModel;
import com.kanojiya.studentcourse.models.FeedbackModel;
import com.kanojiya.studentcourse.models.UserModel;

import com.kanojiya.studentcourse.servicesinterfaces.CourseService;
import com.kanojiya.studentcourse.servicesinterfaces.FeedbackService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class FeedbackController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/feedbackForm/{courseId}")
    public String feedbackForm(
            @PathVariable Long courseId,
            HttpSession session,
            Model model) {

        UserModel user = (UserModel) session.getAttribute("loggedUser");
        
        if (user == null) {                           
            return "redirect:/login";                      
        }

        
        CourseModel course = courseService.findById(courseId);
        

        FeedbackModel feedback =
                feedbackService.getFeedbackByUserAndCourse(user, course);

        model.addAttribute("feedback", feedback);
        model.addAttribute("course", course);

        return "feedbackform";
    }

    @PostMapping("/submitFeedback")
    public String submitFeedback(
            @RequestParam Long courseId,
            @Valid @ModelAttribute("feedback") FeedbackModel feedback,BindingResult result,
            HttpSession session, Model model) {
  
     
        CourseModel course = courseService.findById(courseId);
        
        if (result.hasErrors()) {

            // Page ke liye dubara data set
            model.addAttribute("course", course);

            return "feedbackform"; // SAME PAGE
        }
        
        
        UserModel user = (UserModel) session.getAttribute("loggedUser");
        
        if (user == null) {                           
            return "redirect:/login";                      
        }
        

        feedbackService.saveOrUpdateFeedback(
                user, course, feedback.getRating(), feedback.getComment());

        return "redirect:/home";
    }
    
    
    @PostMapping("/deleteFeedbackForm/{courseId}")
    public String deleteFeedbackForm(
            @PathVariable Long courseId,
            HttpSession session,
            Model model, RedirectAttributes redirectAttributes) {

        UserModel user = (UserModel) session.getAttribute("loggedUser");
        
        if (user == null) {
            return "redirect:/login";
        }
        
        CourseModel course = courseService.findById(courseId);
        
// delete feedback of specific course 
          feedbackService.deleteFeedbackByUserAndCourse(user, course);

       
        redirectAttributes.addFlashAttribute(
		        "deleteMessage",
		        "Delete feedbackform  successful"
		);

        return "redirect:/home";
    }
    
    
    
    // 🔹 Course wise feedback view
    @GetMapping("/course/{courseId}/feedbacks")
    public String viewFeedbacks(
            @PathVariable Long courseId,
            Model model, HttpSession session) {
    	
    	 UserModel user = (UserModel) session.getAttribute("loggedUser");
         
         if (user == null) {
             return "redirect:/login";
         }

        model.addAttribute("feedbacks",
                feedbackService.getFeedbackByCourse(courseId));

        model.addAttribute("courseId", courseId);

        return "viewfeedback";
    }

    // 🔹 Admin delete feedback
    @GetMapping("/feedback/delete/{id}/{courseId}")
    public String deleteFeedback(
            @PathVariable Long id,
            @PathVariable Long courseId, HttpSession session) {

       UserModel user = (UserModel) session.getAttribute("loggedUser");
         
         if (user == null) {
             return "redirect:/login";
         }
			
    		feedbackService.deleteFeedback(id);
		
        
    	

        return "redirect:/course/" + courseId + "/feedbacks";
    }
    
    
}
