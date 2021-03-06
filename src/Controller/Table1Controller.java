package Controller;

import Model.ConnectDB;
import Model.TableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.PreparedStatement;

public class Table1Controller {
    @FXML
    private Pane graph1_pane;
    @FXML
    private Button refreshGraph1BTN;
    @FXML
    private TableView<TableData> table;
    @FXML
    private TableColumn<TableData, String> descCol;
    @FXML
    private TableColumn<TableData, Integer> dataCol;
    private ObservableList<TableData> productAnalysisData = FXCollections.observableArrayList();

    @FXML
    /**
     * Define os tipo de dados de cada coluna da tabela, aplica os estilos necessários e gera a tabela
     */
    public void initialize() {
        descCol.setCellValueFactory(new PropertyValueFactory<TableData, String>("dataDesignation"));
        dataCol.setCellValueFactory(new PropertyValueFactory<TableData, Integer>("data"));
        descCol.setStyle("-fx-background-color: #253437;-fx-text-fill: #ffffff;-fx-font-weight: bold;");
        dataCol.setStyle("-fx-background-color: #ffffff;-fx-text-fill: #000000;-fx-alignment: CENTER;");
        fillTable();
        table.setSelectionModel(null);
    }

    /**
     * Preenche a tabela
     */
    private void fillTable(){
        if(productAnalysisData != null){
            productAnalysisData.add(new TableData("Validades de Produtos a acabar em 5 Dias",getData(0)));
            productAnalysisData.add(new TableData("Validades de Produtos a acabar em 10 Dias",getData(1)));
            productAnalysisData.add(new TableData("Validades de Produtos a acabar em 15 Dias",getData(2)));
            productAnalysisData.add(new TableData("Validades de Produtos a acabar em 20 Dias",getData(3)));
            productAnalysisData.add(new TableData("Markdowns Ativos",getData(4)));
            productAnalysisData.add(new TableData("Markdowns Possiveis",getData(5)));
            table.setItems(productAnalysisData);
        }
    }

    /**
     * Atualiza a tabela
     */
    public void refreshTable(){
        productAnalysisData.clear();
        fillTable();
    }

    /**
     * Obtém da base de dados os dados necessários à construção da tabela
     * @param aTypeOfData Tipo de dados que são necessários, sendo:
     *                    0 - Validades a Terminar dentro de 5 dias,
     *                    1 - Validade a Terminar dentro de 10 dias,
     *                    2 - Validades a Terminar dentro de 15 dias,
     *                    3 - Validades a Terminar dentro de 20 dias,
     *                    4 - Markdowns Ativos e
     *                    5 - Markdowns Possíveis
     * @return Int com o Resultado da Query desejada
     */
    private int getData(int aTypeOfData){
        int finalNumber = -1;
        PreparedStatement ps = null;
        final String stmt0 = "SELECT COUNT(n_interno) FROM validade WHERE (validade.validade > CURDATE()) AND (validade.validade <= DATE_ADD(CURDATE(), INTERVAL 5 DAY));",
                     stmt1 = "SELECT COUNT(n_interno) FROM validade WHERE (validade.validade > CURDATE()) AND (validade.validade <= DATE_ADD(CURDATE(), INTERVAL 10 DAY));",
                     stmt2 = "SELECT COUNT(n_interno) FROM validade WHERE (validade.validade > CURDATE()) AND (validade.validade <= DATE_ADD(CURDATE(), INTERVAL 15 DAY));",
                     stmt3 = "SELECT COUNT(n_interno) FROM validade WHERE (validade.validade > CURDATE()) AND (validade.validade <= DATE_ADD(CURDATE(), INTERVAL 20 DAY));",
                     stmt4 = "SELECT COUNT(markdown) FROM validade WHERE (markdown = 1) AND (validade.validade >= CURDATE());",
                     stmt5 = "SELECT COUNT(markdown) FROM validade WHERE (markdown = 0) AND  (validade.validade <= DATE_ADD(CURDATE(), INTERVAL offset DAY)) AND (validade.validade >= CURDATE());";
        try{
            switch(aTypeOfData){
                case 0:{
                    ps = ConnectDB.getConn().prepareStatement(stmt0);
                    finalNumber = ConnectDB.getTableData(ps);
                    break;
                }
                case 1:{
                    ps = ConnectDB.getConn().prepareStatement(stmt1);
                    finalNumber = ConnectDB.getTableData(ps);
                    break;
                }
                case 2:{
                    ps = ConnectDB.getConn().prepareStatement(stmt2);
                    finalNumber = ConnectDB.getTableData(ps);
                    break;
                }
                case 3:{
                    ps = ConnectDB.getConn().prepareStatement(stmt3);
                    finalNumber = ConnectDB.getTableData(ps);
                    break;
                }
                case 4:{
                    ps = ConnectDB.getConn().prepareStatement(stmt4);
                    finalNumber = ConnectDB.getTableData(ps);
                    break;
                }
                case 5:{
                    ps = ConnectDB.getConn().prepareStatement(stmt5);
                    finalNumber = ConnectDB.getTableData(ps);
                    break;
                }
                default:{
                    finalNumber = -1;
                    break;
                }
            }

        }catch(Exception e){
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
        }
        return finalNumber;
    }
}
