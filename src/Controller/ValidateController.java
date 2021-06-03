package Controller;

import Model.ConnectDB;
import Model.Limpeza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Array;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private TableView<?> rastreabilidadeTable;

    @FXML
    private TableView<?> temperaturasTable;

    @FXML
    private Button validarBtn;

    private ObservableList<Limpeza> listLimpezas;

    private int index = -1;

    @FXML
    protected void initialize(){
        limpezasTable();
    }

    /**
     * Apresenta tabela com checkbox
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
     * Valida as rows selecionadas nas Limpezas
     * @param mouseEvent
     */
    public void validateSelected(MouseEvent mouseEvent) {
        boolean success = false, error = false;

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
        }else if (error){
            MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                    "Algo correu mal, Sem sucesso a validar.").showAndWait();
        }

    }
}
