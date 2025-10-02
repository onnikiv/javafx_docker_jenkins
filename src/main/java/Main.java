
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private final TextField inputField = new TextField();
    private final Label resultLabel = new Label();
    private double celsius;
    private double fahrenheit;
    private double kelvin;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        inputField.setPromptText("Enter number");

        Button cToFButton = new Button("Celsius to Fahrenheit");
        cToFButton.setOnAction(e -> convertCelsiustoFahrenheit());

        Button fToCButton = new Button("Fahrenheit to Celsius");
        fToCButton.setOnAction(e -> convertFahrenheittoCelsius());

        Button kToCButton = new Button("Kelvin to Celsius");
        kToCButton.setOnAction(e -> convertKelvintoCelsius());

        Button cToKButton = new Button("Celsius to Kelvin");
        cToKButton.setOnAction(e -> convertCelsiustoKelvin());

        Button saveButton = new Button("Save to DB");
        saveButton.setOnAction(e -> {
            try {
                Database.saveTemperature(celsius, fahrenheit, resultLabel);
            } catch (Exception ex) {
                resultLabel.setText("Error");
            }
        });

        VBox root = new VBox(10,
                inputField,
                cToFButton, fToCButton, kToCButton, cToKButton,
                resultLabel, saveButton
        );

        Scene scene = new Scene(root, 350, 300);

        stage.setTitle("Temperature Converter");
        stage.setScene(scene);
        stage.show();
    }

    private void convertCelsiustoFahrenheit() {
        try {
            celsius = Double.parseDouble(inputField.getText());
            fahrenheit = (celsius * 9 / 5) + 32;
            resultLabel.setText(String.format("%.2f °C = %.2f °F", celsius, fahrenheit));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid");
        }
    }

    private void convertFahrenheittoCelsius() {
        try {
            fahrenheit = Double.parseDouble(inputField.getText());
            celsius = (fahrenheit - 32) * 5 / 9;
            resultLabel.setText(String.format("%.2f °F = %.2f °C", fahrenheit, celsius));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid");
        }
    }

    private void convertKelvintoCelsius() {
        try {
            kelvin = Double.parseDouble(inputField.getText());
            celsius = kelvin - 273.15;
            resultLabel.setText(String.format("%.2f K = %.2f °C", kelvin, celsius));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid");
        }
    }

    private void convertCelsiustoKelvin() {
        try {
            celsius = Double.parseDouble(inputField.getText());
            kelvin = celsius + 273.15;
            resultLabel.setText(String.format("%.2f °C = %.2f K", celsius, kelvin));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid");
        }
    }
}
