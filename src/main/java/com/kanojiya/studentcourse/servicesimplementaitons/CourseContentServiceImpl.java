package com.kanojiya.studentcourse.servicesimplementaitons;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanojiya.studentcourse.models.CourseContentModel;
import com.kanojiya.studentcourse.repositorys.CourseContentRepository;
import com.kanojiya.studentcourse.servicesinterfaces.CourseContentService;

@Service
public class CourseContentServiceImpl  implements CourseContentService {

    @Autowired
    private CourseContentRepository contentRepo;

    public CourseContentServiceImpl(CourseContentRepository contentRepo) {
        this.contentRepo = contentRepo;
    }
    
    // ye pahle se banaya hua kaam kar raha hai ok
    @Override
    public CourseContentModel getContentByCourseId(Long courseId) {
        return contentRepo.findByCourse_Id(courseId).orElse(null);
    }
  

    @Override
    public CourseContentModel save(CourseContentModel content) {
        return contentRepo.save(content);
    }

    @Override
    public CourseContentModel update(Long id, CourseContentModel content) {

        CourseContentModel dbContent = contentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        dbContent.setOverview(content.getOverview());
        dbContent.setYoutubeUrl(content.getYoutubeUrl());
        dbContent.setSyllabus(content.getSyllabus());
        dbContent.setResourceLink(content.getResourceLink());

        return contentRepo.save(dbContent);
    }

    @Override
    public void deleteById(Long id) {
        contentRepo.deleteById(id);
    }

    @Override
    public Optional<CourseContentModel> findById(Long id) {
        return contentRepo.findById(id);
    }

    @Override
    public Optional<CourseContentModel> findByCourseId(Long courseId) {
        return contentRepo.findByCourse_Id(courseId);
    }

    @Override
    public List<CourseContentModel> findAll() {
        return contentRepo.findAll();
    }
}