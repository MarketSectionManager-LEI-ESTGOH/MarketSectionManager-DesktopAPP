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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.User;

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
    protected void initialize(){
        usersTable();
    }

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

    public void usersTable(){
        System.out.println("usersTable Edit btn clicked!!");
        try {
            System.out.println("-------------------------- no try ---------------------------");
            tipo_tb_users.setCellValueFactory(new PropertyValueFactory<Model.User, String>("userTypeConv"));
            nome_tb_users.setCellValueFactory(new PropertyValueFactory<Model.User, String>("username"));
            email_tb_users.setCellValueFactory(new PropertyValueFactory<Model.User, String>("email"));
            numint_tb_users.setCellValueFactory(new PropertyValueFactory<Model.User, Integer>("userID"));
        }catch(Exception w){
            System.out.println("------------------------ exception ----------------------"); w.printStackTrace();System.out.println("------------------------ exception ----------------------");}
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

        /* AUTO REFRESH ANTIGO
        if(!firstUserRun){
            table_users.focusedProperty().addListener(new ChangeListener<Boolean>()
            {
                boolean refresh = false;
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean onHidden, Boolean onShown)
                {
                    if(onHidden){
                        refresh = true;
                    }else if(onShown){
                        if(searchTextField.getText() == null){
                            if(refresh == true){
                                System.out.println("REFRESH");
                                usersTable();
                                refresh = false;
                            }
                        }
                    }
                }

            });
            firstUserRun = true;
        }*/
    }


    public void register(){
        System.out.println("register btn clicked!!");
        try{
            Stage AddStage = new Stage();
            Parent rootAddStage = FXMLLoader.load(getClass().getResource("/View/AddUser.fxml"));
            AddStage.setScene(new Scene(rootAddStage));
            AddStage.setTitle("Adicionar Utilizador");
            AddStage.setResizable(false);
            AddStage.centerOnScreen();
            AddStage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public TableView<User> getTable_users() {
        return table_users;
    }
}
