package atox.controller;


import atox.view.Pagina;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

public class TemplatePaginasController {

    @FXML
    private ScrollPane paneConteudo;

    private void carregaPagina(Pagina pag){
        paneConteudo.setContent(pag.getNode());
    }

    public void initialize(){
        System.out.println("eeyw");
    }

}
