package Controller;

import Model.Componente;
import Model.ConnectDB;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Model.ConnectDB.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Random;

public class Graph1Controller {
    @FXML
    private Pane graph1_pane = new Pane();
    @FXML
    private NumberAxis xAxis = new NumberAxis(1,31,1);
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private StackedAreaChart<Number, Number> sac = new StackedAreaChart<>(xAxis, yAxis);
    @FXML
    private ComboBox<String> refrigeratorsDesignationCB;
    @FXML
    private Button refreshGraph1BTN;
    private ObservableList<Float> tempsMin = FXCollections.observableArrayList();
    private ObservableList<Float> tempsMax = FXCollections.observableArrayList();
    private Date today = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static int randomStartGraph = -1;
    private static String graphTitle = "";
    private XYChart.Series<Number, Number> afternoonTemps = null;
    private XYChart.Series<Number, Number> morningTemps = null;

    @FXML
    public void initialize() {
        System.out.println("AFTER: ___ randomStartGraph: " + randomStartGraph + " __ graphTitle: " + graphTitle);
        getRefrigeratorsList();
        generateGraph(randomStartGraph, graphTitle);

    }

    private void getRefrigeratorsList(){
        ObservableList<String> refrigerators = ConnectDB.getRefrigeratorsIDandDesign();
        refrigeratorsDesignationCB.setItems(refrigerators);
        if((graphTitle.equals("")) && (randomStartGraph == -1)){
            int generatedListIndex = new Random().nextInt(refrigerators.size()+1);
            graphTitle = refrigerators.get(generatedListIndex);
            randomStartGraph = Integer.parseInt(refrigerators.get(generatedListIndex).split(" - ")[0]);
        }
    }

    private void generateGraph(int aID, String aGraphTitle){
        System.out.println("@genrateGraph: aID: " + aID + " _ aGraphTitle: " + aGraphTitle );
        populateTemps(aID);
        sac.setTitle("Variação da Temperatura (ºC) \nArca: " + aGraphTitle);
        int xMorningCounter = 1, xAfternoonCounter = 1;
        sac.getData().removeAll(morningTemps,afternoonTemps);
        XYChart.Series<Number, Number> morningTemps = new XYChart.Series<>();
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
        randomStartGraph = -1;
        graphTitle = "";
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

    public void refreshGraph(){
       String selectedRefrigerator [] = refrigeratorsDesignationCB.getSelectionModel().getSelectedItem().split(" - ");
        randomStartGraph = Integer.parseInt(selectedRefrigerator[0]);
        graphTitle = selectedRefrigerator[1];
        Pane newLoadedPane = null;
        try {
            newLoadedPane = FXMLLoader.load(getClass().getResource("/View/Graph1.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        graph1_pane.getChildren().clear();
        graph1_pane.getChildren().add(newLoadedPane);
        graph1_pane.setVisible(true);


    }

}
