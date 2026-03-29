package controllers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class FXApp extends Application {

    private ResourceBundle msg;

    @Override
    public void start(Stage stage) {

        ComboBox<String> langBox = new ComboBox<>();
        langBox.getItems().addAll("English", "Finnish", "Swedish");
        langBox.setValue("English");

        Button startBtn = new Button("OK");

        VBox root = new VBox(10, new Label("Select language:"), langBox, startBtn);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);
        stage.setTitle("Shopping Cart");
        stage.show();

        startBtn.setOnAction(e -> {
            Locale locale = switch (langBox.getValue()) {
                case "Finnish" -> new Locale("fi");
                case "Swedish" -> new Locale("sv");
                default -> Locale.ENGLISH;
            };
            msg = ResourceBundle.getBundle("messages", locale);
            showItemCountWindow(stage);
        });
    }

    private void showItemCountWindow(Stage stage) {
        Label label = new Label(msg.getString("enter_items"));
        TextField tf = new TextField();
        Button next = new Button("OK");

        VBox root = new VBox(10, label, tf, next);
        root.setPadding(new Insets(20));
        stage.setScene(new Scene(root, 350, 150));

        next.setOnAction(e -> {
            int count = Integer.parseInt(tf.getText());
            showItemInputs(stage, count);
        });
    }

    private void showItemInputs(Stage stage, int count) {
        VBox fields = new VBox(10);
        TextField[] priceFields = new TextField[count];
        TextField[] quantityFields = new TextField[count];

        for (int i = 0; i < count; i++) {
            priceFields[i] = new TextField();
            quantityFields[i] = new TextField();

            HBox row = new HBox(10,
                    new Label(msg.getString("enter_price") + " #" + (i + 1)),
                    priceFields[i],
                    new Label(msg.getString("enter_quantity") + " #" + (i + 1)),
                    quantityFields[i]
            );
            fields.getChildren().add(row);
        }

        Button calc = new Button("Calculate");
        Label result = new Label();

        VBox root = new VBox(15, fields, calc, result);
        root.setPadding(new Insets(20));
        stage.setScene(new Scene(root, 600, 300));

        calc.setOnAction(e -> {
            double[] prices = new double[count];
            int[] quantities = new int[count];

            for (int i = 0; i < count; i++) {
                prices[i] = Double.parseDouble(priceFields[i].getText());
                quantities[i] = Integer.parseInt(quantityFields[i].getText());
            }

            ShoppingCart cart = new ShoppingCart();
            double total = cart.calculateTotal(prices, quantities);

            result.setText(msg.getString("total_cost") + ": " + total);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}