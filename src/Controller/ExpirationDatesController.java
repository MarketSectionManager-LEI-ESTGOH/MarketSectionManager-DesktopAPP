package Controller;

import Model.ConnectDB;
import Model.ExprirationDate;
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
import  Model.ExprirationDate;

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

    @FXML
    protected void initialize(){
        expirationDatesTable();
    }

    public void getSelected(){}

    public void expirationDatesTable(){
        System.out.println("products Edit btn clicked!!");
        try{
            nIntValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, String>("numInterno"));
            eanValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, String>("ean"));
            nomeValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, String>("nome"));
            valValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, Date>("expirationDate"));
            markValCol.setCellValueFactory(new PropertyValueFactory<ExprirationDate, String>("mardown"));
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
