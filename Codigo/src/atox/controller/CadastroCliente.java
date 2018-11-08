package atox.controller;

import atox.utils.MaskFieldUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.InputMismatchException;

import static atox.utils.Validators.isCPF;


public class CadastroCliente {
    @FXML
    private TextField cpfField;

    @FXML
    public void initialize() {
        MaskFieldUtil.cpfMask(cpfField);
    }

    public void validaCliente() {
        if(!isCPF(cpfField.getText())) {
            return;
        }
    }
}
