package com.example.webProject.models;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    private String dimensions;
    private String type_packaging;
    private String delivery_type;
    private String date_receipt;
//    @OneToMany (fetch = FetchType.EAGER, mappedBy = "id")
//    private List<ClientItem> items;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id_client")
//   private Client client;


    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", dimensions='" + dimensions + '\'' +
                ", type_packaging='" + type_packaging + '\'' +
                ", delivery_type='" + delivery_type + '\'' +
                ", date_receipt=" + date_receipt +
                '}';
    }

//    public List<Item> getItems() {
//        return items;
//    }
//        public List<ClientItem> getItems() {
//        return items;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getType_packaging() {
        return type_packaging;
    }

    public void setType_packaging(String type_packaging) {
        this.type_packaging = type_packaging;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getDate_receipt() {
        return date_receipt;
    }

    public void setDate_receipt(String date_receipt) {
        this.date_receipt = date_receipt;
    }

//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }

    public Item() {
    }

    public Item(String description, String dimensions, String type_packaging, String delivery_type, String date_receipt) {
        this.description = description;
        this.dimensions = dimensions;
        this.type_packaging = type_packaging;
        this.delivery_type = delivery_type;
        this.date_receipt = date_receipt;
//       this.client = client;
    }
}
