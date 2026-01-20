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
import com.kanojiya.studentcourse.servicesinterfaces.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class FeedbackController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private FeedbackService feedbackService;
    
    @Autowired
    private UserService userService;

    // GiveFeedbackform for User
    @GetMapping("/givefeedbackForm/{courseId}")
    public String givefeedbackForm(
            @PathVariable Long courseId,
            HttpSession session,
            Model model) {

        UserModel user = (UserModel) session.getAttribute("loggedUser");
        
        if (user == null) {                           
            return "redirect:/login";                      
        }

        
        CourseModel course = courseService.findById(courseId);
        

        FeedbackModel feedback = new FeedbackModel();
              

        model.addAttribute("feedback", feedback);
        model.addAttribute("course", course);

        return "Givefeedbackform";
    }
    

   //Editfeedbackform      
    @GetMapping("/editfeedbackForm/{courseId}")
    public String editfeedbackForm(
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

        return "Editfeedbackform";
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

            return "Givefeedbackform"; // SAME PAGE
        }
        
        
        UserModel user = (UserModel) session.getAttribute("loggedUser");
        
        if (user == null) {                           
            return "redirect:/login";                      
        }
        

        feedbackService.saveOrUpdateFeedback(
                user, course, feedback.getRating(), feedback.getComment());

        return "redirect:/home";
    }
    
    // feedback delete for USER
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

    // 🔹  delete feedback from Admin
    @GetMapping("/feedback/delete/{feedbackId}/{courseId}")
    public String deleteFeedback(
            @PathVariable Long feedbackId,
            @PathVariable Long courseId, HttpSession session) {

       UserModel user = (UserModel) session.getAttribute("loggedUser");
         
         if (user == null) {
             return "redirect:/login";
         }
			
    		feedbackService.deleteFeedback(feedbackId);
		
        
    	

        return "redirect:/course/" + courseId + "/feedbacks";
    }
    
    
    // 🔹 EditFeedbackFromADMIN
    @GetMapping("/feedback/edit/{feedbackId}/{courseId}/{userId}")
    public String editFeedback(
            @PathVariable Long feedbackId,
            @PathVariable Long courseId,  @PathVariable Long userId, HttpSession session, Model model) {

       UserModel user1 = (UserModel) session.getAttribute("loggedUser");
         
         if (user1 == null) {
             return "redirect:/login";
         }
			
         UserModel user  = userService.findById(userId);
         CourseModel course = courseService.findById(courseId);
         

         FeedbackModel feedback =
                 feedbackService.getFeedbackByUserAndCourse(user, course);

         model.addAttribute("feedback", feedback);
         model.addAttribute("course", course);
         model.addAttribute("user", user);

        
        return "EditfeedbackformFromADMIN";
    }
    
    //submitFeedbackFromADMIN
    @PostMapping("/submitFeedbackFromADMIN")
    public String submitFeedbackFromADMIN(
            @RequestParam Long courseId,
            @RequestParam Long userId,
            @Valid @ModelAttribute("feedback") FeedbackModel feedback,BindingResult result,
            HttpSession session, Model model) {
  
     
    	 UserModel user  = userService.findById(userId);
         CourseModel course = courseService.findById(courseId);
        
        if (result.hasErrors()) {

            // Page ke liye dubara data set and validation fail hone par . nahi to error dega
            model.addAttribute("course", course);
            
            model.addAttribute("user", user);

            return "EditfeedbackformFromADMIN"; // SAME PAGE
        }
        
        
        UserModel user1 = (UserModel) session.getAttribute("loggedUser");
        
        if (user1 == null) {                           
            return "redirect:/login";                      
        }
        
       

        feedbackService.saveOrUpdateFeedback(
                user, course, feedback.getRating(), feedback.getComment());

        return "redirect:/home";
    }
    
    
}
