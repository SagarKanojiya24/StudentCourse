package com.kanojiya.studentcourse.servicesimplementaitons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanojiya.studentcourse.models.CourseModel;
import com.kanojiya.studentcourse.models.FeedbackModel;
import com.kanojiya.studentcourse.models.UserModel;
import com.kanojiya.studentcourse.repositorys.FeedbackRepository;
import com.kanojiya.studentcourse.servicesinterfaces.FeedbackService;

import jakarta.transaction.Transactional;






@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public FeedbackModel getFeedbackByUserAndCourse(UserModel user, CourseModel course) {

    	
        return feedbackRepository
                .findByUserFeedBackAndCourseFeedBack(user, course)
                .orElse(new FeedbackModel());
    }

    @Transactional
    @Override
    public void saveOrUpdateFeedback(UserModel user, CourseModel course, int rating, String comment) {

        FeedbackModel feedback = feedbackRepository
                .findByUserFeedBackAndCourseFeedBack(user, course)
                .orElse(new FeedbackModel());

        feedback.setUserFeedBack(user);
        feedback.setCourseFeedBack(course);
        feedback.setRating(rating);
        feedback.setComment(comment);
        
        

        

        feedbackRepository.save(feedback);
    }

    // deleteFeedbackByUserAndCourse  ye wala user ke side se
   
    @Transactional
	@Override
	public void deleteFeedbackByUserAndCourse(UserModel user, CourseModel course) {
		feedbackRepository.deleteByUserFeedBackAndCourseFeedBack(user, course);
		
	}
	
	//getFeedbackByCourse for all user sprcific course ye wala admin ke side se
	@Override
    public List<FeedbackModel> getFeedbackByCourse(Long courseId) {
        return feedbackRepository.findByCourseFeedBack_Id(courseId);
    }

	//deleteFeedback for all user sprcific course ye wala admin ke side se
	@Transactional
    @Override
    public void deleteFeedback(Long feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
