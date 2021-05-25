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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    public Button insertArcaInDB;
    public TextField refrigeratorDesign;
    public TextField refrigeratorNumber;
    public TextField refrigeratorManufacturer;
    public TextField refrigeratorMaxTemp;
    public TextField refrigeratorMinTemp;
    private static DateFormat timstampRefrigerator = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    private int index = -1;
    ObservableList<ArcaFrigorifica> listArcas;

    @FXML
    protected void initialize(){
        arcasTable();
    }

    public void arcasTable(){
        System.out.println("arcas Edit btn clicked!!");

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
                } else if(String.valueOf(ArcaFrigorifica.getNumero()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(ArcaFrigorifica.getTempMax()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(ArcaFrigorifica.getTempMin()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(ArcaFrigorifica.getFabricante().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(ArcaFrigorifica.getAddDate()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(ArcaFrigorifica.getLimpDate()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(ArcaFrigorifica.getUserLimp()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(ArcaFrigorifica.getUserName() != null){
                    if(ArcaFrigorifica.getUserName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    return false;
                } else {
                    return false;
                }
            });
        });

        SortedList<ArcaFrigorifica> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(arcasTable.comparatorProperty());

        arcasTable.setItems(sortedData);
    }

    public void showAddRefrigerator(){
        System.out.println("add refrigerator btn clicked!!");
        try{
            Stage AddRefrigerator = new Stage();
            Parent rootAddRefrigerator = FXMLLoader.load(getClass().getResource("/View/AddRefrigerator.fxml"));
            AddRefrigerator.setScene(new Scene(rootAddRefrigerator));
            AddRefrigerator.setTitle("Adicionar Arca Frigorifica");
            AddRefrigerator.setResizable(false);
            AddRefrigerator.centerOnScreen();
            AddRefrigerator.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Editar ou Remover Arcas
     * @param mouseEvent
     */
    public void getSelectedRefrigerator(javafx.scene.input.MouseEvent mouseEvent) {
        System.out.println("in getSelectedRefrigerator");
        index = arcasTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        EditArcaBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("edit refrigerator btn clicked!");
                try{
                    EditRefrigeratorController.setThisRefrigerator(listArcas.get(index));
                    Stage EditStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/EditRefrigerator.fxml"));
                    EditStage.setScene(new Scene(root));
                    EditStage.setTitle("Editar Arca  " + designArcaCol.getCellData(index));
                    EditStage.setResizable(false);
                    EditStage.centerOnScreen();
                    EditStage.show();
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        });
        RemoveArcaBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    ArcaFrigorifica selected = listArcas.get(index);

                    System.out.println(selected.getNumero());
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
                    System.out.println(e);
                }

            }
        });
    }

    public void insertRefrigerator(){
        System.out.println("insert refrigerator in db button clicked!");
        if((!refrigeratorDesign.getText().toString().isEmpty()) && (!refrigeratorNumber.getText().toString().isEmpty())
                && (!refrigeratorManufacturer.getText().toString().isEmpty())
                && (!refrigeratorMaxTemp.getText().toString().isEmpty())
                && (!refrigeratorMinTemp.getText().toString().isEmpty())){
            if(Float.valueOf(refrigeratorMaxTemp.getText()) < Float.valueOf(refrigeratorMinTemp.getText())){
                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "A temperatura máxima não pode ser mais baixa que a temperatura mínima!").showAndWait();
            }else {
                if (registerRefrigerator(Integer.parseInt(refrigeratorNumber.getText()), refrigeratorDesign.getText(), refrigeratorManufacturer.getText(),
                        timstampRefrigerator.format(new Date()), Float.parseFloat(refrigeratorMinTemp.getText()), Float.parseFloat(refrigeratorMaxTemp.getText()))) {
                    MainScreenController.alerts(Alert.AlertType.INFORMATION, "SUCESSO", "A Arca Frigorifica foi inserida com sucesso!").showAndWait();
                    clearRefrigeratorFields();
                } else {
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro ao inserir a Arca Frigorifica, por favor tente novamente!").showAndWait();
                }
            }
        }else{
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Todos os Campos são de Preenchimento Obrigatório.\n Prencha todos os campos e tente novamente!").showAndWait();
        }
    }

    private void clearRefrigeratorFields(){
        refrigeratorManufacturer.setText("");
        refrigeratorMinTemp.setText("");
        refrigeratorMaxTemp.setText("");
        refrigeratorNumber.setText("");
        refrigeratorDesign.setText("");
    }

    public static boolean registerRefrigerator(int aNumber, String aDesignation, String aManufacturer, String aInsertionDate, float aMinTemp, float aMaxTemp){
        try {
            String stmt = "INSERT INTO area_frigorifica (numero, designacao, fabricante, d_t_adicao, tem_min, tem_max) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aNumber);
            ps.setString(2,aDesignation);
            ps.setString(3, aManufacturer);
            ps.setString(4, aInsertionDate);
            ps.setFloat(5, aMinTemp);
            ps.setFloat(6, aMaxTemp);
            return ConnectDB.insertIntoTable(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
