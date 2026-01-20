package com.kanojiya.studentcourse.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "course_contents")
public class CourseContentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseModel course;

    @Column(length = 3000)
    private String overview;

    @Column
    private String youtubeUrl;

    @Column(length = 2000)
    private String syllabus;

    @Column
    private String resourceLink;

    // getters & setters
}
