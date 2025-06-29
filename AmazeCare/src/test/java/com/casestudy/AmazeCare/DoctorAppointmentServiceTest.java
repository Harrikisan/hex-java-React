package com.casestudy.AmazeCare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.casestudy.AmazeCare.Dto.DoctorAppointmentDto;
import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Exception.AppointmentNotFoundException;
import com.casestudy.AmazeCare.Exception.DoctorNotFoundException;
import com.casestudy.AmazeCare.Exception.DoctorScheduleNotFoundException;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;
import com.casestudy.AmazeCare.Model.Doctor;
import com.casestudy.AmazeCare.Model.DoctorAppointment;
import com.casestudy.AmazeCare.Model.DoctorSchedule;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Repoitory.DoctorAppointmentRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorRepository;
import com.casestudy.AmazeCare.Repoitory.DoctorScheduleRepository;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;
import com.casestudy.AmazeCare.Service.DoctorAppointmentService;

@SpringBootTest
public class DoctorAppointmentServiceTest {

    @InjectMocks
    private DoctorAppointmentService doctorAppointmentService;

    @Mock
    private DoctorAppointmentRepository doctorAppointmentRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private DoctorScheduleRepository doctorScheduleRepository;
    @Mock
    private DoctorAppointmentDto doctorAppointmentDto;

    private Doctor doctor;
    private Patient patient;
    private DoctorSchedule schedule;
    private DoctorAppointment appointment;

    @BeforeEach
    public void init() {
        doctor = new Doctor();
        doctor.setId(1);

        patient = new Patient();
        patient.setId(1);

        schedule = new DoctorSchedule();
        schedule.setId(1);

        appointment = new DoctorAppointment();
        appointment.setId(1);
        appointment.setDate(LocalDate.now());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDoctorSchedule(schedule);
    }

    @Test
    public void testAddDoctorAppointment() {
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(doctorScheduleRepository.findById(1)).thenReturn(Optional.of(schedule));
        when(doctorAppointmentRepository.save(appointment)).thenReturn(appointment);

        assertEquals(appointment, doctorAppointmentService.addDoctorAppointment(1, 1, 1, appointment));

        assertThrows(PatientNotFountException.class,
                () -> doctorAppointmentService.addDoctorAppointment(99, 1, 1, appointment));
        assertThrows(DoctorNotFoundException.class,
                () -> doctorAppointmentService.addDoctorAppointment(1, 99, 1, appointment));
        assertThrows(DoctorScheduleNotFoundException.class,
                () -> doctorAppointmentService.addDoctorAppointment(1, 1, 99, appointment));
    }

    @Test
    public void testEditAppointmentStatus() {
        when(doctorAppointmentRepository.findById(1)).thenReturn(Optional.of(appointment));
        when(doctorAppointmentRepository.save(appointment)).thenReturn(appointment);

        assertEquals(AppointmentStatus.APPOROVED, doctorAppointmentService.editAppointmentStatus("APPOROVED", 1).getStatus());

        assertThrows(AppointmentNotFoundException.class,
                () -> doctorAppointmentService.editAppointmentStatus("WAITING", 99));
        assertThrows(RuntimeException.class,
                () -> doctorAppointmentService.editAppointmentStatus("WAIT", 1));
    }

    @Test
    public void testGetAppointmentById() {
        when(doctorAppointmentRepository.findById(1)).thenReturn(Optional.of(appointment));
        assertEquals(appointment, doctorAppointmentService.getAppointmentById(1));

        assertThrows(RuntimeException.class, () -> doctorAppointmentService.getAppointmentById(99));
    }

    @AfterEach
    public void Delete() {
        doctor = null;
        patient = null;
        schedule = null;
        appointment = null;
    }
}
