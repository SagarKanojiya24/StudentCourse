package com.kanojiya.studentcourse.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.kanojiya.studentcourse.models.CourseModel;
import com.kanojiya.studentcourse.models.FeedbackModel;
import com.kanojiya.studentcourse.models.UserModel;



public interface FeedbackRepository extends JpaRepository<FeedbackModel, Long> {

    Optional<FeedbackModel> findByUserFeedBackAndCourseFeedBack(UserModel user, CourseModel course);
    
    void deleteByUserFeedBackAndCourseFeedBack(UserModel user, CourseModel course);
    
    // or(ya)
    
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM FeedbackModel f WHERE f.userFeedBack = :user AND f.courseFeedBack = :course")
//    void deleteFeedbackByUserAndCourse(@Param("user") UserModel user, @Param("course") CourseModel course);
    
 // 👉 Course wise feedback
    List<FeedbackModel> findByCourseFeedBack_Id(Long courseId);
}