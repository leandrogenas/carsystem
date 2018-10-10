import atox.Configs;
import atox.Principal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CarSystem extends Application {

    private static String USUARIO = "admin";
    private static String SENHA = "senha";

    private Stage mainStage;

    public boolean autenticar(String login, String senha){
        return login.equals(USUARIO) && senha.equals(SENHA);
    }

    public void inicializaSistema(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/template_paginas.fxml"));
            Scene scenePrincipal = new Scene(root, 1366, 768);
            mainStage.setScene(scenePrincipal);
            mainStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void inicializaLogin() throws IOException {
        GridPane root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

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

        inicializaLogin();
    }

    public static void main(String[] args){
        launch(args);
    }

}
