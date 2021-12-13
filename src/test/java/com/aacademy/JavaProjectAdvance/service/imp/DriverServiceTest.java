package com.aacademy.JavaProjectAdvance.service.imp;

import com.aacademy.JavaProjectAdvance.exception.DuplicateResourceException;
import com.aacademy.JavaProjectAdvance.exception.ResourceNotFoundException;
import com.aacademy.JavaProjectAdvance.model.Driver;
import com.aacademy.JavaProjectAdvance.repository.DriverRepository;
import com.aacademy.JavaProjectAdvance.service.impl.DriverServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverServiceImpl driverServiceImpl;


    @Test
    public void verifyFindAll() {
        when(driverRepository.findAll()).thenReturn(Collections.emptyList());
        driverServiceImpl.findAll();
        verify(driverRepository, times(1)).findAll();
    }

    @Test
    public void verifyFindById() {
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(Driver.builder().build()));
        driverServiceImpl.findById(1L);
        verify(driverRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyFindByDriverNumber() {
        when(driverRepository.findByDriverNumber(any(String.class))).thenReturn(Optional.of(Driver.builder().build()));
        driverServiceImpl.findByDriverNumber("12345");
        verify(driverRepository, times(1)).findByDriverNumber("12345");
    }

    @Test
    public void verifySave() {
        Driver savedDriver = Driver.builder().build();
        when(driverRepository.save(any(Driver.class))).thenReturn(savedDriver);
        driverServiceImpl.save(savedDriver);
        verify(driverRepository, times(1)).save(savedDriver);
    }

    @Test
    public void verifyDelete() {
        doNothing().when(driverRepository).deleteById(any(Long.class));
        driverServiceImpl.delete(1L);
        verify(driverRepository, times(1)).deleteById(1L);
    }

    @Test(expected = DuplicateResourceException.class)
    public void verifyDuplicateException() {
        when(driverRepository.save(any(Driver.class))).thenThrow(DuplicateResourceException.class);
        driverServiceImpl.save(Driver.builder().build());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void verifyFindByDriverNumberException() {
        when(driverRepository.findByDriverNumber(any(String.class))).thenThrow(ResourceNotFoundException.class);
        driverServiceImpl.findByDriverNumber("12345");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void verifyByIdException() {
        when(driverRepository.findById(any(Long.class))).thenThrow(ResourceNotFoundException.class);
        driverServiceImpl.findById(1L);
    }

}
