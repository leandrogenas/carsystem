package atox;

import atox.CarSystem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Login {

    @FXML
    private TextField login;
    @FXML
    private PasswordField senha;
    @FXML
    private Label lblFalha;
    @FXML
    private GridPane paneLogin;

    private void inicializaSistema(){
        try{
            CarSystem.init(new Stage());

            fechaLogin();
        }catch (Exception e){
            System.out.println("Erro na classe atox.Login");
            e.printStackTrace();
        }
    }

    private void fechaLogin(){
        ((Stage) paneLogin.getScene().getWindow()).close();
    }

    @FXML
    private void autenticar(){
        if(login.getText().equals("admin") && senha.getText().equals("senha"))
            inicializaSistema();
        else
            lblFalha.setVisible(true);
    }

    public void initialize(){
        lblFalha.setVisible(false);
    }

}
