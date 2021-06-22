package Controller;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.util.Locale;

public class Graph2Controller {
    @FXML
    private Pane graph2_pane;

    @FXML
    private ComboBox<?> refrigeratorsDesignationCB;

    @FXML
    private Button refreshGraph2BTN;

    @FXML
    private NumberAxis xAxis = new NumberAxis(1,15,1);

    @FXML
    private NumberAxis yAxis = new NumberAxis();

    @FXML
    private ScatterChart<Number, Number> scatter = new ScatterChart<Number,Number>(xAxis,yAxis);

    public void initialize() {
        xAxis.setLabel("Dias Anteriores");
        yAxis.setLabel("Temperatura");
        scatter.setTitle("Dispersão de Temperatura (ºC) - Últimos 15 Dias\nÁrea Frigorífica: ");
        generateGraph();
    }

    private void generateGraph(){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Equities");
        series1.getData().add(new XYChart.Data(4.2, 193.2));
        series1.getData().add(new XYChart.Data(2.8, 33.6));
        series1.getData().add(new XYChart.Data(6.2, 24.8));
        series1.getData().add(new XYChart.Data(1, 14));
        series1.getData().add(new XYChart.Data(1.2, 26.4));
        series1.getData().add(new XYChart.Data(4.4, 114.4));
        series1.getData().add(new XYChart.Data(8.5, 323));
        series1.getData().add(new XYChart.Data(6.9, 289.8));
        series1.getData().add(new XYChart.Data(9.9, 287.1));
        series1.getData().add(new XYChart.Data(0.9, -9));
        series1.getData().add(new XYChart.Data(3.2, 150.8));
        series1.getData().add(new XYChart.Data(4.8, 20.8));
        series1.getData().add(new XYChart.Data(7.3, -42.3));
        series1.getData().add(new XYChart.Data(1.8, 81.4));
        series1.getData().add(new XYChart.Data(7.3, 110.3));
        series1.getData().add(new XYChart.Data(2.7, 41.2));
        scatter.getData().addAll(series1);
        scatter.setPrefSize(450,300);
    }




}
