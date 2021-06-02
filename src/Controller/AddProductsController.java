package Controller;

import Model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    protected void initialize(){
        productFreshNORB.setSelected(false);
        productFreshYESRB.setSelected(false);
    }

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
                            //add
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






        boolean whileControl = true;
        int generatedNumInt = -1;
        while(whileControl){
            generatedNumInt = new Random().nextInt(9999999);
            whileControl = Product.checkNumInt(generatedNumInt);
            System.out.println("no while\n geberated: " + generatedNumInt);
        }
    }

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
