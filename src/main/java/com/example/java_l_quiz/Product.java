package com.example.java_l_quiz;

import javafx.beans.property.*;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String description;

    public Product(int id, String name, double price, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    // Optional: If you want to use JavaFX properties for binding
    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public DoubleProperty priceProperty() {
        return new SimpleDoubleProperty(price);
    }

    public IntegerProperty quantityProperty() {
        return new SimpleIntegerProperty(quantity);
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(description);
    }
}
