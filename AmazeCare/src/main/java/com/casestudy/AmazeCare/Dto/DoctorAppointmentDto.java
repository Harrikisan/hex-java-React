package com.casestudy.AmazeCare.Dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Model.DoctorAppointment;

@Component
public class DoctorAppointmentDto {

	private int id;
	private LocalDate date;
	private AppointmentStatus appointmentStatus;
	private String patientName;
	private String DoctorName;
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
	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDoctorName() {
		return DoctorName;
	}
	public void setDoctorName(String doctorName) {
		DoctorName = doctorName;
	}
	
	public List<DoctorAppointmentDto> convertToDto(List<DoctorAppointment> doctorAppointment){
		List<DoctorAppointmentDto> doctorAppointmentDtos=new ArrayList<>();
		doctorAppointment.stream().forEach(d->{
			DoctorAppointmentDto dto=new DoctorAppointmentDto();
			dto.setId(d.getId());
			dto.setDoctorName(d.getDoctor().getName());
			dto.setDate(d.getDate());
			dto.setAppointmentStatus(d.getStatus());
			dto.setPatientName(d.getPatient().getName());
			doctorAppointmentDtos.add(dto);
		});
		return doctorAppointmentDtos;
	}
}
