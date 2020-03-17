import javafx.scene.chart.XYChart;

public class Model {

    public static final int MAX_CHART_DATA_POINTS = 250;
    private static Model modelInstance = null;

    int dataPoint = 0;

    // lateral graphs
    private XYChart.Series<Number, Number> pressure1 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure2 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure3 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure4 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure5 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure6 = new XYChart.Series<>();

    // center graphs
    private XYChart.Series<Number, Number> pressureCenter = new XYChart.Series<>();
    private XYChart.Series<Number, Number> proximity = new XYChart.Series<>();
    private XYChart.Series<Number, Number> shearX = new XYChart.Series<>();
    private XYChart.Series<Number, Number> shearY = new XYChart.Series<>();

    private Model(){ }

    public static Model getInstance(){
        if (modelInstance == null) {
            modelInstance = new Model();
        }

        return modelInstance;
    }


}
