package com.example.koas.domain.admin.repository;

import com.example.koas.domain.admin.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
}
