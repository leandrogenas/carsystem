package atox.controller.novo_orcamento;

import atox.exception.CarSystemException;
import javafx.scene.layout.AnchorPane;

public class PassoFinalizacao extends Passos {

    PassoFinalizacao(AnchorPane pane){
        super(pane);
    }

    @Override
    public boolean passoValido() {
        return false;
    }

    @Override
    public void carregarElementos() {

    }


}
