package com.kanojiya.studentcourse.models;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "courses")            
public class CourseModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                  

    @NotBlank(message = "Course title zaroori hai")
    @Size(min = 5, max = 100, message = "Title 5-100 characters me hona chahiye")
    @Column(nullable = false)
    private String title;     
    

    @Size(min = 20, max = 2000, message = "Description 20-2000 characters me honi chahiye")
    @Column(length = 2000)
    private String description;    
    

    @NotNull(message = "Duration zaroori hai")
    @Min(value = 1, message = "Duration 1 hour se kam nahi ho sakti")
    @Max(value = 1000, message = "Duration 1000 hours se zyada nahi ho sakti")
    @Column
    private Integer durationHours;  
    
    
    @NotNull(message = "Fee zaroori hai")
    @DecimalMin(value = "0.0", message = "Fee 0 se kam nahi ho sakti")
    @DecimalMax(value = "100000.0", message = "Fee 1 lakh se zyada nahi ho sakti")
    @Column
    private Double fee;   
    
    // ye feed back ke liye hai
    @Getter
    @Setter
    @OneToMany(mappedBy = "courseFeedBack",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackModel> feedbackList;
    
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)         
    @JoinColumn(name = "imageCloudnary_id")              
    private ImageCloudnaryModel imagesCloudnary;                     
    

    public CourseModel() {
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDurationHours() { return durationHours; }
    public void setDurationHours(Integer durationHours) { this.durationHours = durationHours; }

    public Double getFee() { return fee; }
    public void setFee(Double fee) { this.fee = fee; }
}
