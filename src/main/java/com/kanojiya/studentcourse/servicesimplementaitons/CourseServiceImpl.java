package com.kanojiya.studentcourse.servicesimplementaitons;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kanojiya.studentcourse.models.CourseModel;
import com.kanojiya.studentcourse.models.FeedbackModel;
import com.kanojiya.studentcourse.models.ImageCloudnaryModel;
import com.kanojiya.studentcourse.repositorys.CourseRepository;
import com.kanojiya.studentcourse.servicesinterfaces.CourseService;
import com.kanojiya.studentcourse.servicesinterfaces.ImageCloudnaryService;

import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private ImageCloudnaryService imageCloudnaryService;

    @Override
    public List<CourseModel> findAll() {
        return courseRepository.findAll();           
    }

    @Override
    public CourseModel findById(Long id) {
        return courseRepository
                .findById(id)
                .orElse(null);                       
    }

    @Transactional
    @Override
    public CourseModel save(CourseModel course,MultipartFile file) {
    	try {
			
    		
    		ImageCloudnaryModel imageCloudnaryModel=  imageCloudnaryService.saveImage(file);
    		course.setImagesCloudnary(imageCloudnaryModel);
    		imageCloudnaryModel.setCoursesCloudnary(course);
    		
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	 
        return courseRepository.save(course);        
    }

  
    @Transactional
    @Override
    public void deleteById(Long id) {
    	
    	 CourseModel course = courseRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Course not found"));

    	// 🔥 STEP 1: Cloudinary delete
         if (course.getImagesCloudnary() != null) {
             String publicId = course.getImagesCloudnary().getPublicId();
             imageCloudnaryService.deleteImage(publicId);
         }
    	
         // 🔥 STEP 2: DB delete
        courseRepository.deleteById(id);   
        
        
    }

    @Transactional
    @Override
    public CourseModel updateCourse(CourseModel course, MultipartFile file) {

        CourseModel existingCourse =
                courseRepository.findById(course.getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // update normal fields
        existingCourse.setTitle(course.getTitle());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setDurationHours(course.getDurationHours());
        existingCourse.setFee(course.getFee());

        // 🔥 image update only when new file comes
        if (file != null && !file.isEmpty()) {

            if (existingCourse.getImagesCloudnary() != null) {
                imageCloudnaryService.deleteImage(
                    existingCourse.getImagesCloudnary().getPublicId()
                );
            }
            

            try {
            	ImageCloudnaryModel newImage =
                        imageCloudnaryService.saveImage(file);

                existingCourse.setImagesCloudnary(newImage);
                newImage.setCoursesCloudnary(existingCourse);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
            
        }

        return courseRepository.save(existingCourse);
    }

	
// course with all feedback
	@Override
	public List<CourseModel> findAllCourseWithFeedback() {
		return courseRepository.findAllCourseWithFeedback();
	}

	//feedback  filter using user specific "findAllCourseWithFeedback()" ka use karke
	@Override
	public Map<Long, Boolean> getCourseFeedbackMap(List<CourseModel> courses, Long userId) {
		Map<Long, Boolean> courseFeedbackMap = new HashMap<>();

        for (CourseModel course : courses) {

            boolean myFeedback = false;

            if (course.getFeedbackList() != null) {
                for (FeedbackModel f : course.getFeedbackList()) {
                    if (f.getUserFeedBack().getId().equals(userId)) {
                        myFeedback = true;
                        break;
                    }
                }
            }

            courseFeedbackMap.put(course.getId(), myFeedback);
        }

        return courseFeedbackMap;
    }

	// ye course list dega jiska content add nahi hua hai 
	@Override
	public List<CourseModel> findCoursesWithoutContent() {
		
		return courseRepository.findCoursesWithoutContent();
	}
	

}
