package com.kanojiya.studentcourse.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_courses")
public class UserCourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // many purchases → one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    // many purchases → one course
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseModel course;

    @Column(nullable = false)
    private LocalDateTime buyDate = LocalDateTime.now();

    // getters & setters
}
