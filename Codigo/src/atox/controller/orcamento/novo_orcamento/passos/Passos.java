package atox.controller.orcamento.novo_orcamento.passos;

import atox.controller.orcamento.novo_orcamento.NovoOrcamento;
import javafx.scene.layout.AnchorPane;

public abstract class Passos {

    protected AnchorPane container;
    protected NovoOrcamento contrNovoOrcamento;

    Passos(NovoOrcamento contr, AnchorPane pane){
        this.contrNovoOrcamento = contr;
        this.container = pane;
        setVisible(false);
        carregarElementos();
    }

    public void setVisible(boolean visible){
        container.setVisible(visible);
    }

    public abstract boolean passoValido();
    public abstract void carregarElementos();
    public abstract void exibir();
}
