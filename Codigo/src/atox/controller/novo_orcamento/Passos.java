package atox.controller.novo_orcamento;

import atox.exception.CarSystemException;
import javafx.scene.layout.AnchorPane;

public abstract class Passos {

    protected AnchorPane container;

    Passos(AnchorPane pane){
        this.container = pane;
        setVisible(false);
        carregar();
    }

    public void setVisible(boolean visible){
        container.setVisible(visible);
    }

    public abstract void validarPasso() throws CarSystemException;
    public abstract void carregar();


}
