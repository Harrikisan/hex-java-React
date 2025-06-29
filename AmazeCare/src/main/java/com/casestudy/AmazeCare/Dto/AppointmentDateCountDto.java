package com.casestudy.AmazeCare.Dto;

import java.time.LocalDate;
import java.util.List;

public class AppointmentDateCountDto {

	private List<LocalDate> dates;
    private List<Long> counts;

    public AppointmentDateCountDto(List<LocalDate> dates, List<Long> counts) {
        this.dates = dates;
        this.counts = counts;
    }

	public List<LocalDate> getDates() {
		return dates;
	}

	public void setDates(List<LocalDate> dates) {
		this.dates = dates;
	}

	public List<Long> getCounts() {
		return counts;
	}

	public void setCounts(List<Long> counts) {
		this.counts = counts;
	}
    
    
}
