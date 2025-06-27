package com.casestudy.AmazeCare.Dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Model.DoctorAppointment;

@Component
public class DoctorAppointmentDto {

    private int appointmentId;
    private int patientId;
    private LocalDate date;
    private AppointmentStatus appointmentStatus;
    private String patientName;
    private String doctorName;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public List<DoctorAppointmentDto> convertToDto(List<DoctorAppointment> doctorAppointments) {
        List<DoctorAppointmentDto> dtos = new ArrayList<>();
        for (DoctorAppointment d : doctorAppointments) {
            DoctorAppointmentDto dto = new DoctorAppointmentDto();
            dto.setAppointmentId(d.getId());
            dto.setPatientId(d.getPatient().getId());
            dto.setDoctorName(d.getDoctor().getName());
            dto.setPatientName(d.getPatient().getName());
            dto.setDate(d.getDate());
            dto.setAppointmentStatus(d.getStatus());
            dtos.add(dto);
        }
        return dtos;
    }
}
