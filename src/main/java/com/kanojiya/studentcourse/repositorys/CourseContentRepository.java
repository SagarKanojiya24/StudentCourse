package com.kanojiya.studentcourse.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanojiya.studentcourse.models.CourseContentModel;

public interface CourseContentRepository extends JpaRepository<CourseContentModel, Long> {

Optional<CourseContentModel> findByCourse_Id(Long courseId);
}
