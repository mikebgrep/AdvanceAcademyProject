package com.aacademy.JavaProjectAdvance.service.imp;

import com.aacademy.JavaProjectAdvance.exception.DuplicateResourceException;
import com.aacademy.JavaProjectAdvance.exception.ResourceNotFoundException;
import com.aacademy.JavaProjectAdvance.model.Station;
import com.aacademy.JavaProjectAdvance.repository.StationRepository;
import com.aacademy.JavaProjectAdvance.service.StationService;
import com.aacademy.JavaProjectAdvance.service.impl.StationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationServiceImpl stationServiceImpl;


    @Test
    public void verifyByAll() {
        when(stationRepository.findAll()).thenReturn(Collections.emptyList());
        stationServiceImpl.findAll();
        verify(stationRepository, times(1)).findAll();
    }


    @Test
    public void verifyFindById() {
        when(stationRepository.findById(any(Long.class))).thenReturn(Optional.of(Station.builder().build()));
        stationServiceImpl.findById(1L);
        verify(stationRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyFindByName() {
        when(stationRepository.findByName(any(String.class))).thenReturn(Optional.of(Station.builder().build()));
        stationServiceImpl.findByName("Levski");
        verify(stationRepository, times(1)).findByName("Levski");
    }

    @Test
    public void verifySave() {
        Station saveStation = Station.builder().build();
        when(stationRepository.save(any(Station.class))).thenReturn(saveStation);
        stationServiceImpl.save(saveStation);
        verify(stationRepository, times(1)).save(saveStation);
    }

    @Test
    public void verifyDelete() {
        doNothing().when(stationRepository).deleteById(any(Long.class));
        stationServiceImpl.delete(1L);
        verify(stationRepository, times(1)).deleteById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void verifyFindByIdException() {
        when(stationRepository.findById(any(Long.class))).thenThrow(ResourceNotFoundException.class);
        stationServiceImpl.findById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void verifyFindByNameException() {
        when(stationRepository.findByName(any(String.class))).thenThrow(ResourceNotFoundException.class);
        stationServiceImpl.findByName("Levski");
    }

    @Test(expected = DuplicateResourceException.class)
    public void verifySaveDuplicateException() {
        when(stationRepository.save(any(Station.class))).thenThrow(DuplicateResourceException.class);
        stationServiceImpl.save(Station.builder().build());
    }
}
