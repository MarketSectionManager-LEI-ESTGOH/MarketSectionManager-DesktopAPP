package Controller;

import Model.ConnectDB;
import Model.ExprirationDate;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddExpirationDateController {

    @FXML
    private Text hidenLBLAddVal;
    @FXML
    private TextField nIntAddValTF;
    @FXML
    private DatePicker valAddValDP;
    @FXML
    private Button saveBtn;
    private ExprirationDate auxED = new ExprirationDate();
    private int usedCode = -1, pID = -1;

    @FXML
    /**
     * Define Propriedades visuais de alguns componentes
     */
    protected void initialize(){
        saveBtn.setDisable(true);
        hidenLBLAddVal.setVisible(false);
    }

    /**
     * Limpa os campos de Inserção após a mesma
     */
    private void clearFields(){
       hidenLBLAddVal.setText("");
       hidenLBLAddVal.setVisible(false);
       nIntAddValTF.setText("");
      valAddValDP.getEditor().setText("Definir Validade");
      usedCode = -1;
      pID = -1;
    }

    /**
     * Verifica se o Produto ao qual está a ser registada uma validade existe na base de dados
     */
    public void checkIfProductExists(){
        int inputedCode = -1;
        String prodName;
        try{
            if(nIntAddValTF.getText().length() > 0){
                inputedCode = Integer.parseInt(nIntAddValTF.getText());
            }
            if(nIntAddValTF.getText().equalsIgnoreCase("") || nIntAddValTF.getText().length() == 0){
                prodName = null;
            }else{
                prodName = ConnectDB.getString(ps(inputedCode));
            }

            if( prodName != null){
                hidenLBLAddVal.setText(ConnectDB.getString(ps(inputedCode)));
                hidenLBLAddVal.setVisible(true);
                saveBtn.setDisable(false);
                auxED.setNome(hidenLBLAddVal.getText());
                usedCode = inputedCode;
            }else{
                prodName = null;
                hidenLBLAddVal.setVisible(false);
                saveBtn.setDisable(true);
            }
            if(nIntAddValTF.getText().equalsIgnoreCase("")){
                hidenLBLAddVal.setText("");
                prodName = null;
                hidenLBLAddVal.setVisible(false);
                saveBtn.setDisable(true);
            }
        }catch(NumberFormatException nfe){
            if(!nIntAddValTF.getText().isEmpty()){
                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Este Campo não aceita Letras, APENAS NÚMEROS").showAndWait();
                nIntAddValTF.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Prepared statement para o preenchimento de detalhes do registo de validade
     * @param aCmd Coamndo que se quer receber da base de Dados
     * @param aKnownCmd Comando que servirá de chave de obtenção
     * @param aCode Igualizador ao comando de obtenção
     * @return PreparedStatement
     */
    private PreparedStatement ps2(String aCmd, String aKnownCmd, int aCode){
        String stmt = "SELECT " + aCmd + " FROM produto WHERE " + aKnownCmd + " = ?";
        try {
            PreparedStatement prepSt = ConnectDB.getConn().prepareStatement(stmt);
            prepSt.setInt(1,aCode);
            return prepSt;
        }catch (Exception e){
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
        }
        return null;
    }

    /**
     * Preparedstatement para a verificação de existência do Produto
     * @param aCode Código do Produto
     * @return Preparedstatement
     */
    private PreparedStatement ps(int aCode){
        String stmt = "SELECT nome FROM produto WHERE n_interno = ? OR ean = ?";
        try {
            PreparedStatement prepSt = ConnectDB.getConn().prepareStatement(stmt);
            prepSt.setInt(1,aCode);
            prepSt.setInt(2,aCode);
            return prepSt;
        }catch (Exception e){
            MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
        }
        return null;
    }

    /**
     * Guardar Registo de Validade
     */
    public void saveExpirationDate(){
        fillExpirationDetails();
        if(registerExpirationDate()){
            MainScreenController.alerts(Alert.AlertType.INFORMATION, "Sucesso", "A validade para o produto ("+auxED.getNumInterno()+") " + auxED.getNome() + " - " + auxED.getNumInterno() + "" +
                    " foi registada com sucesso para " + auxED.getExpirationDate()).showAndWait();
            clearFields();
        }else{
            MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Aconteceu um erro inesperado, por favor tente novamnete!").showAndWait();
        }
    }

    /**
     * Inserir Registo de Validade na Base de Dados
     * @return
     */
    private boolean registerExpirationDate(){
        try {
            String stmt = "INSERT INTO validade (ean, validade, n_interno, nome, offset, markdown, produto_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setString(1, auxED.getEan());
            ps.setString(2,String.valueOf(auxED.getExpirationDate()));
            ps.setInt(3, Integer.parseInt(auxED.getNumInterno()));
            ps.setString(4, auxED.getNome());
            ps.setInt(5, auxED.getOffset());
            ps.setInt(6, auxED.getMarkdownState());
            ps.setInt(7,pID);
            return ConnectDB.insertIntoTable(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Aconteceu um erro inesperado, por favor tente novamnete!").showAndWait();
        }
        return false;
    }

    /**
     * Preencher detalhes de um registo de validade
     */
    private void fillExpirationDetails(){
        try {
            LocalDate result = valAddValDP.getValue();
            if (result != null) {
                auxED.setExpirationDate(Date.valueOf(result));
                if (ConnectDB.checkProductNumIt(usedCode)) {
                    auxED.setNumInterno(String.valueOf(usedCode));
                    pID = Integer.parseInt(ConnectDB.getString(ps2("id","n_interno",usedCode)));
                    String ean = ConnectDB.getString(ps2("ean", "n_interno", usedCode));
                    if (ean != null) {
                        auxED.setEan(ean);
                    } else {
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Aconteceu um erro inesperado, porfaor tente novamnete!").showAndWait();
                    }
                } else {
                    auxED.setEan(String.valueOf(usedCode));
                    pID = Integer.parseInt(ConnectDB.getString(ps2("id","ean",usedCode)));
                    String nInt = ConnectDB.getString(ps2("n_interno", "ean", usedCode));
                    if (nInt != null) {
                        auxED.setNumInterno(nInt);
                    } else {
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Aconteceu um erro inesperado, porfaor tente novamnete!").showAndWait();
                    }
                }
                auxED.setMarkdownState(0);
                auxED.setOffset(20);
            } else {
                MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "O Campo «Data de Validade é Obrigatório!").showAndWait();
            }
        }catch(Exception e){
            MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Aconteceu um erro inesperado, por favor tente novamnete!").showAndWait();
        }
    }
}
