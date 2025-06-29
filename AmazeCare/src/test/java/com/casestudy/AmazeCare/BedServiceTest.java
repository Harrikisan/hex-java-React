package com.casestudy.AmazeCare;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.casestudy.AmazeCare.Enum.BedAvailability;
import com.casestudy.AmazeCare.Enum.LabStatus;
import com.casestudy.AmazeCare.Exception.BedNotFoundException;
import com.casestudy.AmazeCare.Exception.WardNotFoundException;
import com.casestudy.AmazeCare.Model.Bed;
import com.casestudy.AmazeCare.Model.Ward;
import com.casestudy.AmazeCare.Repoitory.BedRepository;
import com.casestudy.AmazeCare.Repoitory.WardRepository;
import com.casestudy.AmazeCare.Service.BedService;

@SpringBootTest
public class BedServiceTest {

    @InjectMocks
    private BedService bedService;

    @Mock
    private BedRepository bedRepository;

    @Mock
    private WardRepository wardRepository;

    private Bed bed;
    private Ward ward;

    @BeforeEach
    public void setUp() {
        ward = new Ward();
        ward.setId(1);
        ward.setWard_number("W1");
        ward.setStatus(LabStatus.AVAILABLE);

        bed = new Bed();
        bed.setId(1);
        bed.setNumber("B101");
        bed.setWard(ward);
        bed.setBedAvailability(BedAvailability.AVAILABLE);
    }

    @Test
    public void testAddBed() {
        when(wardRepository.findById(1)).thenReturn(Optional.of(ward));
        when(bedRepository.save(bed)).thenReturn(bed);

        assertEquals(bed, bedService.addBed(1, bed));

        WardNotFoundException e = assertThrows(WardNotFoundException.class,
                () -> bedService.addBed(99, bed));
        assertEquals("Ward ID not found", e.getMessage());
    }

    @Test
    public void testUpdateBed() {
        Bed updatedBed = new Bed();
        updatedBed.setNumber("B202");
        updatedBed.setWard(ward);
        updatedBed.setBedAvailability(BedAvailability.AVAILABLE);

        when(bedRepository.findById(1)).thenReturn(Optional.of(bed));
        when(bedRepository.save(bed)).thenReturn(bed);

        assertEquals(bed, bedService.updateBed(1, updatedBed));

        BedNotFoundException e = assertThrows(BedNotFoundException.class,
                () -> bedService.updateBed(99, updatedBed));
        assertEquals("Bed not found with id: 99", e.getMessage());
    }

    @Test
    public void testGetAllBeds() {
        when(bedRepository.findAll()).thenReturn(Arrays.asList(bed));
        List<Bed> beds = bedService.getAllBeds();
        assertEquals(1, beds.size());
    }

    @Test
    public void testGetBedsByAvailability() {
        when(bedRepository.findByBedAvailability(BedAvailability.AVAILABLE)).thenReturn(Arrays.asList(bed));
        List<Bed> beds = bedService.getBedsByAvailability(BedAvailability.AVAILABLE);
        assertEquals(1, beds.size());
    }

    @Test
    public void testChangeAvailability() {
        when(bedRepository.findById(1)).thenReturn(Optional.of(bed));
        when(bedRepository.save(bed)).thenReturn(bed);

        assertEquals(bed, bedService.changeAvailability(1, BedAvailability.NOTAVAILABLE));

        Bed wardUnavailableBed = new Bed();
        Ward unavailableWard = new Ward();
        unavailableWard.setStatus(LabStatus.IMPROVEMENT);
        wardUnavailableBed.setWard(unavailableWard);

        when(bedRepository.findById(2)).thenReturn(Optional.of(wardUnavailableBed));

        WardNotFoundException e = assertThrows(WardNotFoundException.class,
                () -> bedService.changeAvailability(2, BedAvailability.AVAILABLE));
        assertEquals("Ward not available", e.getMessage());

        BedNotFoundException ex = assertThrows(BedNotFoundException.class,
                () -> bedService.changeAvailability(99, BedAvailability.NOTAVAILABLE));
        assertEquals("Bed not found with id: 99", ex.getMessage());
    }

    @AfterEach
    public void Delete() {
        bed = null;
        ward = null;
    }
} 
