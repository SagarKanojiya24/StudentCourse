package com.kanojiya.studentcourse.servicesimplementaitons;

import java.util.Map;
import java.util.HashMap;   // ✅ FIX–1: HashMap import

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kanojiya.studentcourse.models.ImageCloudnaryModel;
import com.kanojiya.studentcourse.repositorys.ImageCloudnaryRepositary;
import com.kanojiya.studentcourse.servicesinterfaces.ImageCloudnaryService;

import jakarta.transaction.Transactional;


@Service
// 👆 Spring ko batata hai ye service layer class hai
public class ImageCloudnaryServiceImpl implements ImageCloudnaryService {

    private final ImageCloudnaryRepositary repo;
    // 👆 Database ke liye repository

    private final Cloudinary cloudinary;
    // 👆 Cloudinary ka injected object (Config se aata hai)

    public ImageCloudnaryServiceImpl(ImageCloudnaryRepositary repo, Cloudinary cloudinary) {
        // 👆 Constructor injection (company standard)
        this.repo = repo;
        this.cloudinary = cloudinary;
    }

    @Transactional
    public ImageCloudnaryModel saveImage(MultipartFile file) throws Exception {
    	
    	if (file == null || file.isEmpty()) {
    	    return null; // image save mat karo
    	}

        // 1️⃣ MultipartFile ko byte[] me convert
        // 👇 Cloudinary upload ke liye bytes chahiye
        byte[] fileBytes = file.getBytes();

        // 2️⃣ Upload options Map banao
        // ✅ FIX–2: Map.of() hata diya
        Map<String, Object> options = new HashMap<>();
        // 👆 Cloudinary options rakhne ke liye

        options.put("folder", "company_images");
        // 👆 Cloudinary ke andar folder create karega

        // 3️⃣ Cloudinary par upload
        @SuppressWarnings("unchecked")
		Map<String, Object> uploadResult = cloudinary.uploader().upload(fileBytes, options);
        // 👆 upload() Object + Map leta hai
        // 👆 Response ek Map hota hai
        
             // OR(aur)
        
     /*   Map uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap());        
*/
        
        
        // 4️⃣ Secure URL nikalo
        String imageUrl = uploadResult.get("secure_url").toString();
        // 👆 Ye actual cloud image URL hota hai
        
        String public_id = uploadResult.get("public_id").toString();
        // 👆 Ye actual cloud image URL hota hai

        // 5️⃣ Entity object banao
        ImageCloudnaryModel img = new ImageCloudnaryModel();

        img.setFileName(file.getOriginalFilename());
        // 👆 User ka original file name

        img.setFilePath(imageUrl);
        // 👆 Cloudinary ka HTTPS URL (IMPORTANT)

        img.setContentType(file.getContentType());
        // 👆 image/jpeg, image/png

        img.setSize(file.getSize());
        // 👆 File size in bytes
        
        //id set for delete image from cloudnary
        img.setPublicId(public_id);

        // 6️⃣ Database me save
        return repo.save(img);
        // 👆 JPA DB me record insert karega
    }
    
    @Transactional
    @Override
    public void deleteImage(String publicId) {

        try {
        	var result = cloudinary.uploader()
                    .destroy(publicId, ObjectUtils.emptyMap());

            System.out.println("Cloudinary delete result = " + result);
        } catch (Exception e) {
            throw new RuntimeException("Cloudinary image delete failed", e);
        }
}
}
