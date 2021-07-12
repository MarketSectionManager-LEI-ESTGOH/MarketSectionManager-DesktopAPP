package Controller;

import Model.Area;
import Model.ConnectDB;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
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
            areasContTable.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getClickCount() == 2){
                        System.out.println("see details of section (double click on row)");
                        int dbID = getControlledAreaDatabaseID(areasContTable.getSelectionModel().getSelectedItem().getNumero());
                        String components = getControlledAreaComponents(dbID);
                        if(components.equalsIgnoreCase("Sem Componentes")){
                            MainScreenController.alerts(Alert.AlertType.INFORMATION,"Listagem de Componentes:","Esta Área Controlada não tem componentes associados!").showAndWait();
                        }else if(components.equals(null)){
                            MainScreenController.alerts(Alert.AlertType.INFORMATION,"Erro","Aconteceu um erro inesperado ao apresentar os componentes da área, por favor tente novamente!").showAndWait();
                        }else{
                            MainScreenController.alerts(Alert.AlertType.INFORMATION,"Listagem de Componentes","Esta Área tem a si associados os seguintes componentes: \n" + components).showAndWait();
                        }
                    }
                }
            });
            editAreaContBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        System.out.println("edit clicked!");
                        EditAreaController.setThisArea(listAreasCont.get(index));
                        Area a = listAreasCont.get(index);
                        System.out.println(a);
                        Stage EditStage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("/View/EditControlledArea.fxml"));
                        EditStage.setScene(new Scene(root));
                        EditStage.getIcons().add(new Image("/Images/logoicon.png"));
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
            AddControlledArea.getIcons().add(new Image("/Images/logoicon.png"));
            AddControlledArea.setTitle("Adicionar Área Controlada");
            AddControlledArea.setResizable(false);
            AddControlledArea.centerOnScreen();
            AddControlledArea.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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

    private String getControlledAreaComponents(int aBdId) {
        try {
            String stmt = "SELECT componentes.designacao FROM area_componentes LEFT JOIN componentes ON componentes_id = componentes.id WHERE area_id = ?";
            PreparedStatement ps = null;
            ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aBdId);
            return ConnectDB.getControlledAreaComponents(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @FXML
    private void exportPDFAction(ActionEvent event){
        final DirectoryChooser dirChooser = new DirectoryChooser();
        File file = dirChooser.showDialog(null);

        if(file != null){
            String path = file.getAbsolutePath();
            if(PDFExporter.genPdf("areasCont", path)){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Exportado", "Exportado com Sucesso.").showAndWait();
            }else{
                MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Algo correu mal ao exportar...").showAndWait();
            }
        }
    }

}
