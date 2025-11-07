package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeRequestDTO;
import com.example.employee_management.dto.EmployeeResponseDTO;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.ResourceNotFoundException;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.specification.EmployeeSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    
    // Create Employee (Name and Email)
    @Override
    public EmployeeResponseDTO createBasic(String firstName, String email) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setEmail(email);

        Employee saved = repository.save(employee);
        return toDto(saved);
    }

    // Create Employee (Name, Email, and Phone)
    @Override
    public EmployeeResponseDTO createWithPhone(EmployeeRequestDTO dto) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());

        Employee saved = repository.save(employee);
        return toDto(saved);
    }

    //Search by Email using JPA Specification
    @Override
    public Optional<EmployeeResponseDTO> findByEmailSpec(String email) {
        Specification<Employee> spec = EmployeeSpecification.hasEmail(email);
        return repository.findOne(spec).map(this::toDto);
    }

    // Search by Email using HQL
    @Override
    public Optional<EmployeeResponseDTO> findByEmailHql(String email) {
        return repository.findByEmailHql(email).map(this::toDto);
    }

    // Search by Email using Native SQL
    @Override
    public Optional<EmployeeResponseDTO> findByEmailNative(String email) {
        return repository.findByEmailNative(email).map(this::toDto);
    }

    // Search by Name using JPA Specification
    @Override
    public List<EmployeeResponseDTO> findByNameSpec(String name) {
        Specification<Employee> spec = EmployeeSpecification.nameContains(name);
        List<Employee> employees = repository.findAll(spec);
        return employees.stream().map(this::toDto).collect(Collectors.toList());
    }

    // Search by Name using HQL
    @Override
    public List<EmployeeResponseDTO> findByNameHql(String name) {
        List<Employee> employees = repository.findByNameHql(name);
        return employees.stream().map(this::toDto).collect(Collectors.toList());
    }

    // Search by Name using Native SQL
    @Override
    public List<EmployeeResponseDTO> findByNameNative(String name) {
        List<Employee> employees = repository.findByNameNative(name);
        return employees.stream().map(this::toDto).collect(Collectors.toList());
    }

    // Update Employee Details (lastName, phone, address)
    @Override
    public EmployeeResponseDTO updateDetails(Long id, String lastName, String phone, String address) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for id: " + id));

        if (lastName != null && !lastName.trim().isEmpty())
            employee.setLastName(lastName);
        if (phone != null && !phone.trim().isEmpty())
            employee.setPhone(phone);
        if (address != null && !address.trim().isEmpty())
            employee.setAddress(address);

        Employee saved = repository.save(employee);
        return toDto(saved);
    }

    //Update Employee Phone Only
    @Override
    public EmployeeResponseDTO updatePhone(Long id, String phone) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for id: " + id));

        employee.setPhone(phone);
        Employee saved = repository.save(employee);
        return toDto(saved);
    }


    // Delete Employee by Email
    @Override
    public void deleteByEmail(String email) {
        Optional<Employee> maybe = repository.findByEmail(email);
        if (maybe.isEmpty()) {
            throw new ResourceNotFoundException("No employee found with email: " + email);
        }
        repository.delete(maybe.get());
    }

    private EmployeeResponseDTO toDto(Employee e) {
        return new EmployeeResponseDTO(
                e.getId(),
                e.getFirstName(),
                e.getLastName(),
                e.getEmail(),
                e.getPhone(),
                e.getAddress()
        );
    }
}
