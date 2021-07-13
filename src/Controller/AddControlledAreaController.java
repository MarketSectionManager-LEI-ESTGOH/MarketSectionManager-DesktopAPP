package Controller;

import Model.Componente;
import Model.ConnectDB;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddControlledAreaController {

    public Button addCABTN;
    @FXML
    private TextField addCANumberTF;
    @FXML
    private TextField addCADesignTF;
    @FXML
    private TableView<Componente> compsTbl;
    @FXML
    private TableColumn<Componente, Integer> compID;
    @FXML
    private TableColumn<Componente, String> compColumn;
    @FXML
    private TableColumn<Componente, CheckBox> chekColumn;
    private ObservableList<Componente> listComponentes;

    @FXML
    /**
     * Gera a tabela
     */
    protected void initialize() {
        componentesTable();
    }

    /**
     * Permite Introduzir uma Área Contrtolada
     */
    public void insertControlledArea() {
        try {
            int areaNumber = -1;
            if (!addCANumberTF.getText().isEmpty()) {
                areaNumber = Integer.parseInt(addCANumberTF.getText());
                if (!addCADesignTF.getText().isEmpty()) {
                    if (registerControlledArea(areaNumber, addCADesignTF.getText())) {
                        boolean hasSelectedComponents = false;
                        for (Componente comp : listComponentes) {
                            if (comp.getCheck().isSelected()) {
                                hasSelectedComponents = true;
                                break;
                            }
                        }
                        if (!hasSelectedComponents) {
                            MainScreenController.alerts(Alert.AlertType.INFORMATION, "Área Intorduzida com Sucesso",
                                    "A Área " + addCADesignTF.getText() + " com o Número Interno " + areaNumber + " foi inserida com sucesso (sem componentes)!").showAndWait();
                            Stage currentStage = (Stage) addCABTN.getScene().getWindow();
                            currentStage.close();
                        } else {
                            String componentDenomination = "";
                            int dbID = getControlledAreaDatabaseID(areaNumber);
                            if (dbID != -1) {
                                boolean success = false, error = false;
                                for (Componente comp : listComponentes) {
                                    if (comp.getCheck().isSelected()) {
                                        componentDenomination += comp.getNome() + ", ";
                                        try {
                                            String stmt = "INSERT INTO area_componentes (area_id, componentes_id) VALUES (?, ?)";
                                            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                                            ps.setInt(1, dbID);
                                            ps.setInt(2, comp.getId());
                                            if (ConnectDB.insertIntoTable(ps)) {
                                                success = true;
                                            } else {
                                                error = true;
                                            }
                                        } catch (Exception e) {
                                            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inseperado, por favor tente novamente!").showAndWait();
                                        }
                                    }
                                }

                                if (success) {
                                    MainScreenController.alerts(Alert.AlertType.INFORMATION, "Área Intorduzida com Sucesso",
                                            "A Área " + addCADesignTF.getText() + " com o Número Interno " + areaNumber + " e os\ncomponentes " + componentDenomination + " foi inserida com sucesso!").showAndWait();
                                    cleanControlledAreaFields();
                                    Stage currentStage = (Stage) addCABTN.getScene().getWindow();
                                    currentStage.close();
                                } else if (error) {
                                    MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                                            "Algo correu mal, Sem sucesso a validar.").showAndWait();
                                }


                            } else {
                                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inseperado, por favor tente novamente!").showAndWait();
                            }

                            //all done
                        }
                    } else {
                        MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inseperado, por favor tente novamente!").showAndWait();
                    }
                } else {
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "A Designação da Área Controlada é de Preenchimento Obrigatório!").showAndWait();
                }
            } else {
                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "O número da Área Controlada é de Preenchimento Obrigatório!").showAndWait();
            }
        } catch (Exception e) {
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Algo correu mal, por favor tente novamente! \n\n " + e).showAndWait();

        }
    }

    /**
     * Limpar os Campos de Introdução depois da inserção de uma Área Controlada
     */
    private void cleanControlledAreaFields() {
        addCANumberTF.setText("");
        addCADesignTF.setText("");
    }

    /**
     * Inserir Área Controlada na Base de Dados
     * @param aNumber Número Interno da Área Controlada
     * @param aDesign Designação da Área Controlada
     * @return True/False
     */
    private boolean registerControlledArea(int aNumber, String aDesign) {
        try {
            String stmt = "INSERT INTO area (numero, designacao) VALUES (?, ?)";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aNumber);
            ps.setString(2, aDesign);
            return ConnectDB.insertIntoTable(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Obter o ID de Base de Dados da Área Controlada cuja Número Interno é recebido por Parâmetro
     * @param aNumInterno Número Interno da Área Controlada
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
     * Apresenta tabela componetes com checkbox
     * https://www.youtube.com/watch?v=aE3XwpHOeG8&list=PL2EKpjm0bX4IWJ1ErhQZgrLPVgyqeP3L5&index=7
     */
    public void componentesTable() {
        compID.setCellValueFactory(new PropertyValueFactory<Componente, Integer>("id"));
        compColumn.setCellValueFactory(new PropertyValueFactory<Componente, String>("nome"));
        chekColumn.setCellValueFactory(new PropertyValueFactory<Componente, CheckBox>("check"));
        listComponentes = ConnectDB.getAllComponents();
        compsTbl.setItems(listComponentes);
    }
}
