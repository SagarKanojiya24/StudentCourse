package com.kanojiya.studentcourse.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kanojiya.studentcourse.models.UserCourseModel;

public interface UserCourseRepository extends JpaRepository< UserCourseModel, Long> {

boolean existsByUserIdAndCourseId(Long userId, Long courseId);

@Query("""
select uc.course.id
from UserCourseModel uc
where uc.user.id = :userId
""")
List<Long> findBoughtCourseIds(Long userId);

Optional<UserCourseModel> findByUserIdAndCourseId(Long userId, Long courseId);
}
