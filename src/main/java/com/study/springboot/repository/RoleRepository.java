package com.study.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.springboot.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
