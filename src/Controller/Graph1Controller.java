package Controller;

import Model.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Graph1Controller {
    @FXML
    private Pane graph1_pane = new Pane();
    @FXML
    private NumberAxis xAxis = new NumberAxis(1,10,1);
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
    /**
     * Define os Labels dos Eixos dos Gráficos, obtém a lista das áreas frigoríficas e gera o gráfico
     */
    public void initialize() {
        xAxis.setLabel("Dias Anteriores");
        yAxis.setLabel("Temperatura");
        getRefrigeratorsList();
        generateGraph(randomStartGraph, graphTitle);
    }

    /**
     * Obtém a Lista das Áreas Frigoríficas registadas na base de dados
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
     * Gera o Gráfico
     * @param aID ID da Área Frigorífica a que se refere o gráfico
     * @param aGraphTitle Título do Gráfico
     */
    private void generateGraph(int aID, String aGraphTitle){
        populateTemps(aID);
        sac.setTitle("Temperatura (ºC) - Últimos 10 Dias\nÁrea Frigorífica: " + aGraphTitle);
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

    /**
     * Obtém as temperaturas de uma determinada área frigorífica, necessárias para a construção do gráfico
     * @param aID ID da área frigorífica
     */
    private void populateTemps(int aID){
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        PreparedStatement ps = null;
        for(int i = 0; i < 10; i++){
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
                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
            }
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
            newLoadedPane = FXMLLoader.load(getClass().getResource("/View/Graph1.fxml"));
        } catch (IOException e) {
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
        }
        graph1_pane.getChildren().clear();
        graph1_pane.getChildren().add(newLoadedPane);
        graph1_pane.setVisible(true);
    }


}
