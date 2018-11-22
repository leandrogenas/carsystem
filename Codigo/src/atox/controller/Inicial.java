package atox.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Inicial {

    @FXML
    private Pane panePrincipal;

    public void initialize(){
        System.out.println("Inicializou");
    }

    @FXML
    public void novoOrcamento(){
        Scene principal = (Scene) panePrincipal.getParent().getScene();
        ((SplitMenuButton) principal.lookup("#btnOrcamento")).fire();

    }

}
