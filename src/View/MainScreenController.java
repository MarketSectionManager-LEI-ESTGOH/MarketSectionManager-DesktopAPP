package View;

import Controller.Product;
import Controller.User;
import Model.ConnectDB;
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
import java.util.Optional;

public class MainScreenController {

    public Button editProfileBtn = new Button();
    public Button logoutBtn = new Button();
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
    ObservableList<Product> listProducts;
    private int index = -1;
    protected static User selectedUser = null;


    public Pane productsPane;

    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Product, Integer> numIntProfuctsCol;
    @FXML
    public TableColumn<Product, String> productNameCol;
    @FXML
    public TableColumn<Product, Integer> freshProductCol;
    @FXML
    public TableColumn<Product, Float> priceProductCol;
    @FXML
    public TableColumn<Product, Integer> vendaProductCol;
    @FXML
    public TableColumn<Product, BigDecimal> eanProductCol;
    @FXML
    public TableColumn<Product, String> brandProductCol;

    public Button RemoveProductBtn;
    public TextField searchProductTextField;
    public Button EditProductBtn;
    public Button addProductBtn;


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
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
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
        currentStage = (Stage) logoutBtn.getScene().getWindow();
        currentStage.close();
        View.Main.u = null;
        new Main().start(new Stage());

    }

    public void register(){
        System.out.println("register btn clicked!!");
        try{
            //EditUserController.setThisUser(listUsers.get(index));
            //System.out.println(listUsers.get(index).getUserID());
            Stage AddStage = new Stage();
            Parent rootAddStage = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
            AddStage.setScene(new Scene(rootAddStage));
            AddStage.setTitle("Adicionar Utilizador");
            AddStage.setResizable(false);
            AddStage.centerOnScreen();
            AddStage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void usersTable(){
        collapse();
        System.out.println("usersTable Edit btn clicked!!");

        tipo_tb_users.setCellValueFactory(new PropertyValueFactory<User, String>("userTypeConv"));
        nome_tb_users.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        email_tb_users.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        numint_tb_users.setCellValueFactory(new PropertyValueFactory<User, Integer>("userID"));

        listUsers = ConnectDB.getAllUsers();
        FilteredList<User> filteredData = new FilteredList<>(listUsers, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(User ->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(User.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(User.getUserID()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(User.getUserTypeConv().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if(User.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else{
                    return false;
                }
            });
        });

        SortedList<User> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_users.comparatorProperty());

        table_users.setItems(sortedData);
        allUsersPane.setVisible(true);
    }

    public void productsTable(){
        collapse();
        System.out.println("products Edit btn clicked!!");

        numIntProfuctsCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("num_int"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        freshProductCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("fresh"));
        priceProductCol.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
        vendaProductCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("venda"));
        eanProductCol.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("ean"));
        brandProductCol.setCellValueFactory(new PropertyValueFactory<Product, String>("brand"));


        listProducts = ConnectDB.getAllProducts();
        FilteredList<Product> filteredData = new FilteredList<>(listProducts, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(Product ->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Product.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(Product.getNum_int()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(Product.getVenda()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(Product.getPrice()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(String.valueOf(Product.getEan()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(Product.getBrand().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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

    public void addUser(){
        System.out.println("add user btn clicked!!");
            try {
                ConnectDB.loadProperties();
                if((userPassPF.getText() == userPassConfPF.getText()) && (userTypeCombo.getSelectionModel().getSelectedIndex() != -1)){


                if(View.Main.u.registerUser(userTypeCombo.getSelectionModel().getSelectedIndex(), userNameTF.getText(), Integer.parseInt(userNumberTF.getText()), userPassPF.getText(), userEmailTF.getText())) {
                            alerts(Alert.AlertType.INFORMATION, "SUCESSO", "Utilizador inserido com sucesso!").showAndWait();
                        } }else{
                    System.out.println("erro - pass igual");
                }
                clearUserRegistrationFileds();
            }catch(NumberFormatException nfe){
                alerts(Alert.AlertType.INFORMATION,"ERRO","O campo número de funcionário apenas aceita números").showAndWait();
                clearUserRegistrationFileds();
            }catch(Exception e){
                alerts(Alert.AlertType.INFORMATION,"ERRO","Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
                clearUserRegistrationFileds();
            }

    }

    private void clearUserRegistrationFileds(){
        userTypeCombo.getSelectionModel().select(-1);
        userNameTF.setText("");
        userNumberTF.setText("");
        userPassPF.setText("");
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
        clearUserRegistrationFileds();
        registerPane.setVisible(false);
        allUsersPane.setVisible(false);
        productsPane.setVisible(false);
    }

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
                    Parent root = FXMLLoader.load(getClass().getResource("EditUser.fxml"));
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
                        alerts(Alert.AlertType.ERROR, "Falha ao remover", "Não pode remover a conta que está" +
                                " a utilizar.").showAndWait();
                    }else{
                        System.out.println(selected.getUserID());
                        Optional<ButtonType> result = alerts(Alert.AlertType.CONFIRMATION, "Remover "+selected.getUsername(), "Tem a certeza que" +
                                " quer remover o utilizador "+selected.getUsername()+" com o número interno "+selected.getUserID()
                                +"?").showAndWait();
                        if(!result.isPresent()){

                        }else if(result.get() == ButtonType.OK){
                            if(User.removeUserFromDB(selected)){
                                alerts(Alert.AlertType.INFORMATION, "Removido com sucesso", "Utilizador "+selected.getUsername()
                                        +" removido com sucesso.").showAndWait();
                            }else{
                                alerts(Alert.AlertType.ERROR, "Falha ao remover", "Algo correu mal...").showAndWait();
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
