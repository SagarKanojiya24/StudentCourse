package com.kanojiya.studentcourse.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "addresses")
public class AddressModel {


	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank(message = "Address Line 1 required hai")
	    @Column(name = "line1", nullable = false)
	    private String line1;              // House no, street

	    @NotBlank(message = "Address Line 2 required hai")
	    @Column(name = "line2")
	    private String line2;              // Optional (landmark etc.)

	    @NotBlank(message = "City required hai")
	    @Column(nullable = false)
	    private String city;

	    @NotBlank(message = "State required hai")
	    @Column(nullable = false)
	    private String state;

	    @NotBlank(message = "Postal Code required hai")
	    @Column(name = "postal_code", nullable = false)
	    private String postalCode;

	    @NotBlank(message = "Country required hai")
	    @Column(nullable = false)
	    private String country;

	    public AddressModel() {
	    }

	    // getters & setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public String getLine1() { return line1; }
	    public void setLine1(String line1) { this.line1 = line1; }

	    public String getLine2() { return line2; }
	    public void setLine2(String line2) { this.line2 = line2; }

	    public String getCity() { return city; }
	    public void setCity(String city) { this.city = city; }

	    public String getState() { return state; }
	    public void setState(String state) { this.state = state; }

	    public String getPostalCode() { return postalCode; }
	    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

	    public String getCountry() { return country; }
	    public void setCountry(String country) { this.country = country; }
	}
