import javafx.scene.chart.XYChart;

public class Model {

    public static final int MAX_CHART_DATA_POINTS = 100;
    private static Model modelInstance = null;

    int dataPoint = 0;

    // data points for current values
    // private double[] pressuresLateral = new double[6];
    // private double[] centerData = new double[4];    // pressure, proximity, shearX, shearY
    private double pressure1, pressure2, pressure3, pressure4, pressure5, pressure6, pressure7, pressure8;
    private double pressureCenter, proximity, shearX, shearY;

    // lateral graphs
    private XYChart.Series<Number, Number> pressure1Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure2Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure3Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure4Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure5Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure6Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure7Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure8Series = new XYChart.Series<>();

    // center graphs
    private XYChart.Series<Number, Number> pressureCenterSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> proximitySeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> shearXSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> shearYSeries = new XYChart.Series<>();

    private Model(){ }

    public static Model getInstance(){
        if (modelInstance == null) {
            modelInstance = new Model();
        }

        return modelInstance;
    }

    public XYChart.Series<Number, Number> getPressure1Series() {
        return pressure1Series;
    }

    public XYChart.Series<Number, Number> getPressure2Series() {
        return pressure2Series;
    }

    public XYChart.Series<Number, Number> getPressure3Series() {
        return pressure3Series;
    }

    public XYChart.Series<Number, Number> getPressure4Series() {
        return pressure4Series;
    }

    public XYChart.Series<Number, Number> getPressure5Series() {
        return pressure5Series;
    }

    public XYChart.Series<Number, Number> getPressure6Series() {
        return pressure6Series;
    }

    public XYChart.Series<Number, Number> getPressure7Series() {
        return pressure7Series;
    }

    public XYChart.Series<Number, Number> getPressure8Series() {
        return pressure8Series;
    }

    public XYChart.Series<Number, Number> getPressureCenterSeries() {
        return pressureCenterSeries;
    }

    public XYChart.Series<Number, Number> getProximitySeries() {
        return proximitySeries;
    }

    public XYChart.Series<Number, Number> getShearXSeries() {
        return shearXSeries;
    }

    public XYChart.Series<Number, Number> getShearYSeries() {
        return shearYSeries;
    }

    public void setPressure1(double pressure1) {
        this.pressure1 = pressure1;
    }

    public void setPressure2(double pressure2) {
        this.pressure2 = pressure2;
    }

    public void setPressure3(double pressure3) {
        this.pressure3 = pressure3;
    }

    public void setPressure4(double pressure4) {
        this.pressure4 = pressure4;
    }

    public void setPressure5(double pressure5) {
        this.pressure5 = pressure5;
    }

    public void setPressure6(double pressure6) {
        this.pressure6 = pressure6;
    }

    public void setPressure7(double pressure7) {
        this.pressure7 = pressure7;
    }

    public void setPressure8(double pressure8) {
        this.pressure8 = pressure8;
    }

    public void setPressureCenter(double pressureCenter) {
        this.pressureCenter = pressureCenter;
    }

    public void setProximity(double proximity) {
        this.proximity = proximity;
    }

    public void setShearX(double shearX) {
        this.shearX = shearX;
    }

    public void setShearY(double shearY) {
        this.shearY = shearY;
    }

    public void updateAreaCharts() {
        pressure1Series.getData().add(new XYChart.Data<>(dataPoint, pressure1));
        pressure2Series.getData().add(new XYChart.Data<>(dataPoint, pressure2));
        pressure3Series.getData().add(new XYChart.Data<>(dataPoint, pressure3));
        pressure4Series.getData().add(new XYChart.Data<>(dataPoint, pressure4));
        pressure5Series.getData().add(new XYChart.Data<>(dataPoint, pressure5));
        pressure6Series.getData().add(new XYChart.Data<>(dataPoint, pressure6));
        pressure7Series.getData().add(new XYChart.Data<>(dataPoint, pressure7));
        pressure8Series.getData().add(new XYChart.Data<>(dataPoint, pressure8));

        pressureCenterSeries.getData().add(new XYChart.Data<>(dataPoint, pressureCenter));
        proximitySeries.getData().add(new XYChart.Data<>(dataPoint, proximity));
        shearXSeries.getData().add(new XYChart.Data<>(dataPoint, shearX));
        shearYSeries.getData().add(new XYChart.Data<>(dataPoint, shearY));

        dataPoint++;
        if (dataPoint > MAX_CHART_DATA_POINTS) {
            clearCharts();
        }
    }

    public void clearCharts() {
        dataPoint = 0;

        pressure1Series.getData().clear();
        pressure2Series.getData().clear();
        pressure3Series.getData().clear();
        pressure4Series.getData().clear();
        pressure5Series.getData().clear();
        pressure6Series.getData().clear();
        pressure7Series.getData().clear();
        pressure8Series.getData().clear();

        pressureCenterSeries.getData().clear();
        proximitySeries.getData().clear();
        shearXSeries.getData().clear();
        shearYSeries.getData().clear();
    }

    public void moveChartsToEnd() {
        dataPoint = MAX_CHART_DATA_POINTS - 1;
    }
}
