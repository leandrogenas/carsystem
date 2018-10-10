package atox.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {



    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private void initialize() {
        System.out.println("Inicializou");
    }

    @FXML
    private void btnEntrarClicado(){
        boolean auth = this.logar(txtLogin.getCharacters().toString(), txtSenha.getCharacters().toString());

    }

    protected boolean logar(String usuario, String senha){
        return true;
    }

}
