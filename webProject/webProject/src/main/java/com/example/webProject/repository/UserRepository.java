package com.example.webProject.repository;

import com.example.webProject.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    AppUser getAppUserByUserName(String name);

}
