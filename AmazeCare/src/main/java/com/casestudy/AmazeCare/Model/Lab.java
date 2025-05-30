package com.casestudy.AmazeCare.Model;

import com.casestudy.AmazeCare.Enum.LabStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Lab {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	private String location;
	
	private String contact;
	
	@Column(name = "lab_status")
	@Enumerated(EnumType.STRING)
	private LabStatus labStatus;

	public LabStatus getLabStatus() {
		return labStatus;
	}

	public void setLabStatus(LabStatus labStatus) {
		this.labStatus = labStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
	
	
}
