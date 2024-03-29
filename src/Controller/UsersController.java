package Controller;

import Model.ConnectDB;
import Model.User;
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

public class UsersController {
    @FXML
    private TableView<Model.User> table_users;
    @FXML
    private TableColumn<Model.User, String> tipo_tb_users;
    @FXML
    private TableColumn<Model.User, String> nome_tb_users;
    @FXML
    private TableColumn<Model.User, Integer> numint_tb_users;
    @FXML
    private TableColumn<Model.User, String> email_tb_users;
    @FXML
    private Button RemoveUserBtn;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button EditUserBtn;
    private ObservableList<User> listUsers;
    private int index = -1;

    @FXML
    /**
     * Gera a tabela
     */
    protected void initialize(){
        usersTable();
    }

    /**
     * Retorna utilizador escolhido da tabela.
     * @param mouseEvent MouseEvent
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
                    if(listUsers.get(index).getUserID() == Main.u.getUserID()){
                        EditUserController.setSelfProfile(true);
                    }else {
                        EditUserController.setSelfProfile(false);
                    }
                    Stage EditStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/EditUser.fxml"));
                    EditStage.setScene(new Scene(root));
                    EditStage.getIcons().add(new Image("/Images/logoicon.png"));
                    EditStage.setTitle("Editar "+nome_tb_users.getCellData(index));
                    EditStage.setResizable(false);
                    EditStage.centerOnScreen();
                    EditStage.show();
                }catch (Exception e){
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
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
                    MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
                }

            }
        });
    }

    /**
     * Obtem a informação e gera a tabela
     */
    public void usersTable(){
        try {
            tipo_tb_users.setCellValueFactory(new PropertyValueFactory<Model.User, String>("userTypeConv"));
            nome_tb_users.setCellValueFactory(new PropertyValueFactory<Model.User, String>("username"));
            email_tb_users.setCellValueFactory(new PropertyValueFactory<Model.User, String>("email"));
            numint_tb_users.setCellValueFactory(new PropertyValueFactory<Model.User, Integer>("userID"));
        }catch(Exception w){
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
        }
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
                }  if(String.valueOf(User.getUserID()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  if(User.getUserTypeConv().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } if(User.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else{
                    return false;
                }
            });
        });

        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());

        table_users.setItems(sortedData);
    }

    /**
     * Mostra a pane de registo de novos utilizadores
     */
    public void register(){
        try{
            Stage AddStage = new Stage();
            Parent rootAddStage = FXMLLoader.load(getClass().getResource("/View/AddUser.fxml"));
            AddStage.setScene(new Scene(rootAddStage));
            AddStage.getIcons().add(new Image("/Images/logoicon.png"));
            AddStage.setTitle("Adicionar Utilizador");
            AddStage.setResizable(false);
            AddStage.centerOnScreen();
            AddStage.show();
        }catch (Exception e){
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
        }
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
            if(PDFExporter.genPdf("utilizadores", path)){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Exportado", "Exportado com Sucesso.").showAndWait();
            }else{
                MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Algo correu mal ao exportar...").showAndWait();
            }
        }
    }

    public TableView<User> getTable_users() {
        return table_users;
    }
}
