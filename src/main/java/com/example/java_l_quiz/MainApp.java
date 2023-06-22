package com.example.java_l_quiz;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class MainApp extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private TableView<Product> table;
    private TextField nameField;
    private TextField priceField;
    private TextField quantityField;
    private TextField descriptionField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        table = new TableView<>();
        table.setItems(getProducts());

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        table.getColumns().addAll(nameColumn, priceColumn, quantityColumn, descriptionColumn);

        VBox formLayout = new VBox();
        formLayout.setSpacing(10);
        formLayout.setPadding(new Insets(10));
        formLayout.getChildren().addAll(
                createLabelAndTextField("Name", nameField = new TextField()),
                createLabelAndTextField("Price", priceField = new TextField()),
                createLabelAndTextField("Quantity", quantityField = new TextField()),
                createLabelAndTextField("Description", descriptionField = new TextField())
        );

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addProduct());

        HBox buttonLayout = new HBox(addButton);
        buttonLayout.setPadding(new Insets(10));

        VBox root = new VBox();
        root.getChildren().addAll(table, formLayout, buttonLayout);

        Scene scene = new Scene(root, 400, 500);

        primaryStage.setTitle("Product List");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Product> getProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Product")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");

                Product product = new Product(id, name, price, quantity, description);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    private void addProduct() {
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        String description = descriptionField.getText();

        Product product = new Product(0, name, price, quantity, description);
        saveProduct(product);

        table.getItems().add(product);

        // Clear input fields
        nameField.clear();
        priceField.clear();
        quantityField.clear();
        descriptionField.clear();
    }

    private void saveProduct(Product product) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Product (name, price, quantity, description) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setString(4, product.getDescription());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private HBox createLabelAndTextField(String labelText, TextField textField) {
        Label label = new Label(labelText);
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(label, textField);
        return hbox;
    }
}


