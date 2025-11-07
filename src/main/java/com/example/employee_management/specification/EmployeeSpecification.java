package com.example.employee_management.specification;

import com.example.employee_management.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    //  search by email
    public static Specification<Employee> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null || email.trim().isEmpty()) {
                return null;
            }
            return cb.equal(cb.lower(root.get("email")), email.toLowerCase());
        };
    }

    // search by name (firstName or lastName)
    public static Specification<Employee> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            String pattern = "%" + name.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), pattern),
                    cb.like(cb.lower(root.get("lastName")), pattern)
            );
        };
    }
}
