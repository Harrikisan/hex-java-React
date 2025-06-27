package com.casestudy.AmazeCare.Dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.casestudy.AmazeCare.Model.DoctorAppointment;
import com.casestudy.AmazeCare.Enum.Slot;

@Component
public class TodaysAppointmentDto {

    private int appointmentId;
    private int patientId;
    private String patientName;
    private Slot slot;
    private LocalDate date;

    // Getters and setters
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Conversion method
    public List<TodaysAppointmentDto> convertToDto(List<DoctorAppointment> appointments) {
        List<TodaysAppointmentDto> dtos = new ArrayList<>();
        for (DoctorAppointment a : appointments) {
            TodaysAppointmentDto dto = new TodaysAppointmentDto();
            dto.setAppointmentId(a.getId());
            dto.setPatientId(a.getPatient().getId());
            dto.setPatientName(a.getPatient().getName());
            dto.setSlot(a.getDoctorSchedule().getSlot());
            dto.setDate(a.getDate());
            dtos.add(dto);
        }
        return dtos;
    }
}
