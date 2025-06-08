package com.casestudy.AmazeCare;

import com.casestudy.AmazeCare.Enum.BedAvailability;
import com.casestudy.AmazeCare.Exception.BedNotFoundException;
import com.casestudy.AmazeCare.Model.Bed;
import com.casestudy.AmazeCare.Repoitory.BedRepository;
import com.casestudy.AmazeCare.Service.BedService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BedServiceTest {

    @Mock
    private BedRepository bedRepository;

    @InjectMocks
    private BedService bedService;

    private Bed testBed;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testBed = new Bed();
        testBed.setId(1);
        testBed.setNumber("B101");
        testBed.setRoomNumber("R1");
        testBed.setWardNumber("W1");
        testBed.setBedAvailability(BedAvailability.AVAILABLE);
    }

    @Test
    public void testAddBed_ShouldSetNotAvailableAndSave() {
        Bed expectedBed = new Bed();
        expectedBed.setNumber("B101");
        expectedBed.setRoomNumber("R1");
        expectedBed.setWardNumber("W1");
        expectedBed.setBedAvailability(BedAvailability.NOTAVAILABLE);

        when(bedRepository.save(any(Bed.class))).thenReturn(expectedBed);

        Bed result = bedService.addBed(testBed);

        assertEquals(BedAvailability.NOTAVAILABLE, result.getBedAvailability());
        assertEquals("B101", result.getNumber());
        verify(bedRepository, times(1)).save(testBed);
    }

    @Test
    public void testUpdateBed_ShouldUpdateFieldsAndReturnUpdatedBed() {
        when(bedRepository.findById(1)).thenReturn(Optional.of(testBed));
        when(bedRepository.save(any(Bed.class))).thenReturn(testBed);

        Bed updated = new Bed();
        updated.setNumber("B102");
        updated.setRoomNumber("R2");
        updated.setWardNumber("W2");
        updated.setBedAvailability(BedAvailability.NOTAVAILABLE);

        Bed result = bedService.updateBed(1, updated);

        assertEquals("B102", result.getNumber());
        assertEquals("R2", result.getRoomNumber());
        assertEquals("W2", result.getWardNumber());
        assertEquals(BedAvailability.NOTAVAILABLE, result.getBedAvailability());
    }

    @Test
    public void testChangeAvailability_ShouldUpdateAvailability() {
        when(bedRepository.findById(1)).thenReturn(Optional.of(testBed));
        when(bedRepository.save(any(Bed.class))).thenReturn(testBed);

        Bed result = bedService.changeAvailability(1, BedAvailability.NOTAVAILABLE);

        assertEquals(BedAvailability.NOTAVAILABLE, result.getBedAvailability());
        verify(bedRepository).save(testBed);
    }

    @Test
    public void testGetAllBeds_ShouldReturnList() {
        when(bedRepository.findAll()).thenReturn(Arrays.asList(testBed));

        List<Bed> result = bedService.getAllBeds();

        assertEquals(1, result.size());
        assertEquals("B101", result.get(0).getNumber());
    }

    @Test
    public void testGetBedsByAvailability_ShouldReturnFilteredList() {
        when(bedRepository.findByBedAvailability(BedAvailability.AVAILABLE)).thenReturn(Arrays.asList(testBed));

        List<Bed> result = bedService.getBedsByAvailability(BedAvailability.AVAILABLE);

        assertEquals(1, result.size());
        assertEquals(BedAvailability.AVAILABLE, result.get(0).getBedAvailability());
    }

    @Test
    public void testUpdateBed_NotFound_ShouldThrowException() {
        when(bedRepository.findById(2)).thenReturn(Optional.empty());

        Bed newBed = new Bed();
        Exception exception = assertThrows(BedNotFoundException.class, () -> {
            bedService.updateBed(2, newBed);
        });

        assertTrue(exception.getMessage().contains("Bed not found with id: 2"));
    }
}
