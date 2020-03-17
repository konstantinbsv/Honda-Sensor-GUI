import javafx.scene.chart.XYChart;

public class Model {

    public static final int MAX_CHART_DATA_POINTS = 250;
    private static Model modelInstance = null;

    int dataPoint = 0;

    // lateral graphs
    private XYChart.Series<Number, Number> pressure1Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure2Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure3Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure4Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure5Series = new XYChart.Series<>();
    private XYChart.Series<Number, Number> pressure6Series = new XYChart.Series<>();

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

    public void setPressure1Series(XYChart.Series<Number, Number> pressure1Series) {
        this.pressure1Series = pressure1Series;
    }

    public XYChart.Series<Number, Number> getPressure2Series() {
        return pressure2Series;
    }

    public void setPressure2Series(XYChart.Series<Number, Number> pressure2Series) {
        this.pressure2Series = pressure2Series;
    }

    public XYChart.Series<Number, Number> getPressure3Series() {
        return pressure3Series;
    }

    public void setPressure3Series(XYChart.Series<Number, Number> pressure3Series) {
        this.pressure3Series = pressure3Series;
    }

    public XYChart.Series<Number, Number> getPressure4Series() {
        return pressure4Series;
    }

    public void setPressure4Series(XYChart.Series<Number, Number> pressure4Series) {
        this.pressure4Series = pressure4Series;
    }

    public XYChart.Series<Number, Number> getPressure5Series() {
        return pressure5Series;
    }

    public void setPressure5Series(XYChart.Series<Number, Number> pressure5Series) {
        this.pressure5Series = pressure5Series;
    }

    public XYChart.Series<Number, Number> getPressure6Series() {
        return pressure6Series;
    }

    public void setPressure6Series(XYChart.Series<Number, Number> pressure6Series) {
        this.pressure6Series = pressure6Series;
    }

    public XYChart.Series<Number, Number> getPressureCenterSeries() {
        return pressureCenterSeries;
    }

    public void setPressureCenterSeries(XYChart.Series<Number, Number> pressureCenterSeries) {
        this.pressureCenterSeries = pressureCenterSeries;
    }

    public XYChart.Series<Number, Number> getProximitySeries() {
        return proximitySeries;
    }

    public void setProximitySeries(XYChart.Series<Number, Number> proximitySeries) {
        this.proximitySeries = proximitySeries;
    }

    public XYChart.Series<Number, Number> getShearXSeries() {
        return shearXSeries;
    }

    public void setShearXSeries(XYChart.Series<Number, Number> shearXSeries) {
        this.shearXSeries = shearXSeries;
    }

    public XYChart.Series<Number, Number> getShearYSeries() {
        return shearYSeries;
    }

    public void setShearYSeries(XYChart.Series<Number, Number> shearYSeries) {
        this.shearYSeries = shearYSeries;
    }
}
