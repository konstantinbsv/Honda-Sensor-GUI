import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Controller implements Initializable {
    // constants
    static final int refreshDelay = 50;     // interface refresh delay in milliseconds

    // logger
    Logger logger = Logger.getLogger(getClass().getName());

    // lateral graphs
    public AreaChart<Number, Number> pressure1;
    public AreaChart<Number, Number> pressure2;
    public AreaChart<Number, Number> pressure3;
    public AreaChart<Number, Number> pressure4;
    public AreaChart<Number, Number> pressure5;
    public AreaChart<Number, Number> pressure6;
    // center graphs
    public AreaChart<Number, Number> pressureCenter;
    public AreaChart<Number, Number> proximity;
    public AreaChart<Number, Number> shearX;
    public AreaChart<Number, Number> shearY;

    // model object
    public   Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = Model.getInstance();

        /* bind area chart series in model to chart views */
        pressure1.getData().add(model.getPressure1Series());
        pressure2.getData().add(model.getPressure2Series());
        pressure3.getData().add(model.getPressure3Series());
        pressure4.getData().add(model.getPressure4Series());
        pressure5.getData().add(model.getPressure5Series());
        pressure6.getData().add(model.getPressure6Series());

        pressureCenter.getData().add(model.getPressureCenterSeries());
        proximity.getData().add(model.getProximitySeries());
        shearX.getData().add(model.getShearXSeries());
        shearY.getData().add(model.getShearYSeries());
        
        startUpdateDaemon();

    }

    /**
     * Creates and starts and daemon task which calls functions necessary to update UI
     */
    private void startUpdateDaemon() {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    updateModelData();
                    Platform.runLater(() -> updateUI());
                    Thread.sleep(refreshDelay);
                }
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Updates interface with data from MCU
     * This function is called in an infinite loop by the startUpdateDaemonTask
     */
    private void updateModelData() {

    }

    /**
     * Updates UI with data from model
     */
    private void updateUI() {
        model.updateAreaCharts();
    }
}
