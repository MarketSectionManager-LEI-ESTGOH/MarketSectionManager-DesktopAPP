package Controller;


import Model.ConnectDB;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.math.BigDecimal;

public class ProdutosController {
    @FXML
    private TableView<Model.Product> productTable;
    @FXML
    private TableColumn<Model.Product, Integer> numIntProfuctsCol;
    @FXML
    private TableColumn<Model.Product, String> productNameCol;
    @FXML
    private TableColumn<Model.Product, String> freshProductCol;
    @FXML
    private TableColumn<Model.Product, Float> priceProductCol;
    @FXML
    private TableColumn<Model.Product, BigDecimal> eanProductCol;
    @FXML
    private TableColumn<Model.Product, String> brandProductCol;
    @FXML
    private Button RemoveProductBtn;
    @FXML
    private TextField searchProductTextField;
    @FXML
    private Button EditProductBtn;
    @FXML
    private Button addProductBtn;
    ObservableList<Product> listProducts;
    private int index = -1;


    @FXML
    protected void initialize(){
        productsTable();
    }

    /**
     * Retorna produto escolhido da tabela.
     * @param mouseEvent
     */
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = productTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        EditProductBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    /*EditUserController.setThisUser(listProducts.get(index));
                    Stage EditStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/EditUser.fxml"));
                    EditStage.setScene(new Scene(root));
                    EditStage.setTitle("Editar "+nome_tb_users.getCellData(index));
                    EditStage.setResizable(false);
                    EditStage.centerOnScreen();
                    EditStage.show();*/
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        });
        RemoveProductBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    /*User selected = listProducts.get(index);
                    if(selected.getUserID() == Main.u.getUserID()){
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Falha ao remover", "Não pode remover a conta que está" +
                                " a utilizar.").showAndWait();
                    }else{
                        System.out.println(selected.getUserID());
                        Optional<ButtonType> result = MainScreenController.alerts(Alert.AlertType.CONFIRMATION, "Remover "+selected.getUsername(), "Tem a certeza que" +
                                " quer remover o utilizador "+selected.getUsername()+" com o número interno "+selected.getUserID()
                                +"?").showAndWait();
                        if(!result.isPresent()){

                        }else if(result.get() == ButtonType.OK){
                            if(User.removeUserFromDB(selected)){
                                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Removido com sucesso", "Utilizador "+selected.getUsername()
                                        +" removido com sucesso.").showAndWait();
                            }else{
                                MainScreenController.alerts(Alert.AlertType.ERROR, "Falha ao remover", "Algo correu mal...").showAndWait();
                            }
                        }else if(result.get() == ButtonType.CANCEL){

                        }
                    }*/
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        });
    }


    public void productsTable(){
        System.out.println("products Edit btn clicked!!");
        try{
            numIntProfuctsCol.setCellValueFactory(new PropertyValueFactory<Model.Product, Integer>("num_int"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<Model.Product, String>("name"));
            freshProductCol.setCellValueFactory(new PropertyValueFactory<Model.Product, String>("freshString"));
            priceProductCol.setCellValueFactory(new PropertyValueFactory<Model.Product, Float>("price"));
            eanProductCol.setCellValueFactory(new PropertyValueFactory<Model.Product, BigDecimal>("ean"));
            brandProductCol.setCellValueFactory(new PropertyValueFactory<Model.Product, String>("brand"));
        }catch (Exception w){
            System.out.println("------------------------ exception ----------------------"); w.printStackTrace();System.out.println("------------------------ exception ----------------------");
        }

        listProducts = ConnectDB.getAllProducts();
        FilteredList<Product> filteredData = new FilteredList<>(listProducts, b -> true);
        searchProductTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(Product ->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Product.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(Product.getNum_int()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(Product.getPrice()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(Product.getEan()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(Product.getBrand().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(Product.getFreshString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Product> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(productTable.comparatorProperty());

        productTable.setItems(sortedData);
    }

}
