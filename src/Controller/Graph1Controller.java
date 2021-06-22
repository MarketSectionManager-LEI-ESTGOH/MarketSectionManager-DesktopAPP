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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Model.ConnectDB.*;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Graph1Controller {
    @FXML
    private Pane graph1_pane;
    @FXML
    private NumberAxis xAxis = new NumberAxis(1,31,1);
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private StackedAreaChart<Number, Number> sac = new StackedAreaChart<>(xAxis, yAxis);
    private ObservableList<Float> tempsMin = FXCollections.observableArrayList();
    private ObservableList<Float> tempsMax = FXCollections.observableArrayList();
    private Date today = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    public void initialize() {
        populateTemps(444);
        printList();
        sac.setTitle("Variação da Temperatura (ºC) \nArca 444");
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

    private void printList(){
        System.out.println("MINS");
        for(Float str : tempsMin) {
            System.out.println("-> Min: " + str);
        }
        System.out.println("MAXS");
        for(Float str : tempsMax) {
            System.out.println("-> Max: " + str);
        }
    }

}
