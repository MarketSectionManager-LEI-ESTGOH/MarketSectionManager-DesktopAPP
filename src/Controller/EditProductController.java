package Controller;

import Model.ConnectDB;
import Model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.util.Random;

public class EditProductController {
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
    private ToggleGroup typeGroup;
    @FXML
    private RadioButton productFreshNORB;
    @FXML
    private Button editProductBTN;
    @FXML
    private TextField productNumIntTF;
    private static Product thisProduct = null;

    /**
     * Definir o registo sobre o qual se está a trabalhar
     * @param aThisProduct Objeto do tipo Product
     */
    protected static void setThisProduct(Product aThisProduct) {
        thisProduct = aThisProduct;
    }

    @FXML
    /**
     * Definir propriedades de elementos gráficos
     */
    protected void initialize(){
        productNumIntTF.setEditable(false);
        productNumIntTF.setText(String.valueOf(String.valueOf(thisProduct.getNum_int())));
        productNameTF.setText(thisProduct.getName());
        if(thisProduct.getFresh() == 1){
            productFreshYESRB.setSelected(true);
            productFreshNORB.setSelected(false);
            productEANTF.setDisable(true);
        }else if(thisProduct.getFresh() == 0){
            productFreshYESRB.setSelected(false);
            productFreshNORB.setSelected(true);
        }else{
            productFreshYESRB.setSelected(false);
            productFreshNORB.setSelected(false);
        }
        productEANTF.setText(thisProduct.getEan());
        productBrandTF.setText(thisProduct.getBrand());

        productPriceNS.getEditor().setText(String.valueOf(thisProduct.getPrice()));
    }


    /**
     * controlo do campo EAN consoante a definição se o produto é fresco (tem EAN) ou se não é fresco (não tem EAN)
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

    /**
     * Guardar Alterações feitas sobre o registo
     */
    public void editProduct(){
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
                            }
                            try {
                                String stmt = "UPDATE produto SET nome = ?, fresco = ?, preco = ?, ean = ?, marca = ? WHERE n_interno = ?";
                                PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
                                ps.setString(1, productNameTF.getText());
                                ps.setInt(2, freshState);
                                ps.setDouble(3, productPriceNS.getValue());
                                ps.setString(4, productEANTF.getText());
                                ps.setString(5, productBrandTF.getText());
                                ps.setInt(6, thisProduct.getNum_int());
                                if(ConnectDB.updateDB(ps)){
                                    MainScreenController.alerts(Alert.AlertType.INFORMATION, "Atualizado com sucesso",
                                            "O Produto foi atualizado com sucesso.").showAndWait();
                                    Stage stage = (Stage) editProductBTN.getScene().getWindow();
                                    stage.close();
                                }else{
                                    MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                                            "Algo correu mal, Sem sucesso ao atualizar.").showAndWait();
                                }
                            }catch (Exception e){
                                MainScreenController.alerts(Alert.AlertType.ERROR, "Algo correu mal...",
                                        "Algo correu mal, Sem sucesso ao atualizar. "+e).showAndWait();
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

}
