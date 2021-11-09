package com.example.webProject.models;

import com.example.webProject.entity.AppUser;

import javax.persistence.*;

@Entity
public class ClientItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_item")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client")
    private Client client;

    private boolean order_status;


    @Override
    public String toString() {
        return "" + client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isOrder_status() {
        return order_status;
    }

    public void setOrder_status(boolean order_status) {
        this.order_status = order_status;
    }

    public ClientItem() {
    }

//    public ClientItems(Item item, Client client, boolean order_status) {
//        this.item = item;
//        this.client = client;
//        this.order_status = order_status;
//    }

    public ClientItem(Item item, Client client) {
        this.item = item;
        this.client = client;
    }
}