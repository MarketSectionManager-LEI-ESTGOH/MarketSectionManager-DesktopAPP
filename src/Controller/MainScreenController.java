package Controller;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class MainScreenController{

    public Button editProfileBtn = new Button();
   // public ImageView logoutBtn = new ImageView();
    private Stage currentStage = null;
    public Button registerBTN = new Button();
    public Button editBTN = new Button();
    public Pane registerPane = new Pane();
    public Button addUserBtn = new Button();
    public Button collapseBtn = new Button();
    public ComboBox userTypeCombo = new ComboBox();
    public TextField userNameTF = new TextField();
    public TextField userNumberTF = new TextField();
    public TextField userEmailTF = new TextField();
    public PasswordField userPassPF = new PasswordField();
    public PasswordField userPassConfPF = new PasswordField();
    public Pane allUsersPane = new Pane();
    public Button collapseBtn1 = new Button();
    @FXML
    private Pane receivedPane;

    protected static User selectedUser = null;
    public Pane productsPane;
    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Product, Integer> numIntProfuctsCol;
    @FXML
    public TableColumn<Product, String> productNameCol;
    @FXML
    public TableColumn<Product, String> freshProductCol;
    @FXML
    public TableColumn<Product, Float> priceProductCol;
    @FXML
    public TableColumn<Product, BigDecimal> eanProductCol;
    @FXML
    public TableColumn<Product, String> brandProductCol;
    public Button RemoveProductBtn;
    public TextField searchProductTextField;
    public Button EditProductBtn;
    public Button addProductBtn;

    @FXML
    private Pane fornecedoresPane;
    @FXML
    public TableView<Fornecedor> fornecedoresTable;
    @FXML
    public TableColumn<Fornecedor, String> identFornCol;
    @FXML
    public TableColumn<Fornecedor, String> nomeFornCol;
    @FXML
    public TableColumn<Fornecedor, Integer> contactoFornCol;
    @FXML
    public TableColumn<Fornecedor, String> emailFornCol;
    @FXML
    public TableColumn<Fornecedor, String> moradaFornCol;
    public Button RemoveForncedorBtn;
    public TextField searchFornecedorTextField;
    public Button EditFornecedorBtn;
    public Button addFornecedorBtn;
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
    public Button addCAComponentsBTN;
    public Button addCABTN;
    @FXML
    private TextField addCANumberTF;
    @FXML
    private TextField addCADesignTF;
    ObservableList<Product> listProducts;

    ObservableList<Fornecedor> listFornecedores;
    ObservableList<Area> listAreasCont;
    private int index = -1;



    /**
     * Referencia apresentação de dados: 20/04/2021
     * https://www.youtube.com/watch?v=tw_NXq08NUE
     *
     * Referencia filtro de pesquisa: 26/04/2021
     * https://www.youtube.com/watch?v=FeTrcNBVWtg
     *
     */


    public void start() throws Exception{
        Stage MainScreen = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        MainScreen.setTitle("Market Section Manager");
        MainScreen.setScene(new Scene(root, 1200, 602));
        //MainScreen.initStyle(StageStyle.UNDECORATED);
        MainScreen.setResizable(false);
        MainScreen.centerOnScreen();
        MainScreen.show();
    }


    public void editProfile(){
        System.out.println("edit profile btn clicked");

    }

    public void logout() throws Exception{
        currentStage = (Stage) editProfileBtn.getScene().getWindow();
        currentStage.close();
        Main.u = null;
        new Main().start(new Stage());

    }





    public void productsTable(){
        collapse();
        System.out.println("products Edit btn clicked!!");

        numIntProfuctsCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("num_int"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        freshProductCol.setCellValueFactory(new PropertyValueFactory<Product, String>("freshString"));
        priceProductCol.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
        eanProductCol.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("ean"));
        brandProductCol.setCellValueFactory(new PropertyValueFactory<Product, String>("brand"));


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
        productsPane.setVisible(true);
    }



    public void fornecedorTable(){
        collapse();
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

    public void areasControladasTable(){
        collapse();
        System.out.println("areas controladas btn clicked!!");

        numeroAreaContCol.setCellValueFactory(new PropertyValueFactory<Area, Integer>("numero"));
        desginacaoAreaContCol.setCellValueFactory(new PropertyValueFactory<Area, String>("designacao"));

        listAreasCont = ConnectDB.getAllAreasCont();
        FilteredList<Area> filteredData = new FilteredList<>(listAreasCont, b -> true);
        searchAreasContTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(Area ->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Area.getDesignacao().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(Area.getNumero()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Area> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(areasContTable.comparatorProperty());
        areasContTable.setItems(sortedData);
        areasControladasPane.setVisible(true);
    }

    public void addUser(){
        System.out.println("add user btn clicked!!");
        ConnectDB.loadProperties();
        if(userTypeCombo.getSelectionModel().getSelectedIndex() != -1){
            if(userPassPF.getText().equals(userPassConfPF.getText())){
                if(User.checkEmail(userEmailTF.getText())){
                    if(!User.checkEmailExists(userEmailTF.getText())){
                        if(Main.u.registerUser(userTypeCombo.getSelectionModel().getSelectedIndex(), userNameTF.getText(), Integer.parseInt(userNumberTF.getText()), userPassPF.getText(), userEmailTF.getText())){
                            alerts(Alert.AlertType.INFORMATION,"SUCESSO","O " + userTypeCombo.getSelectionModel().getSelectedItem() + " (" + userNumberTF.getText() + ") - " + userNameTF.getText() + " foi introduzido com sucesso!").showAndWait();
                        }else{
                            alerts(Alert.AlertType.ERROR, "ERRO", "Erro ao introduzir o utilizador, por favor tente novamente!").showAndWait();
                        }
                        clearUserRegistrationFileds();
                    }else{
                        alerts(Alert.AlertType.ERROR, "ERRO", "O Email introduzido já está em uso!").showAndWait();
                    }
                }else{
                    alerts(Alert.AlertType.ERROR, "ERRO", "O Email introduzido não é válido!\nPor favor introduza o email no formato [nome]@[servidor].[domínio]\n(Ex.: john.doe@msm.com)").showAndWait();
                }
            }else{
                alerts(Alert.AlertType.ERROR, "ERRO", "As Palavras Passe não Coíncidem, por favor tente novamente!").showAndWait();
            }
        }else{
            alerts(Alert.AlertType.ERROR, "ERRO", "O tipo de utilizador é de preenchimento obrigatório!").showAndWait();
        }
    }

    private void clearUserRegistrationFileds(){
        userTypeCombo.getSelectionModel().select(-1);
        userNameTF.setText("");
        userNumberTF.setText("");
        userPassPF.setText("");
        userPassConfPF.setText("");
        userEmailTF.setText("");
    }

    protected static Alert alerts(Alert.AlertType aAlertType, String aTitle, String aText){
        Alert generalAlert = new Alert(aAlertType);
        generalAlert.setTitle(aTitle);
        generalAlert.setHeaderText(aTitle);
        generalAlert.setContentText(aText);
        return generalAlert;
    }

    public void collapse(){
        System.out.println("home (collapse) clicked!");
        clearUserRegistrationFileds();
        if(receivedPane != null){
            receivedPane.setVisible(false);
        }

    }

    public void showUsersTable(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/AllUsersPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        }catch(Exception e ){
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    public void showRefrigeratorsTable(){
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/View/RefrigeratorsPane.fxml"));
            receivedPane.getChildren().add(newLoadedPane);
            receivedPane.setVisible(true);
        }catch(Exception e ){
            System.out.println("erro o loader " + e);
            e.printStackTrace();
        }
    }

    /**
     * Retorna utilizador escolhido da tabela.
     * @param mouseEvent
     */
    public void getSelectedArea(javafx.scene.input.MouseEvent mouseEvent) {
        index = areasContTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        editAreaContBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    EditAreaController.setThisArea(listAreasCont.get(index));
                    Stage EditStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/EditArea.fxml"));
                    EditStage.setScene(new Scene(root));
                    EditStage.setTitle("Editar "+listAreasCont.get(index).getNumero());
                    EditStage.setResizable(false);
                    EditStage.centerOnScreen();
                    EditStage.show();
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        });
        removeAreaContBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Area selected = listAreasCont.get(index);
                    Optional<ButtonType> result = alerts(Alert.AlertType.CONFIRMATION, "Remover "+selected.getNumero(), "Tem a certeza que" +
                            " quer remover a Area Controlada "+selected.getNumero()+" com a designação "+selected.getDesignacao()
                            +"?").showAndWait();
                    if(!result.isPresent()){

                    }else if(result.get() == ButtonType.OK){
                        if(Area.removeAreaFromDB(selected)){
                            alerts(Alert.AlertType.INFORMATION, "Removido com sucesso", "Àrea Controlada "+selected.getNumero()
                                    +" removido com sucesso.").showAndWait();
                        }else{
                            alerts(Alert.AlertType.ERROR, "Falha ao remover", "Algo correu mal...").showAndWait();
                        }
                    }else if(result.get() == ButtonType.CANCEL){

                    }
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        });
    }


    public void showAddControlledArea(){
        System.out.println("add controlled area btn clicked!!");
        try{
            Stage AddControlledArea = new Stage();
            Parent rootAddControlledArea = FXMLLoader.load(getClass().getResource("/View/AddControlledArea.fxml"));
            AddControlledArea.setScene(new Scene(rootAddControlledArea));
            AddControlledArea.setTitle("Adicionar Área Controlada");
            AddControlledArea.setResizable(false);
            AddControlledArea.centerOnScreen();
            AddControlledArea.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void insertControlledArea(){
        System.out.println(" -- @ insertControllerArea() --");
        try{
            int areaNumber = -1;
            if(!addCANumberTF.getText().isEmpty()){
                areaNumber = Integer.parseInt(addCANumberTF.getText());
                if(!addCADesignTF.getText().isEmpty()){
                    if(registerControlledArea(areaNumber, addCADesignTF.getText())){
                        alerts(Alert.AlertType.INFORMATION, "SUCESSO", " A Área Controlada " + addCADesignTF.getText() + " com o Número Interno " + areaNumber + "\nfoi inserida com sucesso!").showAndWait();
                        cleanControlledAreaFields();
                    }else{
                        alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inseperado, por favor tente novamente!").showAndWait();
                    }
                }else{
                    System.out.println("design empty");
                    alerts(Alert.AlertType.ERROR, "ERRO", "A Designação da Área Controlada é de Preenchimento Obrigatório!").showAndWait();
                }
            }else{
                System.out.println("number empty");
                alerts(Alert.AlertType.ERROR, "ERRO", "O número da Área Controlada é de Preenchimento Obrigatório!").showAndWait();
            }
        }catch(Exception e){
            alerts(Alert.AlertType.ERROR, "ERRO", "Algo correu mal, por favor tente novamente! \n\n " + e).showAndWait();

        }
    }

    private void cleanControlledAreaFields(){
        addCANumberTF.setText("");
        addCADesignTF.setText("");
    }

    private boolean registerControlledArea(int aNumber, String aDesign){
        try {
            String stmt = "INSERT INTO area (numero, designacao) VALUES (?, ?)";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aNumber);
            ps.setString(2,aDesign);
            return ConnectDB.insertIntoTable(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }





    //WHY FORNECEDORES AND PRODUTOS USE THE getSelected dos users???                         TEMOS DE VER DISTO PÁ!

    @FXML
    public TableView<User> table_users;
    @FXML
    public TableColumn<User, String> tipo_tb_users;
    @FXML
    public TableColumn<User, String> nome_tb_users;
    @FXML
    public TableColumn<User, Integer> numint_tb_users;
    @FXML
    public TableColumn<User, String> email_tb_users;
    public Button RemoveUserBtn;
    public TextField searchTextField;
    public Button EditUserBtn;
    ObservableList<User> listUsers;


    /**
     * Retorna utilizador escolhido da tabela.
     * @param mouseEvent
     */
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = table_users.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        EditUserBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    EditUserController.setThisUser(listUsers.get(index));
                    Stage EditStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/EditUser.fxml"));
                    EditStage.setScene(new Scene(root));
                    EditStage.setTitle("Editar "+nome_tb_users.getCellData(index));
                    EditStage.setResizable(false);
                    EditStage.centerOnScreen();
                    EditStage.show();
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        });
        RemoveUserBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    User selected = listUsers.get(index);
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
                    }
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        });
    }
}
