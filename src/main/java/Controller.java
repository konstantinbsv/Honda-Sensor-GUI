import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Controller implements Initializable {
    // constants
    static final int REFRESH_DELAY = 50;      // interface refresh delay in milliseconds
    static final int VALUES_PER_UPDATE = 5;   // values to be read on evey model update call

    // logger
    Logger logger = Logger.getLogger(getClass().getName());

    // lateral graphs
    public AreaChart<Number, Number> pressure1;
    public AreaChart<Number, Number> pressure2;
    public AreaChart<Number, Number> pressure3;
    public AreaChart<Number, Number> pressure4;
    public AreaChart<Number, Number> pressure5;
    public AreaChart<Number, Number> pressure6;
    public AreaChart<Number, Number> pressure7;
    public AreaChart<Number, Number> pressure8;
    // center graphs
    public AreaChart<Number, Number> pressureCenter;
    public AreaChart<Number, Number> proximity;
    public AreaChart<Number, Number> shearX;
    public AreaChart<Number, Number> shearY;

    // serial port menu
    public MenuButton serialPortMenu;
    public List<MenuItem> portMenuItems;
    private Map<String, String> portMap;
    private boolean changingPorts;

    // capacitance units menu
    public MenuButton unitsMenu;
    public MenuItem picoItem;
    public MenuItem femtoItem;

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
        pressure7.getData().add(model.getPressure7Series());
        pressure8.getData().add(model.getPressure8Series());

        pressureCenter.getData().add(model.getPressureCenterSeries());
        proximity.getData().add(model.getProximitySeries());
        shearX.getData().add(model.getShearXSeries());
        shearY.getData().add(model.getShearYSeries());

        initializeSerialMenu();
        initializeUnitsMenu();

        startUpdateDaemon();

    }

    private void initializeUnitsMenu() {
        if (DataPatterns.useFemtoFarads) {
            unitsMenu.setText(DataPatterns.CapacitanceUnits.FEMTO);
        } else {
            unitsMenu.setText(DataPatterns.CapacitanceUnits.PICO);
        }

        femtoItem.setOnAction(event -> {
            DataPatterns.useFemtoFarads = true;
            unitsMenu.setText(DataPatterns.CapacitanceUnits.FEMTO);
            model.clearCharts();
            model.moveChartsToEnd();
        });

        picoItem.setOnAction(event -> {
            DataPatterns.useFemtoFarads = false;
            unitsMenu.setText(DataPatterns.CapacitanceUnits.PICO);
            model.clearCharts();
            model.moveChartsToEnd();
        });
    }

    private void initializeSerialMenu() {
        serialPortMenu.setText(Serial.getLastCOMPort());

        serialPortMenu.setOnShowing(event -> {
            serialPortMenu.getItems().clear();
            portMap = Serial.getAvailableSerialPorts();

            // populate menu items
            for (Map.Entry<String, String> mapEntry: portMap.entrySet()) {
                String portDescription = mapEntry.getValue();        // get port description
                MenuItem menuItem = new MenuItem(portDescription);   // create new menu item
                // set event listener
                menuItem.setOnAction(eventItem -> {
                    changingPorts = true;
                    boolean opened = Serial.initializeSerial(mapEntry.getKey());

                    if (opened) {
                        serialPortMenu.setText(mapEntry.getKey());
                    } else {
                        serialPortMenu.setText(mapEntry.getKey() + " unreachable");
                    }

                    model.clearCharts();
                    changingPorts = false;
                });

                serialPortMenu.getItems().add(menuItem);     // add menu item to menu
            }
        });
    }

    /**
     * Creates and starts and daemon task which calls functions necessary to update UI
     */
    private void startUpdateDaemon() {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    if (Serial.isPortOpened()) {
                        updateModelData();
                        Platform.runLater(() -> updateUI());
                    }

                    Thread.sleep(REFRESH_DELAY);
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
        // get all values from serial
        for (int i = 0; i < VALUES_PER_UPDATE; i++) {

            if(changingPorts) {
                return;
            }
            String line = Serial.getNextLine();                                 // get next line from serial
            int designator = DataPatterns.getDesignatorInt(line);               // get designator
            double capacitance = DataPatterns.getCapacitanceValueDouble(line);  // get capacitance value

            // update model according to designator value from serial
            switch (designator) {
                case DataPatterns.Designator.PRESSURE1:
                    model.setPressure1(capacitance);
                    break;
                case DataPatterns.Designator.PRESSURE2:
                    model.setPressure2(capacitance);
                    break;
                case DataPatterns.Designator.PRESSURE3:
                    model.setPressure3(capacitance);
                    break;
                case DataPatterns.Designator.PRESSURE4:
                    model.setPressure4(capacitance);
                    break;
                case DataPatterns.Designator.PRESSURE5:
                    model.setPressure5(capacitance);
                    break;
                case DataPatterns.Designator.PRESSURE6:
                    model.setPressure6(capacitance);
                    break;
                case DataPatterns.Designator.PRESSURE7:
                    model.setPressure7(capacitance);
                    break;
                case DataPatterns.Designator.PRESSURE8:
                    model.setPressure8(capacitance);
                    break;
                case DataPatterns.Designator.PRESSURE_CENTER:
                    model.setPressureCenter(capacitance);
                    break;
                case DataPatterns.Designator.PROXIMITY:
                    model.setProximity(capacitance);
                    break;
                case DataPatterns.Designator.SHEAR_X:
                    model.setShearX(capacitance);
                    break;
                case DataPatterns.Designator.SHEAR_Y:
                    model.setShearY(capacitance);
                    break;
            }
        }

    }

    /**
     * Updates UI with data from model
     */
    private void updateUI() {
        model.updateAreaCharts();
    }
}
