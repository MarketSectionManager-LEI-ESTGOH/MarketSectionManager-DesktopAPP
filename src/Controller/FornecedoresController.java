package Controller;

import Model.ConnectDB;
import Model.Fornecedor;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

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


    public void fornecedorTable(){
        System.out.println("fornecedores btn clicked!!");

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
                } else if(String.valueOf(Fornecedor.getContacto()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(Fornecedor.getNome().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(Fornecedor.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(Fornecedor.getMorada().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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

}
