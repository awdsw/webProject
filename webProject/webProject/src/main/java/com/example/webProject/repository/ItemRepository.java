package com.example.webProject.repository;

import com.example.webProject.models.Item;
import org.springframework.data.repository.CrudRepository;


public interface ItemRepository extends CrudRepository<Item, Long>{


}