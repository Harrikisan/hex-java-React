package com.casestudy.AmazeCare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.casestudy.AmazeCare.Enum.AppointmentStatus;
import com.casestudy.AmazeCare.Enum.PatientType;
import com.casestudy.AmazeCare.Exception.AppointmentNotFoundException;
import com.casestudy.AmazeCare.Exception.BedNotFoundException;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;
import com.casestudy.AmazeCare.Model.Bed;
import com.casestudy.AmazeCare.Model.BedBooking;
import com.casestudy.AmazeCare.Model.Patient;
import com.casestudy.AmazeCare.Repoitory.BedBookingRepository;
import com.casestudy.AmazeCare.Repoitory.BedRepository;
import com.casestudy.AmazeCare.Repoitory.PatientRepository;
import com.casestudy.AmazeCare.Service.BedBookingService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BedBookingServiceTest {

    @InjectMocks
    private BedBookingService bedBookingService;

    @Mock
    private BedBookingRepository bedBookingRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private BedRepository bedRepository;

    private BedBooking bedBooking;
    private Patient patient;
    private Bed bed;

    @BeforeEach
    public void setup() {
        patient = new Patient();
        patient.setId(10);
        patient.setName("Ravi Kumar");

        bed = new Bed();
        bed.setId(20);
        bed.setNumber("B12");

        bedBooking = new BedBooking();
        bedBooking.setId(100);
        bedBooking.setPatirnt(patient);
        bedBooking.setBed(bed);
        bedBooking.setStatus(AppointmentStatus.APPOROVED);
        bedBooking.setPatientType(PatientType.INPATIENT);
    }

    @Test
    public void testBookBed() {
        when(patientRepository.findById(10)).thenReturn(Optional.of(patient));
        when(bedRepository.findById(20)).thenReturn(Optional.of(bed));
        when(bedBookingRepository.save(bedBooking)).thenReturn(bedBooking);

        BedBooking savedBooking = bedBookingService.bookBed(bedBooking, 10, 20);
        assertEquals(bedBooking, savedBooking);

        PatientNotFountException patientEx = assertThrows(PatientNotFountException.class,
                () -> bedBookingService.bookBed(bedBooking, 99, 20));
        assertEquals("Patient not found with id: 99", patientEx.getMessage());

        BedNotFoundException bedEx = assertThrows(BedNotFoundException.class,
                () -> bedBookingService.bookBed(bedBooking, 10, 999));
        assertEquals("Bed not found with id: 999", bedEx.getMessage());
    }

    @Test
    public void testGetById() {
        when(bedBookingRepository.findById(100)).thenReturn(Optional.of(bedBooking));
        BedBooking found = bedBookingService.getById(100);
        assertEquals(bedBooking, found);

        AppointmentNotFoundException ex = assertThrows(AppointmentNotFoundException.class,
                () -> bedBookingService.getById(999));
        assertEquals("Booking not found", ex.getMessage());
    }

    @Test
    public void testGetAll() {
        List<BedBooking> expected = Arrays.asList(bedBooking);
        when(bedBookingRepository.findAll()).thenReturn(expected);
        assertEquals(expected, bedBookingService.getAll());
    }

    @Test
    public void testGetByUsername() {
        when(patientRepository.getbyUsername("ravi")).thenReturn(patient);
        when(bedBookingRepository.findByPatirntId(10)).thenReturn(Arrays.asList(bedBooking));
        List<BedBooking> bookings = bedBookingService.getByUsername("ravi");
        assertEquals(1, bookings.size());
        assertEquals(bedBooking, bookings.get(0));
    }

    @Test
    public void testGetByBedId() {
        when(bedBookingRepository.findByBedId(20)).thenReturn(Arrays.asList(bedBooking));
        List<BedBooking> list = bedBookingService.getByBedId(20);
        assertEquals(1, list.size());
    }

    @Test
    public void testCancelBooking() {
        when(bedBookingRepository.findById(100)).thenReturn(Optional.of(bedBooking));
        bedBooking.setStatus(AppointmentStatus.CANCELED);
        when(bedBookingRepository.save(bedBooking)).thenReturn(bedBooking);

        BedBooking canceled = bedBookingService.cancelBooking(100);
        assertEquals(AppointmentStatus.CANCELED, canceled.getStatus());
    }

    @AfterEach
    public void delete() {
        bedBooking = null;
        patient = null;
        bed = null;
    }
}
