package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainController {

    @FXML private ComboBox<String> comboLanguage;
    @FXML private Button btnApplyLanguage;

    @FXML private Label labelChooseLanguage;
    @FXML private Label labelItems;
    @FXML private TextField tfItemCount;

    @FXML private Button btnCreateInputs;
    @FXML private VBox itemsContainer;
    @FXML private Button btnCalculate;
    @FXML private Label labelTotal;

    private ResourceBundle msg;

    @FXML
    public void initialize() {
        comboLanguage.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        comboLanguage.setValue("English");

        btnApplyLanguage.setOnAction(e -> applyLanguage());
        btnCreateInputs.setOnAction(e -> createItemFields());
        btnCalculate.setOnAction(e -> calculateTotal());

        applyLanguage();
    }

    private void applyLanguage() {
        Locale locale = switch (comboLanguage.getValue()) {
            case "Finnish" -> new Locale("fi");
            case "Swedish" -> new Locale("sv");
            case "Japanese" -> Locale.JAPANESE;
            case "Arabic" -> new Locale("ar");
            default -> Locale.ENGLISH;
        };

        msg = ResourceBundle.getBundle("messages", locale, new UTF8Control());


        labelChooseLanguage.setText(msg.getString("select_language"));
        labelItems.setText(msg.getString("enter_items"));
        btnCreateInputs.setText(msg.getString("create_fields"));
        btnCalculate.setText(msg.getString("calculate"));
    }

    private void createItemFields() {
        itemsContainer.getChildren().clear();
        btnCalculate.setDisable(false);

        int count = Integer.parseInt(tfItemCount.getText());

        for (int i = 0; i < count; i++) {
            TextField price = new TextField();
            TextField qty = new TextField();

            HBox row = new HBox(10,
                new Label(msg.getString("enter_price") + " #" + (i + 1)),
                price,
                new Label(msg.getString("enter_quantity") + " #" + (i + 1)),
                qty
            );

            itemsContainer.getChildren().add(row);
        }
    }

    private void calculateTotal() {
        int count = itemsContainer.getChildren().size();
        double[] prices = new double[count];
        int[] quantities = new int[count];

        for (int i = 0; i < count; i++) {
            HBox row = (HBox) itemsContainer.getChildren().get(i);
            TextField priceField = (TextField) row.getChildren().get(1);
            TextField qtyField = (TextField) row.getChildren().get(3);

            prices[i] = Double.parseDouble(priceField.getText());
            quantities[i] = Integer.parseInt(qtyField.getText());
        }

        ShoppingCart cart = new ShoppingCart();
        double total = cart.calculateTotal(prices, quantities);

        labelTotal.setText(msg.getString("total_cost") + ": " + total);
    }
}
