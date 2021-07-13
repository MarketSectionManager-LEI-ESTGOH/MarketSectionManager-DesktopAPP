package Controller;

import Model.Area;
import Model.Componente;
import Model.ConnectDB;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditAreaController {
    @FXML
    private TextField editCANumberTF;
    @FXML
    private TextField editCADesignTF;
    @FXML
    private Button editCABTN;
    @FXML
    private TableView<Componente> compsTbl;
    @FXML
    private TableColumn<Componente, Integer> compID;
    @FXML
    private TableColumn<Componente, String> compColumn;
    @FXML
    private TableColumn<Componente, CheckBox> chekColumn;
    @FXML
    private TextArea editCACompsAtuaisTA;
    @FXML
    private Button removeCompBTN;
    @FXML
    private TableView<Componente> compAtuaisTable;

    @FXML
    private TableColumn<Componente, Integer> idCompAtuaisCol;

    @FXML
    private TableColumn<Componente, String> compAtuaisCol;

    @FXML
    private TableColumn<Componente, CheckBox> removerCol;

    @FXML
    private Button adcionarBtn;

    private static Area initialArea = null, thisArea = null;

    private ObservableList<Componente> listCompAtuais;
    private ObservableList<Componente> listCompDisp;

    /**
     * Definir o registo sobre o qual se está a "trabalhar"
     * @param aThisArea Objeto do tipo Area
     */
    protected static void setThisArea(Area aThisArea) {
        thisArea = aThisArea;
    }
    private int dbID = -1;

    @FXML
    /**
     * Definir propriedades de elementos gráficos e obter os componentes atuais de uma área controlada e obter a lista
     * de componentes ainda disponíveis para essa área
     */
    protected void initialize(){
        System.out.println("edit controlled area initialize");
        editCANumberTF.setEditable(false);
        editCANumberTF.setDisable(true);
        editCANumberTF.setText(String.valueOf(thisArea.getNumero()));
        editCADesignTF.setText(thisArea.getDesignacao());
        dbID = getControlledAreaDatabaseID(thisArea.getNumero());
        componentesAtuaisTable();
        componentesDispTable();
    }


    /**
     * Adicionar guardar alterações na Área Controlada
     * @param actionEvent
     */
    public void handleUpdateButtonAction(javafx.event.ActionEvent actionEvent) {
        String newDesign = "";
        newDesign = editCADesignTF.getText();

        if(newDesign.isEmpty()){
            MainScreenController.alerts(Alert.AlertType.ERROR,"ERRO", "Todos os campos são de preenchimento obrigatório!").showAndWait();
        }else{
            try {
                String stmt = "UPDATE area SET designacao = ? WHERE numero = ?";
                PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                ps.setString(1, newDesign);
                ps.setInt(2, thisArea.getNumero());
                if(ConnectDB.updateDB(ps)){
                    MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                            "A Area Controlada foi atualizada com sucesso.").showAndWait();
                    Stage stage = (Stage) editCABTN.getScene().getWindow();
                    stage.close();
                }else{
                    MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                            "Algo correu mal, Sem sucesso ao atualizar.").showAndWait();
                }
            }catch (Exception e){
                MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                        "Algo correu mal, Sem sucesso ao atualizar. "+e).showAndWait();
            }
        }
    }

    /**
     * Obter o Id de base de dados de uma área Controlada
     * @param aNumInterno Número Interno da área controlada
     * @return Int
     */
    private int getControlledAreaDatabaseID(int aNumInterno) {
        try {
            String stmt = "SELECT id FROM area WHERE numero = ?";
            PreparedStatement ps = null;
            ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aNumInterno);
            return ConnectDB.getControlledAreaID(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    /**
     * Obter os componentes atuais de uma área atual e preencher a respetivatabela
     */
    public void componentesAtuaisTable(){
        idCompAtuaisCol.setCellValueFactory(new PropertyValueFactory<Model.Componente, Integer>("id"));
        compAtuaisCol.setCellValueFactory(new PropertyValueFactory<Model.Componente, String>("nome"));
        removerCol.setCellValueFactory(new PropertyValueFactory<Model.Componente, CheckBox>("check"));

        listCompAtuais = ConnectDB.getCompFromArea(dbID);

        compAtuaisTable.setItems(listCompAtuais);
    }

    /**
     * Obter os componentes disponiveis para uma área e preencher a respetiva tabela
     */
    public void componentesDispTable(){
        compID.setCellValueFactory(new PropertyValueFactory<Model.Componente, Integer>("id"));
        compColumn.setCellValueFactory(new PropertyValueFactory<Model.Componente, String>("nome"));
        chekColumn.setCellValueFactory(new PropertyValueFactory<Model.Componente, CheckBox>("check"));

        listCompDisp = ConnectDB.getCompDispArea(dbID);

        compsTbl.setItems(listCompDisp);
    }

    /**
     * Remove as rows selecionadas na tabela de componentes
     * @param mouseEvent MouseEvent
     */
    public void removeFromArea(MouseEvent mouseEvent) {
        boolean success = false, error = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remover componentes selecionados?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            for(Componente l : listCompAtuais){
                if(l.getCheck().isSelected()){
                    try {
                        String stmt = "DELETE FROM area_componentes WHERE area_id = ? AND componentes_id = ?";

                        PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                        ps.setInt(1, dbID);
                        ps.setInt(2, l.getId());
                        if(ConnectDB.updateDB(ps)){
                            success = true;
                        }else{
                            error = true;
                        }
                    }catch (Exception e){
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                                "Algo correu mal, Sem sucesso a remover. "+e).showAndWait();
                    }
                }
            }

            if(success){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Removido com sucesso",
                        "Componentes removidos com sucesso.").showAndWait();
                componentesAtuaisTable();
                componentesDispTable();
            }else if (error){
                MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                        "Algo correu mal, Sem sucesso a remover.").showAndWait();
            }
        }else if(alert.getResult() == ButtonType.NO){
            componentesAtuaisTable();
            componentesDispTable();
        }
    }

    /**
     * Adiciona as rows selecionadas na tabela de componentes
     * @param mouseEvent MouseEvent
     */
    public void addToArea(MouseEvent mouseEvent) {
        boolean success = false, error = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adicionar componentes selecionados?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            for(Componente c : listCompDisp){
                if(c.getCheck().isSelected()){
                    try {
                        String stmt = "INSERT INTO area_componentes (area_id, componentes_id) VALUES (?, ?)";

                        PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                        ps.setInt(1, dbID);
                        ps.setInt(2, c.getId());
                        if(ConnectDB.updateDB(ps)){
                            success = true;
                        }else{
                            error = true;
                        }
                    }catch (Exception e){
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                                "Algo correu mal, Sem sucesso a adicionar. "+e).showAndWait();
                    }
                }
            }

            if(success){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Adicionado com sucesso",
                        "Componentes adicionados com sucesso.").showAndWait();
                componentesAtuaisTable();
                componentesDispTable();
            }else if (error){
                MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                        "Algo correu mal, Sem sucesso a adicionar.").showAndWait();
            }
        }else if(alert.getResult() == ButtonType.NO){
            componentesAtuaisTable();
            componentesDispTable();
        }
    }

}
