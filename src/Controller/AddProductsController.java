package Controller;

import Model.ConnectDB;
import Model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;


public class AddProductsController {
    @FXML
    private TextField productNameTF;
    @FXML
    private Spinner<Double> productPriceNS;
    @FXML
    private TextField productEANTF;
    @FXML
    private TextField productBrandTF;
    @FXML
    private RadioButton productFreshYESRB;
    @FXML
    private RadioButton productFreshNORB;

    @FXML
    /**
     * Defenoção de elementos visuais importantes
     */
    protected void initialize(){
        productFreshNORB.setSelected(false);
        productFreshYESRB.setSelected(false);
    }

    /**
     * Limpa os campos de inserção de produtos após o término da mesma
     */
    private void cleanFields(){
        productNameTF.setText("");
        productPriceNS.getEditor().setText("");
        productEANTF.setText("");
        productBrandTF.setText("");
        productFreshYESRB.setSelected(false);
        productFreshNORB.setSelected(false);
    }

    /**
     * Adicionar Produto
     */
    public void addProduct(){
        boolean cont = true;
        boolean contEan = true;
        int freshState = -1;
        if(!productNameTF.getText().isEmpty()){
            if(productPriceNS.getValue() <= 0.0){
                Alert noPriceConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                noPriceConfirmation.setTitle("Confirmação:");
                noPriceConfirmation.setHeaderText("Não Definiu nenhum preço para o Artigo a Introduzir, deseja registar o artigo sem preço definido?");
                ButtonType yesBtn = new ButtonType("Sim");
                ButtonType noBtn = new ButtonType("Não");
                noPriceConfirmation.getButtonTypes().setAll(yesBtn, noBtn);
                noPriceConfirmation.showAndWait();
                if (noPriceConfirmation.getResult().equals(yesBtn)) {
                    cont = true;
                }else{
                    cont = false;
                }
            }
            if(cont){
                if((productFreshYESRB.isSelected())||(productFreshNORB.isSelected())){
                    if(productFreshYESRB.isSelected()){
                        freshState = 1;
                    }else{
                        freshState = 0;
                    }
                    if(freshState == 0){
                        if(!productEANTF.getText().isEmpty()){
                            contEan = true;
                        }else{
                            contEan = false;
                        }
                    }else{
                        contEan = true;
                    }
                    if(((freshState == 0) && (contEan)) || ((freshState == 1) && (contEan))){
                        if(!productBrandTF.getText().isEmpty()){
                            boolean whileControl = true;
                            int generatedNumInt = -1;
                            while(whileControl){
                                generatedNumInt = new Random().nextInt(9999999);
                                whileControl = Product.checkNumInt(generatedNumInt);
                                System.out.println("no while\n geberated: " + generatedNumInt);
                            }
                            try{
                                if(registerProduct(generatedNumInt,productNameTF.getText(),freshState,productPriceNS.getValue(), productEANTF.getText(),productBrandTF.getText())){
                                    MainScreenController.alerts(Alert.AlertType.INFORMATION,"Sucesso na Inserção de Produto!", "O produto " + productNameTF.getText() + " da Marca "
                                                                + productBrandTF.getText() + " com o EAN " + productEANTF.getText() + " foi guarado com o Número Interno " + generatedNumInt).showAndWait();
                                }
                                cleanFields();
                            }catch(Exception e){
                                e.printStackTrace();
                                MainScreenController.alerts(Alert.AlertType.ERROR, "ERRO", "Aconteceu um erro inesperado, por favor tente novamente!").showAndWait();
                            }
                        }else{
                            MainScreenController.alerts(Alert.AlertType.ERROR, "Campo de Preenchimento Obriogatório Vazio!", "O Campo \"Marca\" é de preenchimeto obrigatório!").showAndWait();
                        }
                    }else{
                        MainScreenController.alerts(Alert.AlertType.ERROR, "Campo de Preenchimento Obriogatório Vazio!", "O Campo \"EAN\" é de preenchimeto obrigatório!").showAndWait();
                    }
                }else{
                    MainScreenController.alerts(Alert.AlertType.ERROR, "Campo de Preenchimento Obriogatório Vazio!", "O Campo \"Fresco ?\" é de preenchimeto obrigatório!").showAndWait();
                }
            }else{
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Informação", "A Inserção do produto foi cancelada até á introdução de precificação!").showAndWait();
            }
        }else{
            MainScreenController.alerts(Alert.AlertType.ERROR, "Campo de Preenchimento Obrigaótio Vazio!", "O Nome do Produto é de Preenchimento Obrigatório!").showAndWait();
        }
    }

    /**
     * Registar Produto na Base de Dados
     * @param aNumber Número Interno do Produto
     * @param aName Nome do Produto
     * @param aFresco Se o Produto é (ou não) fresco
     * @param aPreco Preço do Produto
     * @param aEAN EAN do Produto
     * @param aMarca Marca do Produto
     * @return True/False
     */
    public static boolean registerProduct(int aNumber, String aName, int aFresco, double aPreco, String aEAN, String aMarca){
        try {
            String stmt = "INSERT INTO produto (n_interno, nome, fresco, preco, ean, marca) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aNumber);
            ps.setString(2,aName);
            ps.setInt(3, aFresco);
            ps.setDouble(4, aPreco);
            ps.setString(5, aEAN);
            ps.setString(6, aMarca);
            return ConnectDB.insertIntoTable(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Controla o Campo do EAN consoante a definição de se o Produto é fresco (não tem EAN) ou não é Fresco (tem EAN)
     */
    public void eanFieldControl(){
        if(productFreshYESRB.isSelected()){
            productEANTF.setEditable(false);
            productEANTF.setText("");
            productEANTF.setDisable(true);
        }else if(productFreshNORB.isSelected()){
            productEANTF.setEditable(true);
            productEANTF.setDisable(false);
        }
    }




}
