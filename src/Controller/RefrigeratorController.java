package Controller;

import Model.ArcaFrigorifica;
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
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class RefrigeratorController {
    public Pane arcasPane;
    @FXML
    public TableView<ArcaFrigorifica> arcasTable;
    @FXML
    public TableColumn<ArcaFrigorifica, Integer> numArcaCol;
    @FXML
    public TableColumn<ArcaFrigorifica, String> designArcaCol;
    @FXML
    public TableColumn<ArcaFrigorifica, String> fabricanteArcaCol;
    @FXML
    public TableColumn<ArcaFrigorifica, Date> dataAdicArcaCol;
    @FXML
    public TableColumn<ArcaFrigorifica, Float> tempMinArcaCol;
    @FXML
    public TableColumn<ArcaFrigorifica, Float> tempMaxArcaCol;
    @FXML
    public TableColumn<ArcaFrigorifica, Date> ultimaLimpArcaCol;
    @FXML
    public TableColumn<ArcaFrigorifica, BigDecimal> idFuncArcaCol;
    @FXML
    public TableColumn<ArcaFrigorifica, String> nomeFuncArcaCol;
    public Button RemoveArcaBtn;
    public TextField searchArcaTextField;
    public Button EditArcaBtn;
    public Button addArcaBtn;

    private int index = -1;
    ObservableList<ArcaFrigorifica> listArcas;

    @FXML
    /**
     * Gera a tabela
     */
    protected void initialize(){
        arcasTable();
    }

    /**
     * Obtem a informação e gera a tabela;
     */
    public void arcasTable(){
        numArcaCol.setCellValueFactory(new PropertyValueFactory<ArcaFrigorifica, Integer>("numero"));
        designArcaCol.setCellValueFactory(new PropertyValueFactory<ArcaFrigorifica, String>("designacao"));
        fabricanteArcaCol.setCellValueFactory(new PropertyValueFactory<ArcaFrigorifica, String>("fabricante"));
        dataAdicArcaCol.setCellValueFactory(new PropertyValueFactory<ArcaFrigorifica, Date>("addDate"));
        tempMinArcaCol.setCellValueFactory(new PropertyValueFactory<ArcaFrigorifica, Float>("tempMin"));
        tempMaxArcaCol.setCellValueFactory(new PropertyValueFactory<ArcaFrigorifica, Float>("tempMax"));
        ultimaLimpArcaCol.setCellValueFactory(new PropertyValueFactory<ArcaFrigorifica, Date>("limpDate"));
        idFuncArcaCol.setCellValueFactory(new PropertyValueFactory<ArcaFrigorifica, BigDecimal>("userLimp"));
        nomeFuncArcaCol.setCellValueFactory(new PropertyValueFactory<ArcaFrigorifica, String>("userName"));

        listArcas = ConnectDB.getAllArcas();
        FilteredList<ArcaFrigorifica> filteredData = new FilteredList<>(listArcas, b -> true);
        searchArcaTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(ArcaFrigorifica ->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(ArcaFrigorifica.getDesignacao().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(String.valueOf(ArcaFrigorifica.getNumero()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(String.valueOf(ArcaFrigorifica.getTempMax()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(String.valueOf(ArcaFrigorifica.getTempMin()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(ArcaFrigorifica.getFabricante().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } if(String.valueOf(ArcaFrigorifica.getAddDate()) != null){
                    if(String.valueOf(ArcaFrigorifica.getAddDate()).indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                }  if(String.valueOf(ArcaFrigorifica.getLimpDate()) != null){
                    if(String.valueOf(ArcaFrigorifica.getLimpDate()).indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                }  if(String.valueOf(ArcaFrigorifica.getUserLimp()) != null){
                    if(String.valueOf(ArcaFrigorifica.getUserLimp()).indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                }  if(ArcaFrigorifica.getUserName() != null){
                    if(ArcaFrigorifica.getUserName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                } else {
                    return false;
                }
                return false;
            });
        });

        SortedList<ArcaFrigorifica> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(arcasTable.comparatorProperty());

        arcasTable.setItems(sortedData);
    }

    /**
     * Mostra a pane de inserção de novas áreas frigoríficas
     */
    public void showAddRefrigerator(){
        try{
            Stage AddRefrigerator = new Stage();
            Parent rootAddRefrigerator = FXMLLoader.load(getClass().getResource("/View/AddRefrigerator.fxml"));
            AddRefrigerator.setScene(new Scene(rootAddRefrigerator));
            AddRefrigerator.getIcons().add(new Image("/Images/logoicon.png"));
            AddRefrigerator.setTitle("Adicionar Arca Frigorifica");
            AddRefrigerator.setResizable(false);
            AddRefrigerator.centerOnScreen();
            AddRefrigerator.show();
        }catch (Exception e){
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
        }
    }

    /**
     * Editar ou Remover Arcas
     * @param mouseEvent MouseEvent
     */
    public void getSelectedRefrigerator(javafx.scene.input.MouseEvent mouseEvent) {
        index = arcasTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        EditArcaBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    EditRefrigeratorController.setThisRefrigerator(listArcas.get(index));
                    Stage EditStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/EditRefrigerator.fxml"));
                    EditStage.setScene(new Scene(root));
                    EditStage.getIcons().add(new Image("/Images/logoicon.png"));
                    EditStage.setTitle("Editar Arca  " + designArcaCol.getCellData(index));
                    EditStage.setResizable(false);
                    EditStage.centerOnScreen();
                    EditStage.show();
                }catch (Exception e){
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
                }

            }
        });
        RemoveArcaBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    ArcaFrigorifica selected = listArcas.get(index);
                    Optional<ButtonType> result = MainScreenController.alerts(Alert.AlertType.CONFIRMATION, "Remover "+selected.getDesignacao(), "Tem a certeza que" +
                            " quer remover a Arca Frigorifica "+selected.getDesignacao()+" com o número interno "+selected.getNumero()
                            +"?").showAndWait();
                    if(!result.isPresent()){
                    }else if(result.get() == ButtonType.OK){
                        if(ArcaFrigorifica.removeRefrigeratorFromDB(selected)){
                            MainScreenController.alerts(Alert.AlertType.INFORMATION, "Removido com sucesso", "Arca Frigorifica "+selected.getDesignacao()
                                    +" removida com sucesso.").showAndWait();
                        }else{
                            MainScreenController.alerts(Alert.AlertType.ERROR, "Falha ao remover", "Algo correu mal...").showAndWait();
                        }
                    }else if(result.get() == ButtonType.CANCEL){

                    }

                }catch (Exception e){
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
                }

            }
        });
    }

    @FXML
    /**
     * Exportar Tabela para PDF
     */
    private void exportPDFAction(ActionEvent event){
        final DirectoryChooser dirChooser = new DirectoryChooser();
        File file = dirChooser.showDialog(null);

        if(file != null){
            String path = file.getAbsolutePath();
            if(PDFExporter.genPdf("arcasFrig", path)){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Exportado", "Exportado com Sucesso.").showAndWait();
            }else{
                MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Algo correu mal ao exportar...").showAndWait();
            }
        }
    }

}
