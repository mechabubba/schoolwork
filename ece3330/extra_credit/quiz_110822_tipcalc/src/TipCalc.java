import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class TipCalc extends Application {
    @FXML
    private Label tipSliderLabel;

    @FXML
    private Slider tipSlider;

    @FXML
    private TextField tipBillField;

    @FXML
    private TextField tipField;

    @FXML
    private TextField tipTotalField;

    @FXML
    private Label tipError;

    @FXML
    private void updateSliderLabel(MouseEvent event) {
        double value = tipSlider.getValue();
        tipSliderLabel.setText(String.format("%.2f%%", value));
    }

    @FXML
    private void calculateTip(ActionEvent event) {
        double bill;
        try {
            bill = Double.parseDouble(tipBillField.getText());
        } catch(Exception e) {
            // something went terribly wrong
            tipError.setText("Could not parse the provided dollar amount!");
            return;
        }

        tipError.setText("");
        double tip = tipSlider.getValue();

        double tippedAmount = (tip / 100) * bill;
        double tippedTotal = tippedAmount + bill;
        tipField.setText(String.format("$%.2f", tippedAmount));
        tipTotalField.setText(String.format("$%.2f", tippedTotal));
    }

    public static void main(String[] args) {
        launch(args); // boilerplate code
    }


    @Override
    public void start(Stage stage) throws IOException {
        URL myfxml = getClass().getResource("TipCalc.fxml");
        if(myfxml == null) {
            System.err.println("File not found.");
            return;
        }

        Parent root = FXMLLoader.load(myfxml);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
