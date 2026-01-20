package com.kanojiya.studentcourse.servicesinterfaces;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kanojiya.studentcourse.models.CourseModel;

public interface CourseService {

    List<CourseModel> findAll();               

    CourseModel findById(Long id);             

    CourseModel save(CourseModel course, MultipartFile file);           

    void deleteById(Long id); 
    
     CourseModel updateCourse(CourseModel course, MultipartFile file);

	
	 List<CourseModel> findAllCourseWithFeedback();
	 Map<Long, Boolean> getCourseFeedbackMap(
	            List<CourseModel> courses, Long userId
	    );

	 List<CourseModel> findCoursesWithoutContent();
	
}
