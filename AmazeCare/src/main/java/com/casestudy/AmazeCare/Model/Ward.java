package com.casestudy.AmazeCare.Model;

import com.casestudy.AmazeCare.Enum.LabStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ward {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String ward_number;
	private LabStatus status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWard_number() {
		return ward_number;
	}
	public void setWard_number(String ward_number) {
		this.ward_number = ward_number;
	}
	public LabStatus getStatus() {
		return status;
	}
	public void setStatus(LabStatus status) {
		this.status = status;
	}
	
	
	
}
