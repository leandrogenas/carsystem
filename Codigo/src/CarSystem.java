import atox.Configs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CarSystem extends Application {

    private Configs configs = Configs.getConfigs("carsystem.ini");

    private Stage mainStage;
    private Scene scenePrincipal;


    private boolean autenticar(String login, String senha){
        return login.equals("admin") && senha.equals("senha");
    }

    private void carregaTelaPrincipal() throws IOException {
        Pane telaPrincipal = FXMLLoader.load(getClass().getResource("/fxml/tela_principal.fxml"));
        scenePrincipal = new Scene(telaPrincipal, 900, 600);
        mainStage.setScene(scenePrincipal);
        mainStage.show();
    }


    private void inicializaSistema(){
        try{
            carregaTelaPrincipal();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void exibeTela(){
        mainStage.show();
    }

    private void inicializaLogin() throws IOException {
        GridPane root = FXMLLoader.load(getClass().getResource("/fxml/tela_login.fxml"));

        root.lookup("#btnEntrar").setOnMouseClicked(event -> {
            boolean auth = this.autenticar(
                    ((TextField) root.lookup("#txtLogin")).getText(),
                    ((PasswordField) root.lookup("#txtSenha")).getText()
            );

            if(auth)
                inicializaSistema();
            else
                ((Label) root.lookup("#lblFalha")).setVisible(true);

        });

        mainStage.setTitle("Login");
        mainStage.setScene(new Scene(root, 300, 200));
        mainStage.show();
    }

    @Override
    public void start(Stage mainStage) throws Exception{
        this.mainStage = mainStage;

        //inicializaLogin();
        inicializaSistema();
    }

    public static void main(String[] args){
        launch(args);
    }

}
