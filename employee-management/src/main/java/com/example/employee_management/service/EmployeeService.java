package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeRequestDTO;
import com.example.employee_management.dto.EmployeeResponseDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    // Create operations
    EmployeeResponseDTO createBasic(String firstName, String email);
    EmployeeResponseDTO createWithPhone(EmployeeRequestDTO dto); // ← Add this line

    // Read operations
    Optional<EmployeeResponseDTO> findByEmailSpec(String email);
    Optional<EmployeeResponseDTO> findByEmailHql(String email);
    Optional<EmployeeResponseDTO> findByEmailNative(String email);

    List<EmployeeResponseDTO> findByNameSpec(String name);
    List<EmployeeResponseDTO> findByNameHql(String name);
    List<EmployeeResponseDTO> findByNameNative(String name);

    // Update operations
    EmployeeResponseDTO updateDetails(Long id, String lastName, String phone, String address);
    EmployeeResponseDTO updatePhone(Long id, String phone);

    // Delete operation
    void deleteByEmail(String email);
}
