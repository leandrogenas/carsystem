package atox.view;

import javafx.scene.Node;

public class PaginaErro implements PaginaInterface {

    private String msg;

    public PaginaErro(String s) {
        msg = s;
    }

    @Override
    public String getNomeFXML() {
        return "/fxml/pagina_erro.fxml";
    }

    @Override
    public String getTitulo() {
        return msg;
    }

    @Override
    public Node getConteudo() throws Exception {
        return null;
    }
}
