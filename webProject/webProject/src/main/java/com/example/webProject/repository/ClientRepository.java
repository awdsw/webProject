package com.example.webProject.repository;

import com.example.webProject.models.Client;
import org.springframework.data.repository.CrudRepository;


public interface ClientRepository extends CrudRepository<Client, Long> {
    Client getClientByClient(String name);

}