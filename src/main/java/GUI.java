import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GUI extends Application {
    private static final String STAGE_TITLE = "Sensor GUI";
    public static final String CSS = "css/flat_dark_theme.css";
    public static final String ICON = "img/icon.png";
    public static final String FXML = "ChartView.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(FXML));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(CSS);
        primaryStage.setTitle(STAGE_TITLE);
        primaryStage.getIcons().add(
                new Image(
                        getClass().getResourceAsStream(ICON),
                        100, 100, true, true)); // add icon
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
