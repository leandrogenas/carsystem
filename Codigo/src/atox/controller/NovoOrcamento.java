package atox.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NovoOrcamento {

    private int numPasso;

    private Scene passoAnterior;
    private Scene passoAtual;

    @FXML
    private GridPane gridPrincipal;

    public void initialize(){
        numPasso = 1;
    }



    @FXML
    public void proximoPasso(ActionEvent ev){


    }

}
