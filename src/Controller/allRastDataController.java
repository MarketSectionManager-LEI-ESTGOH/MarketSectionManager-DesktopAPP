package Controller;

import Model.ConnectDB;
import Model.Limpeza;
import Model.Rastreabilidade;
import Model.Temperatura;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class AllRastDataController {

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Limpeza> limpezasDataTable;

    @FXML
    private TableColumn<Limpeza, String> areaLimpCol;

    @FXML
    private TableColumn<Limpeza, String> compLimpCol;

    @FXML
    private TableColumn<Limpeza, Date> dataLimpCol;

    @FXML
    private TableColumn<Limpeza, String> utilLimpCol;

    @FXML
    private TableColumn<Limpeza, Integer> nInternoLimp;

    @FXML
    private TableColumn<Limpeza, CheckBox> validarLimpCol;

    @FXML
    private TableView<Rastreabilidade> rastreabilidadeDataTable;

    @FXML
    private TableColumn<Rastreabilidade, Integer> loteRastCol;

    @FXML
    private TableColumn<Rastreabilidade, Date> dataEntRastCol;

    @FXML
    private TableColumn<Rastreabilidade, String> origemRastCol;

    @FXML
    private TableColumn<Rastreabilidade, String> produtoRastCol;

    @FXML
    private TableColumn<Rastreabilidade, String> userRastCol;

    @FXML
    private TableColumn<Rastreabilidade, String> fornRastCol;

    @FXML
    private TableColumn<Rastreabilidade, CheckBox> validateRastCol;

    @FXML
    private TableView<Temperatura> temperaturasDataTable;

    @FXML
    private TableColumn<Temperatura, String> areaTempCol;

    @FXML
    private TableColumn<Temperatura, Integer> tempTempCol;

    @FXML
    private TableColumn<Temperatura, Date> timestampTempCol;

    @FXML
    private TableColumn<Temperatura, String> userTempCol;

    @FXML
    private TableColumn<Temperatura, CheckBox> validateTemCol;
    @FXML
    private TabPane tabPane;

    private ObservableList<Limpeza> listLimpezas;
    private ObservableList<Rastreabilidade> listRastreabilidade;
    private ObservableList<Temperatura> listTemperatura;
    private int index = -1;
    private static int selectReceivedTab = 0;

    public static void setSelectReceivedTab(int aReceivedTab){
        selectReceivedTab = aReceivedTab;
    }

    @FXML
    protected void initialize(){
        limpezasTable();
        rastreabilidadeTable();
        temperaturasTable();
        SelectionModel<Tab> tabSelectionModel = tabPane.getSelectionModel();
        tabSelectionModel.select(selectReceivedTab);

    }

    /**
     * Apresenta tabela rastreabilidade com checkbox
     * https://www.youtube.com/watch?v=aE3XwpHOeG8&list=PL2EKpjm0bX4IWJ1ErhQZgrLPVgyqeP3L5&index=7
     *
     */
    public void rastreabilidadeTable(){
        loteRastCol.setCellValueFactory(new PropertyValueFactory<Rastreabilidade, Integer>("lote"));
        dataEntRastCol.setCellValueFactory(new PropertyValueFactory<Rastreabilidade, Date>("dataEntrada"));
        origemRastCol.setCellValueFactory(new PropertyValueFactory<Rastreabilidade, String>("origem"));
        produtoRastCol.setCellValueFactory(new PropertyValueFactory<Rastreabilidade, String>("produto"));
        userRastCol.setCellValueFactory(new PropertyValueFactory<Rastreabilidade, String>("utilizador"));
        fornRastCol.setCellValueFactory(new PropertyValueFactory<Rastreabilidade, String>("fornecedor"));
        validateRastCol.setCellValueFactory(new PropertyValueFactory<Rastreabilidade, CheckBox>("validate"));

        listRastreabilidade = ConnectDB.getAllRast();

        rastreabilidadeDataTable.setItems(listRastreabilidade);
    }

    /**
     * Apresenta tabela limpezas com checkbox
     * https://www.youtube.com/watch?v=aE3XwpHOeG8&list=PL2EKpjm0bX4IWJ1ErhQZgrLPVgyqeP3L5&index=7
     *
     */
    public void limpezasTable(){
        areaLimpCol.setCellValueFactory(new PropertyValueFactory<Limpeza, String>("areaName"));
        compLimpCol.setCellValueFactory(new PropertyValueFactory<Limpeza, String>("compName"));
        dataLimpCol.setCellValueFactory(new PropertyValueFactory<Limpeza, Date>("dataLimp"));
        utilLimpCol.setCellValueFactory(new PropertyValueFactory<Limpeza, String>("userName"));
        nInternoLimp.setCellValueFactory(new PropertyValueFactory<Limpeza, Integer>("userNumInterno"));
        validarLimpCol.setCellValueFactory(new PropertyValueFactory<Limpeza, CheckBox>("validate"));

        listLimpezas = ConnectDB.getAllLimp();

        limpezasDataTable.setItems(listLimpezas);
    }

    /**
     * Apresenta tabela temperaturas com checkbox
     * https://www.youtube.com/watch?v=aE3XwpHOeG8&list=PL2EKpjm0bX4IWJ1ErhQZgrLPVgyqeP3L5&index=7
     *
     */
    public void temperaturasTable(){
        areaTempCol.setCellValueFactory(new PropertyValueFactory<Temperatura, String>("areaFrig"));
        tempTempCol.setCellValueFactory(new PropertyValueFactory<Temperatura, Integer>("temperatura"));
        timestampTempCol.setCellValueFactory(new PropertyValueFactory<Temperatura, Date>("dataHora"));
        userTempCol.setCellValueFactory(new PropertyValueFactory<Temperatura, String>("utilizador"));
        validateTemCol.setCellValueFactory(new PropertyValueFactory<Temperatura, CheckBox>("validate"));

        listTemperatura = ConnectDB.getAllTemp();

        temperaturasDataTable.setItems(listTemperatura);
    }

}
