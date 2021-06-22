package Controller;

import Model.Componente;
import Model.ConnectDB;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Model.ConnectDB.*;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Random;

public class Graph1Controller {
    @FXML
    private Pane graph1_pane;
    @FXML
    private NumberAxis xAxis = new NumberAxis(1,31,1);
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private StackedAreaChart<Number, Number> sac = new StackedAreaChart<>(xAxis, yAxis);
    @FXML
    private ComboBox<String> refrigeratorsDesignationCB;
    private ObservableList<Float> tempsMin = FXCollections.observableArrayList();
    private ObservableList<Float> tempsMax = FXCollections.observableArrayList();
    private Date today = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int randomStartGraph = -1;
    private String graphTitle = "";

    @FXML
    public void initialize() {
        getRefrigeratorsList();
        generateGraph(randomStartGraph, graphTitle);

    }

    private void getRefrigeratorsList(){
        ObservableList<String> refrigerators = ConnectDB.getRefrigeratorsIDandDesign();
        refrigeratorsDesignationCB.setItems(refrigerators);
        int generatedListIndex = new Random().nextInt(refrigerators.size()+1);
        graphTitle = refrigerators.get(generatedListIndex);
        randomStartGraph = Integer.parseInt(refrigerators.get(generatedListIndex).split(" - ")[0]);
    }

    private void generateGraph(int aID, String aGraphTitle){
        populateTemps(aID);
        sac.setTitle("Variação da Temperatura (ºC) \nArca: " + aGraphTitle);
        XYChart.Series<Number, Number> morningTemps = new XYChart.Series<>();
        int xMorningCounter = 1, xAfternoonCounter = 1;
        morningTemps.setName("Mínimo");
        for(Float tempMinValue : tempsMin) {
            morningTemps.getData().add(new XYChart.Data(xMorningCounter, tempMinValue));
            xMorningCounter++;
        }
        XYChart.Series<Number, Number> afternoonTemps = new XYChart.Series<>();
        afternoonTemps.setName("Máximo");
        for(Float tempMaxValue : tempsMax) {
            afternoonTemps.getData().add(new XYChart.Data(xAfternoonCounter, tempMaxValue));
            xAfternoonCounter++;
        }
        sac.getData().addAll(morningTemps,afternoonTemps);
        sac.setPrefSize(450,300);
    }

    private void populateTemps(int aID){
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        PreparedStatement ps = null;
        for(int i = 0; i < 15; i++){
            cal.add(Calendar.DAY_OF_MONTH,-1);
            try{
                String stmtMin = "SELECT MIN(temperatura) FROM temperatura where area_frigorifica_id = ? AND data_hora LIKE '%"+dateFormat.format(cal.getTime())+"%';";
                ps = ConnectDB.getConn().prepareStatement(stmtMin);
                ps.setInt(1, aID);
                tempsMin.add(ConnectDB.getTempsGrapgh1(ps));
                String stmtMax = "SELECT MAX(temperatura) FROM temperatura where area_frigorifica_id = ? AND data_hora LIKE '%"+dateFormat.format(cal.getTime())+"%';";
                ps = ConnectDB.getConn().prepareStatement(stmtMax);
                ps.setInt(1, aID);
                tempsMax.add(ConnectDB.getTempsGrapgh1(ps));
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

}
