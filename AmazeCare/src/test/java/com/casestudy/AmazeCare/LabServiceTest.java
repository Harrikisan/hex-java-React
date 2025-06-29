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

import com.casestudy.AmazeCare.Enum.LabStatus;
import com.casestudy.AmazeCare.Exception.LabNotFoundException;
import com.casestudy.AmazeCare.Model.Lab;
import com.casestudy.AmazeCare.Repoitory.LabRepository;
import com.casestudy.AmazeCare.Service.LabService;

@SpringBootTest
public class LabServiceTest {

    @InjectMocks
    private LabService labService;

    @Mock
    private LabRepository labRepository;

    private Lab lab;

    @BeforeEach
    public void setup() {
        lab = new Lab();
        lab.setId(1);
        lab.setName("Test Lab");
        lab.setLabStatus(LabStatus.AVAILABLE);
    }

    @Test
    public void testAddLab() {
        when(labRepository.save(lab)).thenReturn(lab);
        Lab addedLab = labService.add(lab);
        assertEquals(LabStatus.AVAILABLE, addedLab.getLabStatus());
        assertEquals(lab, addedLab);
    }

    @Test
    public void testGetAllLabs() {
        List<Lab> labList = Arrays.asList(lab);
        when(labRepository.getAll()).thenReturn(labList);
        assertEquals(1, labService.getAll().size());
    }

    @Test
    public void testEditAvailability() {
        when(labRepository.findById(1)).thenReturn(Optional.of(lab));
        when(labRepository.save(lab)).thenReturn(lab);

        Lab updatedLab = labService.editAvailability(1, LabStatus.IMPROVEMENT);
        assertEquals(LabStatus.IMPROVEMENT, updatedLab.getLabStatus());

        when(labRepository.findById(99)).thenReturn(Optional.empty());
        LabNotFoundException exception = assertThrows(LabNotFoundException.class, () -> {
            labService.editAvailability(99, LabStatus.AVAILABLE);
        });
        assertEquals("Lab not found", exception.getMessage());
    }

    @AfterEach
    public void delete() {
        lab = null;
    }
}
