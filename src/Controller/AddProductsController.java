package Controller;

import Model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.util.Random;


public class AddProductsController {
    @FXML
    private TextField productNameTF;
    @FXML
    private Spinner<?> productPriceNS;
    @FXML
    private TextField productEANTF;
    @FXML
    private TextField productBrandTF;
    @FXML
    private RadioButton productFreshYESRB;
    @FXML
    private RadioButton productFreshNORB;
    @FXML
    private Button addProductBTN;

    @FXML
    protected void initialize(){
        productFreshNORB.setSelected(false);
        productFreshYESRB.setSelected(false);
    }

    public void addProduct(){






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
