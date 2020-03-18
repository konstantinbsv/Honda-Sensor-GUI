import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GUI extends Application {
    private static final String stageTitle = "Sensor GUI";
    public static final String cssRes = "res/css/flat_dark_theme.css";
    public static final String iconRes = "res/img/icon.png";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ChartView.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssRes);
        primaryStage.setTitle(stageTitle);
        primaryStage.getIcons().add(
                new Image(
                        getClass().getResourceAsStream(iconRes),
                        100, 100, true, true)); // add icon
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
