package atox.view;

import javafx.scene.Node;

public interface PaginaInterface {

    String getNomeFXML();
    String getTitulo();
    Node getConteudo() throws Exception;

}
