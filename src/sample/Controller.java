package sample;

import com.grishberg.Parser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextArea taInput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onFormatClicked(ActionEvent event) {
        String input = taInput.getText();
        String output = Parser.format(input);
        taInput.setText(output);
        System.out.println("Вы нажали кнопку Войти!");
    }
}
