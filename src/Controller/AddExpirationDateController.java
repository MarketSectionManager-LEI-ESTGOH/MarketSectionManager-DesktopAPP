package Controller;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AddExpirationDateController {

    @FXML
    private Text hidenLBLAddVal;
    @FXML
    private TextField nIntAddValTF;
    @FXML
    private DatePicker valAddValDP;
    @FXML
    private Button saveBtn;

    @FXML
    protected void initialize(){
        saveBtn.setDisable(true);

        InvalidationListener inliost = (evt) -> {
            if( !nIntAddValTF.isFocused() ) {
                checkIfProductExists();
            };
        };

        nIntAddValTF.focusedProperty().addListener(
                new WeakInvalidationListener(inliost)
        );


    }

    public void checkIfProductExists(){
        if(!nIntAddValTF.getText().isEmpty()){
            hidenLBLAddVal.setText("lost focus");
            hidenLBLAddVal.setVisible(true);
        }
    }

    public void abcfunc(){
        System.out.println("YAH!");
        if(nIntAddValTF.getText().equalsIgnoreCase("joao")){

        }
    }
}
