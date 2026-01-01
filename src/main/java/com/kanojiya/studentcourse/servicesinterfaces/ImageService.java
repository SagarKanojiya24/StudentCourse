package com.kanojiya.studentcourse.servicesinterfaces;


import org.springframework.web.multipart.MultipartFile;

import com.kanojiya.studentcourse.models.ImageModel;


public interface ImageService {

//	 save new image
	public ImageModel saveImage(MultipartFile file) throws Exception ;
	
	 // 🔥 NEW: old image delete
    void deleteImage(ImageModel image) throws Exception;

    // 🔥 NEW: replace image (delete + upload)
    ImageModel updateImage(ImageModel oldImage, MultipartFile newFile) throws Exception;
}
