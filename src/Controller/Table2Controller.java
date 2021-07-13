package Controller;

import Model.ConnectDB;
import Model.TableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;

public class Table2Controller {
    @FXML
    private TableView<TableData> table;
    @FXML
    private TableColumn<TableData, String> descCol;
    @FXML
    private TableColumn<TableData, Integer> dataCol;
    private ObservableList<TableData> registerAnalysisData = FXCollections.observableArrayList();

    @FXML
    /**
     * Define o tipo de dados de cada coluna, aplica os estilos necessários e gera a tabela
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
        if(registerAnalysisData != null){
            registerAnalysisData.add(new TableData("Registos de Rastreabilidade por Validar",getData(0)));
            registerAnalysisData.add(new TableData("Registos de Temperatura por Validar",getData(1)));
            registerAnalysisData.add(new TableData("Registos de Limpeza por Validar",getData(2)));
            registerAnalysisData.add(new TableData("Áreas Registadas",getData(3)));
            registerAnalysisData.add(new TableData("Número de Administradores Registados",getData(4)));
            registerAnalysisData.add(new TableData("Número de Funcionários Registados",getData(5)));
            table.setItems(registerAnalysisData);
        }
    }

    /**
     * Atualiza a tabela
     */
    public void refreshTable(){
        registerAnalysisData.clear();
        fillTable();
    }

    /**
     * Obtém da base de dados os dados necessários à construção da tabela
     * @param aTypeOfData Tipo de dados que são necessários, sendo:
     *                    0 - Validades a Terminar dentro de 5 dias,
     *                    1 - Validades a Terminar dentro de 10 dias,
     *                    2 - Validades a Terminar dentro de 15 dias,
     *                    3 - Validade a Terminar dentro de 20 dias,
     *                    4 - Markdowns Ativos e
     *                    5 - Markdowns Possíveis
     * @return Int com o resultado da Query desejada
     */
    private int getData(int aTypeOfData){
        int finalNumber = -1;
        PreparedStatement ps = null;
        final String stmt0 = "SELECT COUNT(id) FROM rastreabilidade WHERE assinado_user IS NULL;",
                     stmt1 = "SELECT COUNT(id) FROM temperatura WHERE assinado IS NULL;",
                     stmt2 = "SELECT COUNT(id) FROM limpeza WHERE assinatura IS NULL;",
                     stmt3 = "SELECT COUNT(id) FROM area;",
                     stmt4 = "SELECT COUNT(id) FROM user WHERE tipo = 1;",
                     stmt5 = "SELECT COUNT(id) FROM user WHERE tipo = 0;";
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
