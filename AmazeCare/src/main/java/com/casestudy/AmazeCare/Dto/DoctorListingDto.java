package com.casestudy.AmazeCare.Dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.casestudy.AmazeCare.Model.Doctor;

@Component
public class DoctorListingDto {

    private int id;
    private String name;
    private String phone;
    private String specilization;
    private String imageUrl; // ✅ New field

    // ✅ Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecilization() {
        return specilization;
    }

    public void setSpecilization(String specilization) {
        this.specilization = specilization;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // ✅ Mapping method
    public List<DoctorListingDto> convertToDto(List<Doctor> doctorList) {
        List<DoctorListingDto> dtoList = new ArrayList<>();

        doctorList.forEach(d -> {
            DoctorListingDto dto = new DoctorListingDto();
            dto.setId(d.getId());
            dto.setName(d.getName());
            dto.setSpecilization(d.getSpecilization());
            dto.setPhone(d.getPhone());
            dto.setImageUrl(d.getImageUrl()); // ✅ Set image URL
            dtoList.add(dto);
        });

        return dtoList;
    }
}
