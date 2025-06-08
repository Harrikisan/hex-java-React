package com.casestudy.AmazeCare.Model;

import com.casestudy.AmazeCare.Enum.BedAvailability;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String number;
	
	@Column(name = "room_number")
	private String roomNumber;
	
	@Column(name = "ward_number")
	private String wardNumber;
	
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

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getWardNumber() {
		return wardNumber;
	}

	public void setWardNumber(String wardNumber) {
		this.wardNumber = wardNumber;
	}

	public BedAvailability getBedAvailability() {
		return bedAvailability;
	}

	public void setBedAvailability(BedAvailability bedAvailability) {
		this.bedAvailability = bedAvailability;
	}
	
	
}
