package atox.controller;


import atox.view.PaginaFactory;
import atox.view.PaginaInterface;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

public class TemplatePaginasController {

    @FXML
    private ScrollPane paneConteudo;

    @FXML
    private Text lblTitulo;

    private void carregaPagina(PaginaInterface pag){
        try {
            lblTitulo.setText(pag.getTitulo());
            paneConteudo.setContent(pag.getConteudo());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize(){
        carregaPagina(PaginaFactory.getPagina("principal"));
    }

}
