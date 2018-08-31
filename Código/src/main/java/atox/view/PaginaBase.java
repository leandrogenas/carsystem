package atox.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public abstract class PaginaBase implements PaginaInterface {

    private Pane conteudo;

    public abstract String getNomeFXML();
    public abstract String getTitulo();

    private void loadPane() throws Exception{
        URL fxml = getClass().getClassLoader().getResource(getNomeFXML());
        if(fxml == null)
            throw new Exception("Arquivo FXML não encontrado");

        conteudo = FXMLLoader.load(fxml);
    }

    public PaginaBase() throws Exception{
        loadPane();
    }

    public Pane getPane(){
        return conteudo;
    }

}
