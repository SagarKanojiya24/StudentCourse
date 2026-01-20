package com.kanojiya.studentcourse.repositorys;

import com.kanojiya.studentcourse.models.CourseModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface CourseRepository extends JpaRepository<CourseModel, Long> {
	 // Course ke saath saare Feedbacks load karne ke liye join fetch
	@Query("SELECT c FROM CourseModel c LEFT JOIN FETCH c.feedbackList")
    List<CourseModel> findAllCourseWithFeedback();
	
	
	@Query("""
		    SELECT c FROM CourseModel c
		    WHERE c.id NOT IN (
		        SELECT cc.course.id FROM CourseContentModel cc
		    )
		""")
		List<CourseModel> findCoursesWithoutContent();

}
