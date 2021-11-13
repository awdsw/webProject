package com.example.webProject.repository;

import com.example.webProject.models.Client;
import com.example.webProject.models.ClientItem;
import com.example.webProject.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ClientItemRepository extends CrudRepository<ClientItem, Long> {
    Iterable<ClientItem> getItemsByClient(Client id);

    Iterable<ClientItem> getClientItemsByClient(Client client);

    ClientItem getClientItemByItem(Item item);

    ClientItem getClientItemByClientAndItem(Client client, Item item);


}
