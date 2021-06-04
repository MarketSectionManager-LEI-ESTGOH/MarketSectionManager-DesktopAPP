package Controller;

import Model.Area;
import Model.ConnectDB;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class ControlledAreaController {
    @FXML
    private Pane areasControladasPane;
    @FXML
    public TableView<Area> areasContTable;
    @FXML
    public TableColumn<Area, Integer> numeroAreaContCol;
    @FXML
    public TableColumn<Area, String> desginacaoAreaContCol;
    public Button removeAreaContBtn;
    public TextField searchAreasContTextField;
    public Button editAreaContBtn;
    public Button addAreasContBtn;


    ObservableList<Area> listAreasCont;
    private int index = -1;

    @FXML
    protected void initialize() {
        areasControladasTable();
    }

    public void areasControladasTable() {
        System.out.println("areas controladas btn clicked!!");

        numeroAreaContCol.setCellValueFactory(new PropertyValueFactory<Area, Integer>("numero"));
        desginacaoAreaContCol.setCellValueFactory(new PropertyValueFactory<Area, String>("designacao"));

        listAreasCont = ConnectDB.getAllAreasCont();
        FilteredList<Area> filteredData = new FilteredList<>(listAreasCont, b -> true);
        searchAreasContTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Area -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (Area.getDesignacao().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                if (String.valueOf(Area.getNumero()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Area> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(areasContTable.comparatorProperty());
        areasContTable.setItems(sortedData);
    }

    /**
     * Retorna utilizador escolhido da tabela.
     *
     * @param mouseEvent
     */
    public void getSelectedArea(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            index = areasContTable.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            editAreaContBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        EditAreaController.setThisArea(listAreasCont.get(index));
                        Stage EditStage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("/View/EditArea.fxml"));
                        EditStage.setScene(new Scene(root));
                        EditStage.setTitle("Editar " + listAreasCont.get(index).getNumero());
                        EditStage.setResizable(false);
                        EditStage.centerOnScreen();
                        EditStage.show();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            });
            removeAreaContBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        Area selected = listAreasCont.get(index);
                        Optional<ButtonType> result = MainScreenController.alerts(Alert.AlertType.CONFIRMATION, "Remover " + selected.getNumero(), "Tem a certeza que" +
                                " quer remover a Area Controlada " + selected.getNumero() + " com a designação " + selected.getDesignacao()
                                + "?").showAndWait();
                        if (!result.isPresent()) {
                        } else if (result.get() == ButtonType.OK) {

                            if (Area.removeAreaFromDB(selected)) {
                                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Removido com sucesso", "Àrea Controlada " + selected.getNumero()
                                        + " removido com sucesso.").showAndWait();
                            } else {
                                MainScreenController.alerts(Alert.AlertType.ERROR, "Falha ao remover", "Algo correu mal...").showAndWait();
                            }

                        } else if (result.get() == ButtonType.CANCEL) {

                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            });
        } catch (Exception e) {
            MainScreenController.alerts(Alert.AlertType.ERROR, "Falha ao remover", "Algo correu mal...").showAndWait();
        }
    }


    public void showAddControlledArea() {
        System.out.println("add controlled area btn clicked!!");
        try {
            Stage AddControlledArea = new Stage();
            Parent rootAddControlledArea = FXMLLoader.load(getClass().getResource("/View/AddControlledArea.fxml"));
            AddControlledArea.setScene(new Scene(rootAddControlledArea));
            AddControlledArea.setTitle("Adicionar Área Controlada");
            AddControlledArea.setResizable(false);
            AddControlledArea.centerOnScreen();
            AddControlledArea.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
