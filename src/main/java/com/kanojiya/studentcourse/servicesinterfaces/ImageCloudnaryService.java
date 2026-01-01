package com.kanojiya.studentcourse.servicesinterfaces;

import org.springframework.web.multipart.MultipartFile;

import com.kanojiya.studentcourse.models.ImageCloudnaryModel;



public interface ImageCloudnaryService {
	
	 public ImageCloudnaryModel saveImage(MultipartFile file) throws Exception;
	 
	 void deleteImage(String publicId);
	 
	
	
}

