package com.casestudy.AmazeCare.Dto;

import java.util.List;

import com.casestudy.AmazeCare.Model.Patient;

public class PatientAppointmentCountDto {
	private List<Patient> patients;
    private List<Integer> count;

    public PatientAppointmentCountDto(List<Patient> patients, List<Integer> count) {
        this.patients = patients;
        this.count = count;
    }

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public List<Integer> getCount() {
		return count;
	}

	public void setCount(List<Integer> count) {
		this.count = count;
	}
    

    
}
