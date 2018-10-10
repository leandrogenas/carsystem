package atox.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.net.URL;

public abstract class PaginaBase implements PaginaInterface {

    private Pane conteudo;

    public abstract String getNomeFXML();
    public abstract String getTitulo();

    private void loadConteudo() throws Exception{
        URL fxml = getClass().getResource(getNomeFXML());
        if(fxml == null)
            throw new Exception("Arquivo FXML não encontrado");

        conteudo = FXMLLoader.load(fxml);
    }

    public PaginaBase() throws Exception{
        loadConteudo();
    }

    public Node getConteudo(){
        return conteudo.getChildren().get(0);
    }

}
