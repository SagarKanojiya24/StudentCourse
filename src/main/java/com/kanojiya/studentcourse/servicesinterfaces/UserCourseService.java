package com.kanojiya.studentcourse.servicesinterfaces;

import java.util.List;

import com.kanojiya.studentcourse.models.UserCourseModel;

public interface UserCourseService {

    boolean isCourseBought(Long userId, Long courseId);

    void buyCourse(Long userId, Long courseId);

    List<Long> getBoughtCourseIds(Long userId);

    UserCourseModel getUserCourse(Long userId, Long courseId);
}
