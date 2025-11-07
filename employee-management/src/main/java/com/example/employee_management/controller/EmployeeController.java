package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeRequestDTO;
import com.example.employee_management.dto.EmployeeResponseDTO;
import com.example.employee_management.service.EmployeeService;
import com.example.employee_management.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> rootMessage() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", " Employee API is running. Use endpoints like /by-email, /by-name, /create/basic, etc.");
       
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Fetch Employee Details by Email
    @GetMapping("/by-email")
    public ResponseEntity<EmployeeResponseDTO> getByEmail(
            @RequestParam @NotBlank @Email String email,
            @RequestParam(defaultValue = "spec") String method) {

        return serviceMethodEmail(email, method)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + email));
    }

    private java.util.Optional<EmployeeResponseDTO> serviceMethodEmail(String email, String method) {
        switch (method.toLowerCase()) {
            case "hql":
                return service.findByEmailHql(email);
            case "native":
                return service.findByEmailNative(email);
            default:
                return service.findByEmailSpec(email);
        }
    }

    // Fetch Employee Details by Name
    @GetMapping("/by-name")
    public ResponseEntity<List<EmployeeResponseDTO>> getByName(
            @RequestParam @NotBlank String name,
            @RequestParam(defaultValue = "spec") String method) {

        List<EmployeeResponseDTO> result;
        switch (method.toLowerCase()) {
            case "hql":
                result = service.findByNameHql(name);
                break;
            case "native":
                result = service.findByNameNative(name);
                break;
            default:
                result = service.findByNameSpec(name);
        }
        return ResponseEntity.ok(result);
    }

    // Create Employee (Name and Email)
    @PostMapping("/create/basic")
    public ResponseEntity<EmployeeResponseDTO> createBasic(@RequestParam @NotBlank String firstName,
                                                           @RequestParam @NotBlank @Email String email) {
        EmployeeResponseDTO created = service.createBasic(firstName, email);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //Create Employee (Name, Email, and Phone)
    @PostMapping("/create/with-phone")
    public ResponseEntity<EmployeeResponseDTO> createWithPhone(@Valid @RequestBody EmployeeRequestDTO dto) {
        if (dto.getPhone() == null || dto.getPhone().isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }
        EmployeeResponseDTO created = service.createWithPhone(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Update Employee Details (Last Name, Phone, and Address)
    @PutMapping("/{id}/details")
    public ResponseEntity<EmployeeResponseDTO> updateDetails(@PathVariable Long id,
                                                             @RequestBody EmployeeRequestDTO dto) {
        EmployeeResponseDTO updated = service.updateDetails(id, dto.getLastName(), dto.getPhone(), dto.getAddress());
        return ResponseEntity.ok(updated);
    }

    // Update Employee Phone Only
    @PatchMapping("/{id}/phone")
    public ResponseEntity<EmployeeResponseDTO> updatePhone(@PathVariable Long id,
                                                           @RequestParam String phone) {
        EmployeeResponseDTO updated = service.updatePhone(id, phone);
        return ResponseEntity.ok(updated);
    }

    // Delete Employee by Email
    @DeleteMapping("/by-email")
    public ResponseEntity<String> deleteByEmail(@RequestParam @NotBlank @Email String email) {
        service.deleteByEmail(email);
        return ResponseEntity.ok("Deleted employee with email: " + email);
    }
}
