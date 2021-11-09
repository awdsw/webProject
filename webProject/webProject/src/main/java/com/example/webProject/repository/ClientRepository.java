package com.example.webProject.repository;

import com.example.webProject.models.Client;
import com.example.webProject.models.ClientItem;
import com.example.webProject.models.Item;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ClientRepository extends CrudRepository<Client, Long> {
    Client getClientByClient(String name);
    //Client findByOrderByNameAsc(String name);
//    Iterable <Item> getItemsByClient(Client client);

    Object findAll(Sort client);

}
