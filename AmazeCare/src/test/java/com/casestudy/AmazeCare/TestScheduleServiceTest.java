package com.casestudy.AmazeCare;

import com.casestudy.AmazeCare.Enum.Day;
import com.casestudy.AmazeCare.Model.TestSchedule;
import com.casestudy.AmazeCare.Repoitory.LabRepository;
import com.casestudy.AmazeCare.Repoitory.TestAppointmentRepository;
import com.casestudy.AmazeCare.Repoitory.TestScheduleRepository;
import com.casestudy.AmazeCare.Service.TestScheduleService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestScheduleServiceTest {

    @InjectMocks
    private TestScheduleService testScheduleService;

    @Mock
    private TestScheduleRepository testScheduleRepository;

    @Mock
    private LabRepository labRepository;

    @Mock
    private TestAppointmentRepository testAppointmentRepository;

    private TestSchedule testSchedule;

    @BeforeEach
    public void setUp() {
        testSchedule = new TestSchedule();
        testSchedule.setId(1);
    }

    @Test
    public void testAddScheduleSuccess() {
        when(testScheduleRepository.save(testSchedule)).thenReturn(testSchedule);
        TestSchedule saved = testScheduleService.add(testSchedule);
        assertEquals(testSchedule, saved);
    }

    @Test
    public void testGetAvailableSlotsSuccessAndEmpty() {
        int labId = 1;
        LocalDate date = LocalDate.now();
        Day day = Day.valueOf(date.getDayOfWeek().toString());

        when(testScheduleRepository.getAvailableDatesForLabTest(labId, day, date)).thenReturn(Collections.emptyList());

        List<TestSchedule> result = testScheduleService.getAvailableSlots(labId, date);
        assertEquals(0, result.size());
    }

    @AfterEach
    public void Delete() {
        testSchedule = null;
    }
}
