package atox.tela;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class NovoOrcamento extends Tela {

    @Override
    public String getNomeFXML() { return "/fxml/tela_novo_orcamento_1.fxml"; }

    @Override
    public String getTitulo() { return "Novo Orçamento"; }

    private GridPane carregaProxPasso(int passo){
        try {
            return FXMLLoader.load(getClass().getResource("/fxml/tela_novo_orcamento_" + passo + ".fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void mudaPasso(int passo){
        this.conteudo.getChildren().add(carregaProxPasso(passo));
    }

}

