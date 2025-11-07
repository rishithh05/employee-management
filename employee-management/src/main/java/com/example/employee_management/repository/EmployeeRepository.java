package com.example.employee_management.repository;

import com.example.employee_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    // HQL (JPQL)
    @Query("select e from Employee e where lower(e.email) = lower(:email)")
    Optional<Employee> findByEmailHql(@Param("email") String email);

    // Native SQL
    @Query(value = "SELECT * FROM employees e WHERE lower(e.email) = lower(:email)", nativeQuery = true)
    Optional<Employee> findByEmailNative(@Param("email") String email);

 
    @Query("select e from Employee e where lower(e.firstName) like lower(concat('%',:name,'%')) or lower(e.lastName) like lower(concat('%',:name,'%'))")
    List<Employee> findByNameHql(@Param("name") String name);

    
    @Query(value = "SELECT * FROM employees e WHERE lower(e.first_name) LIKE lower(concat('%',:name,'%')) OR lower(e.last_name) LIKE lower(concat('%',:name,'%'))", nativeQuery = true)
    List<Employee> findByNameNative(@Param("name") String name);

    // JPA 
    Optional<Employee> findByEmail(String email);
}
