package com.casestudy.AmazeCare.Dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.casestudy.AmazeCare.Enum.Day;
import com.casestudy.AmazeCare.Enum.Slot;

@Component
public class DoctorScheduleDto {

	private int id;
	private LocalDate date;
	private Day day;
	private Slot slot;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Day getDay() {
		return day;
	}
	public void setDay(Day day) {
		this.day = day;
	}
	public Slot getSlot() {
		return slot;
	}
	public void setSlot(Slot slot) {
		this.slot = slot;
	}
	
	
}
