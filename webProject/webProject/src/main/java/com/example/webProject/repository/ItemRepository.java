package com.example.webProject.repository;


import com.example.webProject.models.Client;
import com.example.webProject.models.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;


public interface ItemRepository extends CrudRepository<Item, Long>{
    //Iterable<Item> getItemsByClient(Client client);


}
