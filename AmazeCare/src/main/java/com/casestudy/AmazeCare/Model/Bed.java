package com.casestudy.AmazeCare.Model;

import com.casestudy.AmazeCare.Enum.BedAvailability;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Bed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String number;
	
	@ManyToOne
	private Ward ward;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "bed_availability")
	private BedAvailability bedAvailability;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}


	public Ward getWard() {
		return ward;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}

	public BedAvailability getBedAvailability() {
		return bedAvailability;
	}

	public void setBedAvailability(BedAvailability bedAvailability) {
		this.bedAvailability = bedAvailability;
	}
	
	
}
