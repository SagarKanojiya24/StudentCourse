package com.kanojiya.studentcourse.servicesinterfaces;

import java.util.List;
import java.util.Optional;

import com.kanojiya.studentcourse.models.CourseContentModel;

public interface CourseContentService {
    CourseContentModel getContentByCourseId(Long courseId);  // ye pahle banaya tha sahi chal raha hai thik hai
    
    CourseContentModel save(CourseContentModel content);

    CourseContentModel update(Long id, CourseContentModel content);

    void deleteById(Long id);

    Optional<CourseContentModel> findById(Long id);

    Optional<CourseContentModel> findByCourseId(Long courseId);

    List<CourseContentModel> findAll();
    
}
