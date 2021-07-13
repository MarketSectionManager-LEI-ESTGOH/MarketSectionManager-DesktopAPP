package Controller;


import Model.ConnectDB;
import Model.Product;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

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
    private TableColumn<Model.Product, String> eanProductCol;
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
    /**
     * Gera a tabela
     */
    protected void initialize(){
        productsTable();
    }


    /**
     * Mostra a Pane de inserção de novos produtos
     */
    public void showAddProduct(){
        System.out.println("add prod btn clicked!!");
        try{
            Stage AddProduct = new Stage();
            Parent rootAddProdut = FXMLLoader.load(getClass().getResource("/View/AddProducts.fxml"));
            AddProduct.setScene(new Scene(rootAddProdut));
            AddProduct.getIcons().add(new Image("/Images/logoicon.png"));
            AddProduct.setTitle("Adicionar Produto");
            AddProduct.setResizable(false);
            AddProduct.centerOnScreen();
            AddProduct.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }


    /**
     * Retorna produto escolhido da tabela.
     * @param mouseEvent MouseEvent
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
                    EditProductController.setThisProduct(listProducts.get(index));
                    Stage EditStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/EditProduct.fxml"));
                    EditStage.setScene(new Scene(root));
                    EditStage.getIcons().add(new Image("/Images/logoicon.png"));
                    EditStage.setTitle("Editar " + productNameCol.getCellData(index));
                    EditStage.setResizable(false);
                    EditStage.centerOnScreen();
                    EditStage.show();
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        });
        RemoveProductBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Product selected = listProducts.get(index);


                        System.out.println(selected.getNum_int() + " - " + selected.getName());
                        Optional<ButtonType> result = MainScreenController.alerts(Alert.AlertType.CONFIRMATION, "Remover "+selected.getNum_int(),
                                                                                     "Tem a certeza que quer remover o Produto  ("  +selected.getNum_int()+")"
                                                                                    +selected.getEan() + " - " + selected.getName()+ "?").showAndWait();
                        if(!result.isPresent()){
                        }else if(result.get() == ButtonType.OK){
                            if(Product.removeProductFromDB(selected)){
                                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Removido com Sucesso "+selected.getNum_int(),
                                        "Produto  ("  +selected.getNum_int()+")"
                                                +selected.getEan() + " - " + selected.getName()+ " Removido com Sucesso!").showAndWait();
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

    /**
     * Obtem a informação e gera a tabela
     */
    public void productsTable(){
        System.out.println("products Edit btn clicked!!");
        try{
            numIntProfuctsCol.setCellValueFactory(new PropertyValueFactory<Model.Product, Integer>("num_int"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<Model.Product, String>("name"));
            freshProductCol.setCellValueFactory(new PropertyValueFactory<Model.Product, String>("freshString"));
            priceProductCol.setCellValueFactory(new PropertyValueFactory<Model.Product, Float>("price"));
            eanProductCol.setCellValueFactory(new PropertyValueFactory<Model.Product, String>("ean"));
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
                }  if(String.valueOf(Product.getNum_int()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(String.valueOf(Product.getPrice()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(Product.getEan() != null){
                    if(String.valueOf(Product.getEan()).indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                }  if(Product.getBrand() != null){
                    if(Product.getBrand().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                } if(Product.getFreshString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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

    @FXML
    /**
     * Exporta a tabela para PDF
     */
    private void exportPDFAction(ActionEvent event){
        final DirectoryChooser dirChooser = new DirectoryChooser();
        File file = dirChooser.showDialog(null);

        if(file != null){
            String path = file.getAbsolutePath();
            if(PDFExporter.genPdf("produtos", path)){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Exportado", "Exportado com Sucesso.").showAndWait();
            }else{
                MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Algo correu mal ao exportar...").showAndWait();
            }
        }
    }

}
