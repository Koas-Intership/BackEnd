package com.example.koas.domain.admin.repository;

import com.example.koas.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>
{
    Optional<Admin> findByEmail(String Email);

}
