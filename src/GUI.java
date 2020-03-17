import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {
    private static final String stageTitle = "Sensor GUI";
    public static final String cssRes = "res/css/flat_dark_theme.css";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ChartView.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssRes);
        primaryStage.setTitle(stageTitle);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
