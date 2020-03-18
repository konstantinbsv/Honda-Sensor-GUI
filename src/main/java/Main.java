import javafx.application.Application;

public class Main {
    public static void main(String[] args) {

        Serial.initializeSerial();
        Application.launch(GUI.class, args);
    }
}
