package com.aacademy.JavaProjectAdvance.service.imp;

import com.aacademy.JavaProjectAdvance.exception.DuplicateResourceException;
import com.aacademy.JavaProjectAdvance.exception.ResourceNotFoundException;
import com.aacademy.JavaProjectAdvance.model.Bus;
import com.aacademy.JavaProjectAdvance.repository.BusRepository;
import com.aacademy.JavaProjectAdvance.service.impl.BusServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusServiceImpl busServiceImpl;

    @Test
    public void verifyByAll() {
        when(busRepository.findAll()).thenReturn(Collections.emptyList());
        busServiceImpl.findAll();
        verify(busRepository, times(1)).findAll();
    }

    @Test
    public void verifySave() {
        Bus busSave = Bus.builder().build();
        when(busRepository.save(any(Bus.class))).thenReturn(busSave);
        busServiceImpl.save(busSave);
        verify(busRepository, times(1)).save(busSave);
    }

    @Test
    public void verifyFindByNumber() {
        when(busRepository.findByNumber(any(String.class)))
                .thenReturn(Optional.of(Bus.builder().build()));
        busServiceImpl.findByNumber("1");
        verify(busRepository,times(1)).findByNumber("1");
    }

    @Test
    public void verifyFindById() {
        when(busRepository.findById(any(Long.class))).thenReturn(Optional.of(Bus.builder().build()));
        busServiceImpl.findById(1L);
        verify(busRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyDelete() {
        doNothing().when(busRepository).deleteById(any(Long.class));
        busServiceImpl.delete(1L);

        verify(busRepository, times(1)).deleteById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void verifyFindByIdException() {
        when(busRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        busServiceImpl.findById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void verifyFindByNumberException() {
        when(busRepository.findByNumber(any(String.class))).thenReturn(Optional.empty());

        busServiceImpl.findByNumber("1");
    }

    @Test(expected = DuplicateResourceException.class)
    public void verifySaveDuplicateException() {
        when(busRepository.save(any(Bus.class)))
                .thenThrow(DuplicateResourceException.class);

        busServiceImpl.save(Bus.builder().build());

    }


}
