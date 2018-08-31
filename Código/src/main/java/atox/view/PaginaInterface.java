package atox.view;

import javafx.scene.layout.Pane;

public interface PaginaInterface {

    String getNomeFXML();
    String getTitulo();
    Pane getPane() throws Exception;

}
