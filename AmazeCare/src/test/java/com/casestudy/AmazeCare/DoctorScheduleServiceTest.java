package com.casestudy.AmazeCare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.casestudy.AmazeCare.Enum.Day;
import com.casestudy.AmazeCare.Enum.ScheduleStatus;
import com.casestudy.AmazeCare.Enum.Slot;
import com.casestudy.AmazeCare.Exception.DoctorNotFoundException;
import com.casestudy.AmazeCare.Exception.DoctorScheduleNotFoundException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.DoctorSchedule;
import com.casestudy.AmazeCare.Repoitory.DoctorAppointmentRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorScheduleRepository;
import com.casestudy.AmazeCare.Service.DoctorScheduleService;

@SpringBootTest
public class DoctorScheduleServiceTest {

    @InjectMocks
    private DoctorScheduleService doctorScheduleService;

    @Mock
    private DoctorScheduleRepository doctorScheduleRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorAppointmentRepository doctorAppointmentRepository;

    private Doctor doctor;
    private DoctorSchedule doctorSchedule;

    @BeforeEach
    public void setup() {
        doctor = new Doctor();
        doctor.setId(1);
        doctor.setName("Dr. Test");

        doctorSchedule = new DoctorSchedule();
        doctorSchedule.setId(1);
        doctorSchedule.setDay(Day.MONDAY);
        doctorSchedule.setSlot(Slot.FIVE);
        doctorSchedule.setStatus(ScheduleStatus.AVAILABLE);
        doctorSchedule.setDoctor(doctor);
    }

    @Test
    public void testAddScheduleByUsername() {
        when(doctorRepository.getByUsername("testUser")).thenReturn(Optional.of(doctor));
        when(doctorScheduleRepository.save(doctorSchedule)).thenReturn(doctorSchedule);

        DoctorSchedule savedSchedule = doctorScheduleService.addSchedule("testUser", doctorSchedule);
        assertEquals(doctorSchedule, savedSchedule);

        DoctorNotFoundException ex = assertThrows(DoctorNotFoundException.class, () -> {
            doctorScheduleService.addSchedule("wrongUser", doctorSchedule);
        });
        assertEquals("Doctor not available or active", ex.getMessage());
    }

    @Test
    public void testAddScheduleById() {
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(doctorScheduleRepository.save(doctorSchedule)).thenReturn(doctorSchedule);

        DoctorSchedule saved = doctorScheduleService.addSchedule(1, doctorSchedule);
        assertEquals(doctorSchedule, saved);

        DoctorNotFoundException ex = assertThrows(DoctorNotFoundException.class, () -> {
            doctorScheduleService.addSchedule(99, doctorSchedule);
        });
        assertEquals("Doctor not found", ex.getMessage());
    }

    @Test
    public void testGetByDoctorId() {
        when(doctorScheduleRepository.getByDoctor(1)).thenReturn(Arrays.asList(doctorSchedule));
        List<DoctorSchedule> list = doctorScheduleService.getByDoctor(1);
        assertEquals(1, list.size());
    }

    @Test
    public void testGetAvailableSlotsForDate() {
        LocalDate date = LocalDate.now();
        when(doctorScheduleRepository.getAvailableSlotsForDate(1, date, Day.valueOf(date.getDayOfWeek().toString())))
                .thenReturn(Arrays.asList(doctorSchedule));
        List<DoctorSchedule> list = doctorScheduleService.getAvailableSlotsForDate(1, date);
        assertEquals(1, list.size());
    }

    @Test
    public void testEditStatus() {
        when(doctorScheduleRepository.findById(1)).thenReturn(Optional.of(doctorSchedule));
        when(doctorScheduleRepository.save(doctorSchedule)).thenReturn(doctorSchedule);

        DoctorSchedule updated = doctorScheduleService.editStatus(1, "NOTAVAILABLE");
        assertEquals(ScheduleStatus.NOTAVAILABLE, updated.getStatus());

        DoctorScheduleNotFoundException ex = assertThrows(DoctorScheduleNotFoundException.class, () -> {
            doctorScheduleService.editStatus(99, "AVAILABLE");
        });
        assertEquals("record not found", ex.getMessage());
    }


    @AfterEach
    public void delete() {
        doctor = null;
        doctorSchedule = null;
    }
}
