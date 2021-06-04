package Controller;

import Model.ConnectDB;
import Model.Limpeza;
import Model.Rastreabilidade;
import Model.Temperatura;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.sql.PreparedStatement;

public class ValidateController {

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Limpeza> limpezasTable;

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
    private TableView<Rastreabilidade> rastreabilidadeTable;

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
    private Button validarRastreabilidadeBtn;

    @FXML
    private TableView<Temperatura> temperaturasTable;

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
    private Button validarTempBtn;

    @FXML
    private Button validarBtn;

    private ObservableList<Limpeza> listLimpezas;
    private ObservableList<Rastreabilidade> listRastreabilidade;
    private ObservableList<Temperatura> listTemperatura;

    private int index = -1;

    @FXML
    protected void initialize(){
        limpezasTable();
        rastreabilidadeTable();
        temperaturasTable();
    }

    /**
     * Apresenta tabela rastreabilidade com checkbox
     * https://www.youtube.com/watch?v=aE3XwpHOeG8&list=PL2EKpjm0bX4IWJ1ErhQZgrLPVgyqeP3L5&index=7
     *
     */
    public void rastreabilidadeTable(){
        loteRastCol.setCellValueFactory(new PropertyValueFactory<Model.Rastreabilidade, Integer>("lote"));
        dataEntRastCol.setCellValueFactory(new PropertyValueFactory<Model.Rastreabilidade, Date>("dataEntrada"));
        origemRastCol.setCellValueFactory(new PropertyValueFactory<Model.Rastreabilidade, String>("origem"));
        produtoRastCol.setCellValueFactory(new PropertyValueFactory<Model.Rastreabilidade, String>("produto"));
        userRastCol.setCellValueFactory(new PropertyValueFactory<Model.Rastreabilidade, String>("utilizador"));
        fornRastCol.setCellValueFactory(new PropertyValueFactory<Model.Rastreabilidade, String>("fornecedor"));
        validateRastCol.setCellValueFactory(new PropertyValueFactory<Model.Rastreabilidade, CheckBox>("validate"));

        listRastreabilidade = ConnectDB.getAllRastToVal();

        rastreabilidadeTable.setItems(listRastreabilidade);
    }

    /**
     * Apresenta tabela limpezas com checkbox
     * https://www.youtube.com/watch?v=aE3XwpHOeG8&list=PL2EKpjm0bX4IWJ1ErhQZgrLPVgyqeP3L5&index=7
     *
     */
    public void limpezasTable(){
        areaLimpCol.setCellValueFactory(new PropertyValueFactory<Model.Limpeza, String>("areaName"));
        compLimpCol.setCellValueFactory(new PropertyValueFactory<Model.Limpeza, String>("compName"));
        dataLimpCol.setCellValueFactory(new PropertyValueFactory<Model.Limpeza, Date>("dataLimp"));
        utilLimpCol.setCellValueFactory(new PropertyValueFactory<Model.Limpeza, String>("userName"));
        nInternoLimp.setCellValueFactory(new PropertyValueFactory<Model.Limpeza, Integer>("userNumInterno"));
        validarLimpCol.setCellValueFactory(new PropertyValueFactory<Model.Limpeza, CheckBox>("validate"));

        listLimpezas = ConnectDB.getAllLimpToVal();

        limpezasTable.setItems(listLimpezas);

    }

    /**
     * Apresenta tabela temperaturas com checkbox
     * https://www.youtube.com/watch?v=aE3XwpHOeG8&list=PL2EKpjm0bX4IWJ1ErhQZgrLPVgyqeP3L5&index=7
     *
     */
    public void temperaturasTable(){
        areaTempCol.setCellValueFactory(new PropertyValueFactory<Model.Temperatura, String>("areaFrig"));
        tempTempCol.setCellValueFactory(new PropertyValueFactory<Model.Temperatura, Integer>("temperatura"));
        timestampTempCol.setCellValueFactory(new PropertyValueFactory<Model.Temperatura, Date>("dataHora"));
        userTempCol.setCellValueFactory(new PropertyValueFactory<Model.Temperatura, String>("utilizador"));
        validateTemCol.setCellValueFactory(new PropertyValueFactory<Model.Temperatura, CheckBox>("validate"));

        listTemperatura = ConnectDB.getAllTempToVal();

        temperaturasTable.setItems(listTemperatura);
    }

    /**
     * Valida as rows selecionadas nas Limpezas
     * @param mouseEvent
     */
    public void validateSelected(MouseEvent mouseEvent) {
        boolean success = false, error = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Validar limpezas selecionadas?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            for(Limpeza l : listLimpezas){
                if(l.getValidate().isSelected()){
                    try {
                        String stmt = "UPDATE limpeza SET assinatura = (SELECT id FROM user WHERE num_interno = ?), data_assinatura = ? WHERE id = ? ";

                        PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                        ps.setInt(1, Main.u.getUserID());
                        ps.setString(2, String.valueOf(java.time.LocalDate.now()));
                        ps.setInt(3, l.getId());
                        if(ConnectDB.updateDB(ps)){
                            success = true;
                        }else{
                            error = true;
                        }
                    }catch (Exception e){
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                                "Algo correu mal, Sem sucesso a validar. "+e).showAndWait();
                    }
                }
            }

            if(success){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                        "Registos validados com sucesso.").showAndWait();
                limpezasTable();
            }else if (error){
                MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                        "Algo correu mal, Sem sucesso a validar.").showAndWait();
            }
        }else if(alert.getResult() == ButtonType.NO){
            limpezasTable();
        }
    }

    /**
     * Valida as rows selecionadas nas Rastreabilidade
     * @param mouseEvent
     */
    public void validateSelectedRast(MouseEvent mouseEvent) {
        boolean success = false, error = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Validar rastreabilidades selecionadas?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            for(Rastreabilidade l : listRastreabilidade){
                if(l.getValidate().isSelected()){
                    try {
                        String stmt = "UPDATE rastreabilidade SET assinado_user = (SELECT id FROM user WHERE num_interno = ?) WHERE id = ? ";

                        PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                        ps.setInt(1, Main.u.getUserID());
                        ps.setInt(2, l.getId());
                        if(ConnectDB.updateDB(ps)){
                            success = true;
                        }else{
                            error = true;
                        }
                    }catch (Exception e){
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                                "Algo correu mal, Sem sucesso a validar. "+e).showAndWait();
                    }
                }
            }

            if(success){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                        "Registos validados com sucesso.").showAndWait();
                rastreabilidadeTable();
            }else if (error){
                MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                        "Algo correu mal, Sem sucesso a validar.").showAndWait();
            }
        }else if(alert.getResult() == ButtonType.NO){
            rastreabilidadeTable();
        }
    }

    /**
     * Valida as rows selecionadas nas Temperaturas
     * @param mouseEvent
     */
    public void validateSelectedTemp(MouseEvent mouseEvent) {
        boolean success = false, error = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Validar temperaturas selecionadas?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            for(Temperatura l : listTemperatura){
                if(l.getValidate().isSelected()){
                    try {
                        String stmt = "UPDATE temperatura SET assinado = (SELECT id FROM user WHERE num_interno = ?) WHERE id = ? ";

                        PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                        ps.setInt(1, Main.u.getUserID());
                        ps.setInt(2, l.getId());
                        if(ConnectDB.updateDB(ps)){
                            success = true;
                        }else{
                            error = true;
                        }
                    }catch (Exception e){
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                                "Algo correu mal, Sem sucesso a validar. "+e).showAndWait();
                    }
                }
            }

            if(success){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                        "Registos validados com sucesso.").showAndWait();
                temperaturasTable();
            }else if (error){
                MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                        "Algo correu mal, Sem sucesso a validar.").showAndWait();
            }
        }else if(alert.getResult() == ButtonType.NO){
            temperaturasTable();
        }
    }

}
