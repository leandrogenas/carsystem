package atox.controller.novo_orcamento;

import atox.exception.CarSystemException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;

public class NovoOrcamento {
    @FXML
    private VBox container;

    private int numPasso;
    private Passos passoAtual;

    private PassoClienteVeiculo passoCV;
    private PassoPecas passoPecas;
    private PassoServicos passoServicos;
    private PassoFinalizacao passoFinalizacao;



    @FXML
    private void initialize(){
        passoCV = new PassoClienteVeiculo((AnchorPane) container.lookup("#passoCV"));
        passoPecas = new PassoPecas((AnchorPane) container.lookup("#passoPecas"));
        passoServicos = new PassoServicos((AnchorPane) container.lookup("#passoServicos"));
        passoFinalizacao = new PassoFinalizacao((AnchorPane) container.lookup("#passoFinalizacao"));

        numPasso = 1;
        passoAtual = passoCV;
        passoAtual.setVisible(true);
    }

    public void proximo(ActionEvent ev){ navegacao(true); }
    public void anterior(ActionEvent ev){ navegacao(false); }

    private void navegacao(boolean praFrente){
        if(praFrente)
            if(!passoAtual.passoValido())
                return;

        passoAtual.setVisible(false);

        switch (numPasso) {
            case 1: passoAtual = passoPecas; break;
            case 2: passoAtual = (praFrente) ? passoServicos : passoCV; break;
            case 3: passoAtual = (praFrente) ? passoFinalizacao : passoPecas; break;
            case 4: passoAtual = passoServicos;
        }

        if(passoAtual instanceof PassoFinalizacao)
            ((PassoFinalizacao) passoAtual).setDados(
                passoCV.getDadosCliente(),
                passoCV.getDadosVeiculo(),
                passoPecas.getPecas(),
                passoServicos.getServicos()
            );

        scroll((praFrente) ? ++numPasso : --numPasso);
        passoAtual.setVisible(true);

    }

    public void finalizarOrcamento(){
        passoFinalizacao.finalizarOrcamento();
    }

    private void scroll(int passo){
        container.setLayoutY(-530 * (passo-1));
    }

    private static void mensagem(String msg, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Novo Orçamento");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }


}
