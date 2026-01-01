package com.kanojiya.studentcourse.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
	@Table(
	    name = "feedback",
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = {"user_id", "course_id"})
	    }
	)
	@Setter
	@Getter
	public class FeedbackModel {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotNull(message = "Rating dena zaroori hai")
	    @Min(value = 1, message = "Rating 1 se kam nahi ho sakti")
	    @Max(value = 5, message = "Rating 5 se zyada nahi ho sakti")
	    private int rating;
	    
	    @NotBlank(message = "Comment likhna zaroori hai")
	    @Size(min = 10, max = 500, message = "Comment 10-500 characters me hona chahiye")
	    private String comment;

	    @Getter
	    @Setter
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private UserModel userFeedBack;

	    @Getter
	    @Setter
	    @ManyToOne
	    @JoinColumn(name = "course_id")
	    private CourseModel courseFeedBack;
	}
