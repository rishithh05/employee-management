package com.example.employee_management;

import com.example.employee_management.dto.EmployeeRequestDTO;
import com.example.employee_management.dto.EmployeeResponseDTO;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.ResourceNotFoundException;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createBasic_shouldReturnCreated() {
        Employee e = new Employee();
        e.setId(1L);
        e.setFirstName("John");
        e.setEmail("john@example.com");

        when(repository.save(any(Employee.class))).thenReturn(e);

        EmployeeResponseDTO dto = service.createBasic("John", "john@example.com");

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("John", dto.getFirstName());
        verify(repository, times(1)).save(any(Employee.class));
    }

    @Test
    public void updatePhone_whenNotFound_shouldThrow() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updatePhone(99L, "99999"));
    }

    @Test
    public void findByEmailHql_shouldReturnOptional() {
        Employee e = new Employee();
        e.setId(2L);
        e.setEmail("a@b.com");
        when(repository.findByEmailHql("a@b.com")).thenReturn(Optional.of(e));
        Optional<EmployeeResponseDTO> result = service.findByEmailHql("a@b.com");
        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getId());
    }
}
