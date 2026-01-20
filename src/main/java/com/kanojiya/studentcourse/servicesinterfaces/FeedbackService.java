package com.kanojiya.studentcourse.servicesinterfaces;

import java.util.List;

import com.kanojiya.studentcourse.models.CourseModel;
import com.kanojiya.studentcourse.models.FeedbackModel;
import com.kanojiya.studentcourse.models.UserModel;

public interface FeedbackService {
	
	  FeedbackModel getFeedbackByUserAndCourse(UserModel user, CourseModel course);

	    void saveOrUpdateFeedback(UserModel user, CourseModel course, int rating, String comment);

		void deleteFeedbackByUserAndCourse(UserModel user, CourseModel course);

		 // course wise feedback
	    List<FeedbackModel> getFeedbackByCourse(Long courseId);

	    // admin delete feedback
	    void deleteFeedback(Long feedbackId);
}
