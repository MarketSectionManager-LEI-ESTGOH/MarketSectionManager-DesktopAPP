package Controller;

import Model.ConnectDB;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Graph2Controller {
    @FXML
    private Pane graph2_pane;
    @FXML
    private ComboBox<String> refrigeratorsDesignationCB;
    @FXML
    private Button refreshGraph2BTN;
    @FXML
    private NumberAxis xAxis = new NumberAxis(1,10,1);
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private ScatterChart<Number, Number> scatter = new ScatterChart<Number,Number>(xAxis,yAxis);
    private static String graphTitle = "";
    private static int randomStartGraph = -1;
    private XYChart.Series tempDisperssion = new XYChart.Series();
    private ArrayList<ArrayList<Float>> graphData = new ArrayList<>();
    private Date today = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Dá Nome aos eixos do gráfico, obtem as áreas frigoríficas e gera o gráfico
     */
    public void initialize() {
        xAxis.setLabel("Dias Anteriores");
        yAxis.setLabel("Temperatura");
        getRefrigeratorsList();
        generateGraph(randomStartGraph, graphTitle);

    }

    /**
     * Obtém as áreas frigoríficas registadas na base de dados
     */
    private void getRefrigeratorsList(){
        ObservableList<String> refrigerators = ConnectDB.getRefrigeratorsIDandDesign();
        refrigeratorsDesignationCB.setItems(refrigerators);
        if((graphTitle.equals("")) && (randomStartGraph == -1)){
            int generatedListIndex = new Random().nextInt(refrigerators.size());
            graphTitle = refrigerators.get(generatedListIndex);
            randomStartGraph = Integer.parseInt(refrigerators.get(generatedListIndex).split(" - ")[0]);
        }
    }

    /**
     * Gera o gráfico
     * @param aID ID da área frigorífica
     * @param aTitle Título do Gráfico
     */
    private void generateGraph(int aID, String aTitle){
        tempDisperssion.setName("Temperatura (ºC)");
        populateTemps(aID);
        scatter.setTitle("Dispersão de Temperatura (ºC) - Últimos 10 Dias\nÁrea Frigorífica: " + graphTitle);
        if(graphData != null ){
            for (int i = 0; i < graphData.size(); i++) {
                int arraySize = graphData.get(i).size();
                for (int j = 0; j < arraySize; j++) {
                    tempDisperssion.getData().add(new XYChart.Data((i+1), graphData.get(i).get(j)));
                }
            }
        }
        scatter.getData().addAll(tempDisperssion);
        scatter.setPrefSize(450,300);
        randomStartGraph = -1;
        graphTitle = "";
    }

    /**
     * Obtém as temperatura de uma determinada área frigorífica, necessárias para a construção do gráfico
     * @param aID
     */
    private void populateTemps (int aID){
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        for(int i = 0; i < 10; i++){
            cal.add(Calendar.DAY_OF_MONTH,-1);
            graphData.add(ConnectDB.getDispersion(aID, dateFormat.format(cal.getTime())));
        }
    }

    /**
     * Atualiza o gráfico
     */
    public void refreshGraph(){
        String selectedRefrigerator [] = refrigeratorsDesignationCB.getSelectionModel().getSelectedItem().split(" - ");
        randomStartGraph = Integer.parseInt(selectedRefrigerator[0]);
        graphTitle = selectedRefrigerator[0] + " - " +selectedRefrigerator[1];
        Pane newLoadedPane = null;
        try {
            newLoadedPane = FXMLLoader.load(getClass().getResource("/View/Graph2.fxml"));
        } catch (IOException e) {
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
        }
        graph2_pane.getChildren().clear();
        graph2_pane.getChildren().add(newLoadedPane);
        graph2_pane.setVisible(true);
    }




}
