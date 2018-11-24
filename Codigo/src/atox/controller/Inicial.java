package atox.controller;

import atox.BancoDeDados;
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
        BancoDeDados instancia = BancoDeDados.getInstancia();
    }

    @FXML
    public void novoOrcamento(){
        Scene principal = (Scene) panePrincipal.getParent().getScene();
        ((SplitMenuButton) principal.lookup("#btnOrcamento")).fire();

    }

}
