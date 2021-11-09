package com.example.webProject.models;

//import javax.persistence.Entity;
import com.example.webProject.entity.AppUser;

import javax.persistence.*;
import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String client;
    private String phone;
    private String email;
    private String delivery_address;
    @OneToMany (fetch = FetchType.EAGER, mappedBy = "id")
    private List<ClientItem> clients;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private AppUser appUser;



    @Override
    public String toString() {
        return
//                "Client{" +
//                "id=" + id +
//                ", client='" +
                client
//                ", phone='" + phone + '\'' +
//                ", email='" + email + '\'' +
//                ", delivery_address='" + delivery_address
                ;
    }

    public List<ClientItem> getClients() {
        return clients;
    }


//        public List<Client> getClients() {
//        return clients;
//    }

    public String toJSON(){
        return "" + id+ "," + "\"" + client +"\""+ "," +"\"" + phone +"\""+ "," +"\"" + email +"\""
                + "," +"\"" + delivery_address +"\"";
    }

    public Client() {
    }

    public Client(String client, String phone, String email, String delivery_address) {
        this.client = client;
        this.phone = phone;
        this.email = email;
        this.delivery_address = delivery_address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }
}
