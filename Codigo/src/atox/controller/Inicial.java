package atox.controller;

import atox.Tela;
import javafx.fxml.FXML;

public class Inicial {
    private ViewController viewController;
    @FXML
    private void novoOrcamento(){ viewController.carregaTela(Tela.NOVO_ORCAMENTO); }

}
