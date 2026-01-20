package com.kanojiya.studentcourse.servicesimplementaitons;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kanojiya.studentcourse.models.ImageModel;
import com.kanojiya.studentcourse.repositorys.ImageRepository;
import com.kanojiya.studentcourse.servicesinterfaces.ImageService;

import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import java.nio.file.Files;
import java.util.List;
import java.util.UUID;


@Service
public class ImageServiceImpl implements ImageService {
	private static final long MAX_SIZE = 5 * 1024 * 1024; // 5MB
	private static final List<String> ALLOWED_TYPES = List.of(
	        "image/jpeg",
	        "image/png",
	        "image/webp"
	);
	
	

	    private final ImageRepository repo;

	    // Storage folder on D drive
	    private final String UPLOAD_DIR = "D:/companydata/uploads/images/";
	    
	    

	    public ImageServiceImpl(ImageRepository repo) {
	        this.repo = repo;
	    }

	    
	    @Transactional
	    public ImageModel saveImage(MultipartFile file) throws Exception {
	    	
	    	//Restore empty check
	    	if (file == null || file.isEmpty()) {
	    	    return null; // ya throw exception, ya simple skip
	    	}

//	    	or (OR)
	    	
	    	  // Empty file check
/*	        if (file.isEmpty()) {
	            throw new RuntimeException("File empty hai");
	        }
*/
	    	
	    	 //  Content type validation
	        String contentType = file.getContentType();
	        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
	            throw new RuntimeException("Sirf JPG, PNG, WEBP images allowed");
	        }

	        //  Size validation
	        if (file.getSize() > MAX_SIZE) {
	            throw new RuntimeException("File size max 5MB allowed");
	        }
	    	
	        
	     // Folder ensure
	        File folder = new File(UPLOAD_DIR);
	        if (!folder.exists()) {
	            folder.mkdirs();
	        }

	        // Unique filename generate
	        String uniqueName = UUID.randomUUID() + "_" + file.getOriginalFilename();

	        // Path create
	        Path path = Paths.get(UPLOAD_DIR + uniqueName);

	        // Save file to disk
	        try {
	        Files.write(path, file.getBytes());
	        } catch (IOException e) {
	            throw new RuntimeException("File save nahi ho paya: " + e.getMessage());
	        }

	        
	        // Save metadata to DB
	        ImageModel img = new ImageModel();
	        img.setFileName(uniqueName);
	        img.setFilePath("/" + uniqueName); // URL
	        img.setContentType(file.getContentType());
	        img.setSize(file.getSize());

	        return repo.save(img);
	    }

	    @Transactional
		@Override
		public void deleteImage(ImageModel image) throws Exception {
			 if (image == null) return;

		        // 1️⃣ Delete from disk
		        String fullPath = UPLOAD_DIR + image.getFileName();
		        Files.deleteIfExists(Paths.get(fullPath));

		        // 2️⃣ Delete from DB
		        repo.delete(image);			
		}

	    @Transactional
		@Override
		public ImageModel updateImage(ImageModel oldImage, MultipartFile newFile) throws Exception {
			 // 🔥 Step 1: delete old
	        deleteImage(oldImage);

	        // 🔥 Step 2: save new
	        return saveImage(newFile);
		}
	}
