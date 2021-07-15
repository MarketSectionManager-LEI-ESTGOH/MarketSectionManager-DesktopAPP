package Controller;

import Model.ConnectDB;
import Model.Fornecedor;
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
import java.util.Optional;

public class FornecedoresController {

    @FXML
    private Pane fornecedoresPane;
    @FXML
    private TableView<Fornecedor> fornecedoresTable;
    @FXML
    private TableColumn<Fornecedor, String> identFornCol;
    @FXML
    private TableColumn<Fornecedor, String> nomeFornCol;
    @FXML
    private TableColumn<Fornecedor, Integer> contactoFornCol;
    @FXML
    private TableColumn<Fornecedor, String> emailFornCol;
    @FXML
    private TableColumn<Fornecedor, String> moradaFornCol;
    @FXML
    private Button RemoveForncedorBtn;
    @FXML
    private TextField searchFornecedorTextField;
    @FXML
    private Button EditFornecedorBtn;
    @FXML
    private Button addFornecedorBtn;

    ObservableList<Fornecedor> listFornecedores;
    private int index = -1;

    @FXML
    /**
     * Gerar tabela
     */
    protected void initialize(){
        fornecedorTable();
    }

    /**
     * Obtenção de dados para geração de tabela
     */
    public void fornecedorTable(){
        identFornCol.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("identificador"));
        nomeFornCol.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("nome"));
        contactoFornCol.setCellValueFactory(new PropertyValueFactory<Fornecedor, Integer>("contacto"));
        emailFornCol.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("email"));
        moradaFornCol.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("morada"));
        listFornecedores = ConnectDB.getAllFornecedores();
        FilteredList<Fornecedor> filteredData = new FilteredList<>(listFornecedores, b -> true);
        searchFornecedorTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(Fornecedor ->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Fornecedor.getIdentificador().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(String.valueOf(Fornecedor.getContacto()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(Fornecedor.getNome().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(Fornecedor.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(Fornecedor.getMorada().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Fornecedor> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(fornecedoresTable.comparatorProperty());

        fornecedoresTable.setItems(sortedData);
        fornecedoresPane.setVisible(true);
    }

    /**
     * Retorna fornecedor escolhido da tabela.
     * @param mouseEvent MouseEvent
     */
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = fornecedoresTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        EditFornecedorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    EditFornecedorController.setThisForn(listFornecedores.get(index));
                    Stage EditStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/EditFornecedor.fxml"));
                    EditStage.setScene(new Scene(root));
                    EditStage.getIcons().add(new Image("/Images/logoicon.png"));
                    EditStage.setTitle("Editar "+nomeFornCol.getCellData(index));
                    EditStage.setResizable(false);
                    EditStage.centerOnScreen();
                    EditStage.show();
                }catch (Exception e){
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
                }

            }
        });
        RemoveForncedorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Fornecedor selected = listFornecedores.get(index);

                    Optional<ButtonType> result = MainScreenController.alerts(Alert.AlertType.CONFIRMATION, "Remover "+selected.getNome(), "Tem a certeza que" +
                            " quer remover o fornecedor "+selected.getNome()+" com o identifcador"+selected.getIdentificador()
                            +"?").showAndWait();
                    if(!result.isPresent()){

                    }else if(result.get() == ButtonType.OK){
                        if(Fornecedor.removeFornFromDB(selected)){
                            MainScreenController.alerts(Alert.AlertType.INFORMATION, "Removido com sucesso", "Fornecedor "+selected.getNome()
                                    +" removido com sucesso.").showAndWait();
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

    /**
     * Mostra a Pane de Inserção de Fornecedores
     */
    public void registerForn(){
        try{
            Stage AddStage = new Stage();
            Parent rootAddStage = FXMLLoader.load(getClass().getResource("/View/AddFornecedor.fxml"));
            AddStage.setScene(new Scene(rootAddStage));
            AddStage.getIcons().add(new Image("/Images/logoicon.png"));
            AddStage.setTitle("Adicionar Fornecedor");
            AddStage.setResizable(false);
            AddStage.centerOnScreen();
            AddStage.show();
        }catch (Exception e){
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
        }
    }

    @FXML
    /**
     * Exportar tabela para PDF
     */
    private void exportPDFAction(ActionEvent event){
        final DirectoryChooser dirChooser = new DirectoryChooser();
        File file = dirChooser.showDialog(null);

        if(file != null){
            String path = file.getAbsolutePath();
            if(PDFExporter.genPdf("fornecedores", path)){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Exportado", "Exportado com Sucesso.").showAndWait();
            }else{
                MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Algo correu mal ao exportar...").showAndWait();
            }
        }
    }

}
