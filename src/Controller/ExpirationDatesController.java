package Controller;

import Model.ConnectDB;
import Model.ExprirationDate;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleRole;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import  Model.ExprirationDate;
import javafx.stage.Stage;

import java.util.Date;
import java.util.Optional;

public class ExpirationDatesController {
    @FXML
    private Pane productsPane;
    @FXML
    private TableView<ExprirationDate> valTable;
    @FXML
    private TableColumn<ExprirationDate, String> nIntValCol;
    @FXML
    private TableColumn<ExprirationDate, String> eanValCol;
    @FXML
    private TableColumn<ExprirationDate, String> nomeValCol;
    @FXML
    private TableColumn<ExprirationDate, Date> valValCol;
    @FXML
    private TableColumn<ExprirationDate, String> markValCol;
    @FXML
    private TableColumn<ExprirationDate, Integer> offValCol;
    @FXML
    private Button RemoveValBtn;
    @FXML
    private TextField searchProductTextField;
    @FXML
    private Button EditValBtn;
    @FXML
    private Button addValBtn;
    @FXML
    private Button markdownBTN;
    @FXML
    private Button offsetBTN;
    private ObservableList<ExprirationDate> expirationDatesList;
    private int index;
    static DatePicker d = new DatePicker();

    @FXML
    protected void initialize(){
        expirationDatesTable();
    }

    public void getSelected(){
        index = valTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        RemoveValBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(" __ BTN REMOVER VALIDADE CLICKED! __");
                try{
                    ExprirationDate selected = expirationDatesList.get(index);

                    System.out.println(selected.getNumInterno() + " - " + selected.getNome());
                    Optional<ButtonType> result = MainScreenController.alerts(Alert.AlertType.CONFIRMATION, "Remover "+selected.getNumInterno(),
                            "Tem a certeza que quer remover o Registo  ("  +selected.getNumInterno()+")"
                                    +selected.getEan() + " - " + selected.getNome()+ "?").showAndWait();
                    if(!result.isPresent()){
                    }else if(result.get() == ButtonType.OK){
                        if(ExprirationDate.removeExpirationDateFromDB(selected)){
                            MainScreenController.alerts(Alert.AlertType.INFORMATION, "Removido com Sucesso "+selected.getNumInterno(),
                                    "Registo:  ("  +selected.getNumInterno()+")"
                                            +selected.getEan() + " - " + selected.getNome()+ " Removido com Sucesso!").showAndWait();
                        }else{
                            MainScreenController.alerts(Alert.AlertType.ERROR, "Falha ao remover", "Algo correu mal...").showAndWait();
                        }
                    }else if(result.get() == ButtonType.CANCEL){

                    }

                }catch (Exception e){
                    System.out.println(e);
                }

            }
        });
        offsetBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(" __ offset CLICKED! __");
                try {
                    ExprirationDate selected = expirationDatesList.get(index);
                    TextInputDialog offsetInputBox = new TextInputDialog();
                    offsetInputBox.setTitle("Definição de Offset");
                    offsetInputBox.setHeaderText("Qual o Novo Offset do Produto \n(" + selected.getNumInterno() + ") " + selected.getNome() + " com o EAN " + selected.getEan() + "?");
                    Stage stage = (Stage) offsetInputBox.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("/Images/logoicon.png"));
                    Optional<String> result = offsetInputBox.showAndWait();
                    if (!result.isPresent()) {
                    } else{
                        int finalOffset = 20;
                    try {
                        finalOffset = Integer.parseInt(offsetInputBox.getEditor().getText());
                        System.out.println("FinalOffset = " + finalOffset);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "O Offset não pode ser um valor alfabético, por favor tente novamente!").showAndWait();
                        finalOffset = 20;
                    }
                    if (ExprirationDate.updateOffsetInDB(selected, finalOffset)) {
                        MainScreenController.alerts(Alert.AlertType.INFORMATION, "SUCESSO", "O Offset do produto (" + selected.getNumInterno() + ") " + selected.getNome() + " foi atualizado com sucesso!").showAndWait();
                    } else {
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Aconteu um Erro", "Acoteceu um erro ao atualizar o Offset, po favor tente novamente!").showAndWait();
                    }
                }
                }catch (Exception e){
                    System.out.println(e);
                    MainScreenController.alerts(Alert.AlertType.ERROR,"Aconteu um Erro", "Acoteceu um erro ao atualizar o Offset, po favor tente novamente!").showAndWait();
                }

            }
        });
        markdownBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(" __ markdown CLICKED! __");
                try{
                    ExprirationDate selected = expirationDatesList.get(index);
                    System.out.println("Is markdowned: " + selected.getMardown());
                    if(selected.getMardown().equalsIgnoreCase("sim") || selected.getMardown().equalsIgnoreCase("sem informação")){
                        Optional<ButtonType> result = MainScreenController.alerts(Alert.AlertType.CONFIRMATION, "Mardown "+selected.getNumInterno(),
                                "Tem a certeza que quer Remover o Markdown do Produto  ("  +selected.getNumInterno()+")"
                                        +selected.getEan() + " - " + selected.getNome()+ "?").showAndWait();
                        if(!result.isPresent()){
                        }else if(result.get() == ButtonType.OK){
                            if(ExprirationDate.updateMarkdownInDB(selected,0)){
                                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Markdown Removido com Sucesso ",
                                        "Markdown do Produto: ("  +selected.getNumInterno()+")"
                                                +selected.getEan() + " - " + selected.getNome()+ " Removido com Sucesso!").showAndWait();
                            }else{
                                MainScreenController.alerts(Alert.AlertType.ERROR, "Falha ao remover Markdown", "Algo correu mal...").showAndWait();
                            }
                        }else if(result.get() == ButtonType.CANCEL){ }
                    }else if(selected.getMardown().equalsIgnoreCase("não")){
                        Optional<ButtonType> result = MainScreenController.alerts(Alert.AlertType.CONFIRMATION, "Mardown "+selected.getNumInterno(),
                                "Tem a certeza que quer Adicionar Markdown ao Produto  ("  +selected.getNumInterno()+")"
                                        +selected.getEan() + " - " + selected.getNome()+ "?").showAndWait();
                        if(!result.isPresent()){
                        }else if(result.get() == ButtonType.OK){
                            if(ExprirationDate.updateMarkdownInDB(selected,1)){
                                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Markdown Adicionado com Sucesso ",
                                        "Markdown ao Produto: ("  +selected.getNumInterno()+")"
                                                +selected.getEan() + " - " + selected.getNome()+ " Adicionado com Sucesso!").showAndWait();
                            }else{
                                MainScreenController.alerts(Alert.AlertType.ERROR, "Falha ao adicionar Markdown", "Algo correu mal...").showAndWait();
                            }
                        }else if(result.get() == ButtonType.CANCEL){ }
                    } else {
                        MainScreenController.alerts(Alert.AlertType.ERROR,"Aconteu um Erro", "Acoteceu um erro, por favor tente novamente!").showAndWait();
                    }
                }catch (Exception e){
                    System.out.println(e);
                    MainScreenController.alerts(Alert.AlertType.ERROR,"Aconteu um Erro", "Acoteceu um erro ao atualizar o Markdown, por favor tente novamente!").showAndWait();
                }

            }
        });
        EditValBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(" __ edit val CLICKED! __");
                try{
                    EditExpirationDateController.setThisED(expirationDatesList.get(index));
                    Stage EditStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/EditExpirationDate.fxml"));
                    EditStage.setScene(new Scene(root));
                    EditStage.getIcons().add(new Image("/Images/logoicon.png"));
                    EditStage.setTitle("Editar Registo de Validade de " + nomeValCol.getCellData(index));
                    EditStage.setResizable(false);
                    EditStage.centerOnScreen();
                    EditStage.show();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });
    }

    public void showAddExpirationDate(){
        try{
            EditExpirationDateController.setThisED(expirationDatesList.get(index));
            Stage EditStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/View/AddExpirationDate.fxml"));
            EditStage.setScene(new Scene(root));
            EditStage.getIcons().add(new Image("/Images/logoicon.png"));
            EditStage.setTitle("Adicionar Registo de Validade");
            EditStage.setResizable(false);
            EditStage.centerOnScreen();
            EditStage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void expirationDatesTable(){
        System.out.println("products Edit btn clicked!!");
        try{
            nIntValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, String>("numInterno"));
            eanValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, String>("ean"));
            nomeValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, String>("nome"));
            valValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, Date>("expirationDate"));
            markValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, String>("mardown"));
            offValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, Integer>("offset"));
        }catch (Exception w){
            System.out.println("------------------------ exception ----------------------"); w.printStackTrace();System.out.println("------------------------ exception ----------------------");
        }
        expirationDatesList = ConnectDB.getAllExpirationDates();
        FilteredList<ExprirationDate> filteredData = new FilteredList<>(expirationDatesList, b -> true);
        searchProductTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(ExprirationDate ->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(ExprirationDate.getNome().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(String.valueOf(ExprirationDate.getNumInterno()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(String.valueOf(ExprirationDate.getEan()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } if(String.valueOf(ExprirationDate.getExpirationDate()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }if(String.valueOf(ExprirationDate.getOffset()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else {
                    return false;
                }
            });
        });
        SortedList<ExprirationDate> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(valTable.comparatorProperty());
        valTable.setItems(sortedData);
    }
}
