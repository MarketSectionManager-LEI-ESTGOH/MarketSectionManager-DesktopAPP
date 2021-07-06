package Controller;

import Model.Area;
import Model.ExprirationDate;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class EditExpirationDateController {
    @FXML
    private TextField nIntEdValTF;
    @FXML
    private TextField eanEdValTF;
    @FXML
    private TextField nomeEdValTF;
    @FXML
    private TextField offEdValTF;
    @FXML
    private TextField markEdValTF;
    @FXML
    private TextField antValEdValTF;
    @FXML
    private DatePicker newValEdValDP;
    private static ExprirationDate thisED = null;

    protected static void setThisED(ExprirationDate aThisED) {
        thisED = aThisED;
    }

    @FXML
    protected void initialize(){
        System.out.println("edit expiration date initialize");
        nIntEdValTF.setText(thisED.getNumInterno());
        nIntEdValTF.setEditable(false);
        eanEdValTF.setText(thisED.getEan());
        eanEdValTF.setEditable(false);
        nomeEdValTF.setText(thisED.getNome());
        nomeEdValTF.setEditable(false);
        offEdValTF.setText(String.valueOf(thisED.getOffset()));
        offEdValTF.setEditable(false);
        markEdValTF.setText(thisED.getMardown());
        markEdValTF.setEditable(false);
        antValEdValTF.setText(""+thisED.getExpirationDate());
        antValEdValTF.setEditable(false);
    }

    public void saveChanges(){
        System.out.println(" save changes btn clicked");
        LocalDate result = newValEdValDP.getValue();
        if(result != null){
            if(ExprirationDate.updateExpDateInDB(thisED, String.valueOf(result))){
                MainScreenController.alerts(Alert.AlertType.INFORMATION, "Sucesso", "A Data de alidade do produto " + thisED.getNome() + "foi " +
                        " alterada de " + thisED.getExpirationDate() + " para " + result).showAndWait();
            }else{
                MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Tem de selecionar uma data! Se desejar abortar a edição feche a janela!").showAndWait();
            }
        }else{
            MainScreenController.alerts(Alert.AlertType.ERROR, "Erro", "Tem de selecionar uma data! Se desejar abortar a edição feche a janela!").showAndWait();
        }

    }
}
