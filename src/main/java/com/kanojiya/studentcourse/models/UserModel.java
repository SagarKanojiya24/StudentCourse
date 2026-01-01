package com.kanojiya.studentcourse.models;

import java.util.List;

import com.kanojiya.studentcourse.custom.annotations.ValidMobileNumber;
import com.kanojiya.studentcourse.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;                          

@Entity                                              
@Table(name = "users")                               
public class UserModel {

    @Id                                               
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;                                 

    @NotBlank(message = "Name blank nahi ho sakta")
    @Pattern(
    	    regexp = "^[A-Za-z0-9 ]+$",
    	    message = "Name can contain only letters and numbers"
    	)
    @Column(nullable = false)                        
    private String name;                              

    @NotBlank(message = "Email required hai")
    @Email(message = "Email format galat hai")
    @Column(nullable = false, unique = true)         
    private String email;   
    
    @ValidMobileNumber
    @NotBlank(message = "Mobile number is required")
    @Column(nullable = false)
    private String mobile;

    @NotBlank(message = "Password required hai")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$#!%*?&])[A-Za-z\\d@$#!%*?&]{8,}$",
        message = "Password must contain Uppercase, Lowercase, Number and Special Character"
    )
    @Column(nullable = false)                        
    private String password;   
    
    
    
     
//    ye feedback ke liye hai
    @Getter
    @Setter
    @OneToMany(mappedBy = "userFeedBack")
    private List<FeedbackModel> feedbackList;
    
    
    //roles
    @Enumerated(EnumType.STRING)      // DB me "USER" / "ADMIN" string ke form me store hoga
    @Column(nullable = false)
    private Role role = Role.USER;    // default value: USER

  
    @Valid
    @OneToOne(cascade = CascadeType.ALL , orphanRemoval = true)          // User ke sath Address lifecycle
    @JoinColumn(name = "address_id")              // users table me FK column address_id
    private AddressModel address;                      // Alag Address entity

    
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)         // User ke sath image lifecycle
    @JoinColumn(name = "image_id")              // users table me FK column image_id
    private ImageModel images;                      // Alag Image entity
    
    
	public ImageModel getImages() {
		return images;
	}

	public void setImages(ImageModel images) {
		this.images = images;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
    public UserModel() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public AddressModel getAddress() {
		return address;
	}

	public void setAddress(AddressModel address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
    
    
   
}

