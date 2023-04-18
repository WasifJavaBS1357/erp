package com.brainstation23.erp.service;

import com.brainstation23.erp.model.dto.CreateEmployeeRequest;
import com.brainstation23.erp.model.dto.UpdateEmployeeRequest;
import com.brainstation23.erp.persistence.entity.EmployeeEntity;
import com.brainstation23.erp.persistence.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should create an employee")
    public void testCreateOne() {
        // given
        CreateEmployeeRequest createRequest = mock(CreateEmployeeRequest.class);
        EmployeeEntity createdEntity = mock(EmployeeEntity.class);
        UUID uuid = UUID.randomUUID();
        when(employeeRepository.save(any())).thenReturn(createdEntity);
        when(createdEntity.getId()).thenReturn(uuid);

        // when
        UUID actualUUID = employeeService.createOne(createRequest);

        // when
        Assertions.assertEquals(uuid, actualUUID);
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("should update an employee with a valid id")
    public void testUpdateOneWithValidId() {
        // given
        UUID uuid = UUID.randomUUID();
        UpdateEmployeeRequest updateRequest = mock(UpdateEmployeeRequest.class);
        EmployeeEntity entity = mock(EmployeeEntity.class);
        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(entity));

        // when
        employeeService.updateOne(uuid, updateRequest);

        // then
        verify(entity, times(1)).setFirstName(updateRequest.getFirstName());
        verify(entity, times(1)).setLastName(updateRequest.getLastName());
        verify(entity, times(1)).setUserName(updateRequest.getUserName());
        verify(entity, times(1)).setEmail(updateRequest.getEmail());
        verify(entity, times(1)).setPassword(updateRequest.getPassword());
        verify(entity, times(1)).setBalance(updateRequest.getBalance());
        verify(employeeRepository, times(1)).save(entity);
    }
}
