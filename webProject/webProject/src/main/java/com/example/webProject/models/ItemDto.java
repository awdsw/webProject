package com.example.webProject.models;

//DTO - data transfer object используется в проектах для того чтобы не вытаскивать Entity во фронт слой во избежании
// нарушения целостности данных в бд
public class ItemDto {

    private long id;
    private String description;
    private String dimensions;
    private String type_packaging;
    private String delivery_type;
    private String date_receipt;
    private String deliveryText;

    public ItemDto(Item item, boolean status) {
        this.id = item.getId();
        this.description = item.getDescription();
        this.dimensions = item.getDimensions();
        this.type_packaging = item.getType_packaging();
        this.delivery_type = item.getDelivery_type();
        this.date_receipt = item.getDate_receipt();
        if (item.getDelivery_type().equals(Delivery.DELIVERY)) {
            if (status) {
                this.deliveryText = "Менеджер отправил ваш заказ.";
            } else {
                this.deliveryText = "Вы выбрали доставку, ждите когда менеджер одобрит ваш заказ.";
            }
        } else if (item.getDelivery_type().equals(Delivery.PICKUP)) {
            this.deliveryText = "Вы выбрали самовывоз, можете приехать за заказом.";
        }
    }

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

    public String getDeliveryText() {
        return deliveryText;
    }

    public String getDate_receipt() {
        return date_receipt;
    }

    public void setDate_receipt(String date_receipt) {
        this.date_receipt = date_receipt;
    }

}