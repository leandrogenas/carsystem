package atox.controller;

import atox.Tela;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public final class ViewController {
    @FXML
    public static AnchorPane paneTitulo;

    @FXML
    public static AnchorPane paneConteudo;

    @FXML
    public static Label lblTitulo;

    public static void carregaTela(Tela tela){
        try {
            Parent conteudo = tela.getFXMLLoader().load();

            lblTitulo.setText(tela.getTitulo());
            paneConteudo.getChildren().clear();
            paneConteudo.getChildren().add(conteudo);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
