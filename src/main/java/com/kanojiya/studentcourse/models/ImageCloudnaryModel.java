package com.kanojiya.studentcourse.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "imagescloudnary")
public class ImageCloudnaryModel {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;                        // Image ID (PK)

	    @Column(nullable = false)
	    private String fileName;                // Disk par stored naam (UUID + original)

	    @Column(nullable = false)
	    private String filePath;                // Browser URL, e.g. /images/uuid_name.png

	    @Column(nullable = false)
	    private String contentType;             // image/jpeg, image/png, ...

	    @Column(nullable = false)
	    private Long size;                      // file size (bytes)
	    
	    @Getter
	    @Setter
	    @Column(nullable = false) // delete karne ke liye cloud se vimp
	    private String publicId; // cloudinary public_id

	    
	    @Getter
	    @Setter
	    @OneToOne(mappedBy = "imagesCloudnary")
	    private CourseModel coursesCloudnary;
	    

	    public ImageCloudnaryModel() {
	    }

	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public String getFileName() { return fileName; }
	    public void setFileName(String fileName) { this.fileName = fileName; }

	    public String getFilePath() { return filePath; }
	    public void setFilePath(String filePath) { this.filePath = filePath; }

	    public String getContentType() { return contentType; }
	    public void setContentType(String contentType) { this.contentType = contentType; }

	    public Long getSize() { return size; }
	    public void setSize(Long size) { this.size = size; }
	}
