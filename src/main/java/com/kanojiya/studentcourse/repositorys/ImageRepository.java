package com.kanojiya.studentcourse.repositorys;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kanojiya.studentcourse.models.ImageModel;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    
}
