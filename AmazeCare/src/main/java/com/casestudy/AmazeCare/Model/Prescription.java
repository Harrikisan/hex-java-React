package com.casestudy.AmazeCare.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String medicineName;
	
	private String dosage;
	
	private boolean morning;
	
	private boolean afternoon;
	
	private boolean night;
	
	@Column(name = "before_meal")
	private boolean beforeMeal;
	
	@Column(name="after_meal")
	private boolean afterMeal;
	
	private String duration;
	
	@ManyToOne
	private MedicalRecord medicalRecord;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	

	public boolean isMorning() {
		return morning;
	}

	public void setMorning(boolean morning) {
		this.morning = morning;
	}

	public boolean isAfternoon() {
		return afternoon;
	}

	public void setAfternoon(boolean afternoon) {
		this.afternoon = afternoon;
	}

	public boolean isNight() {
		return night;
	}

	public void setNight(boolean night) {
		this.night = night;
	}

	public boolean isBeforeMeal() {
		return beforeMeal;
	}

	public void setBeforeMeal(boolean beforeMeal) {
		this.beforeMeal = beforeMeal;
	}

	public boolean isAfterMeal() {
		return afterMeal;
	}

	public void setAfterMeal(boolean afterMeal) {
		this.afterMeal = afterMeal;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	
	
	
}
