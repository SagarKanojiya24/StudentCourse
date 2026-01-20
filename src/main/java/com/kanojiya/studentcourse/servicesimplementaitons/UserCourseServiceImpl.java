package com.kanojiya.studentcourse.servicesimplementaitons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanojiya.studentcourse.models.CourseModel;
import com.kanojiya.studentcourse.models.UserCourseModel;
import com.kanojiya.studentcourse.models.UserModel;
import com.kanojiya.studentcourse.repositorys.CourseRepository;
import com.kanojiya.studentcourse.repositorys.UserCourseRepository;
import com.kanojiya.studentcourse.repositorys.UserRepository;
import com.kanojiya.studentcourse.servicesinterfaces.UserCourseService;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    private UserCourseRepository userCourseRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Override
    public boolean isCourseBought(Long userId, Long courseId) {
        return userCourseRepo.existsByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public void buyCourse(Long userId, Long courseId) {

        if (isCourseBought(userId, courseId)) return;

        UserModel user = userRepo.findById(userId).orElseThrow();
        CourseModel course = courseRepo.findById(courseId).orElseThrow();

        UserCourseModel uc = new UserCourseModel();
        uc.setUser(user);
        uc.setCourse(course);

        userCourseRepo.save(uc);
    }

    @Override
    public List<Long> getBoughtCourseIds(Long userId) {
        return userCourseRepo.findBoughtCourseIds(userId);
    }

    @Override
    public UserCourseModel getUserCourse(Long userId, Long courseId) {
        return userCourseRepo
                .findByUserIdAndCourseId(userId, courseId)
                .orElseThrow();
    }
}
