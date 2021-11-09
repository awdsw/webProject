package com.example.webProject.repository;

import com.example.webProject.entity.AppRole;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<AppRole, Long> {
}
