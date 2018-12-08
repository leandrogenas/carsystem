package atox.controller.orcamento.novo_orcamento.passos;

import atox.exception.CarSystemException;
import atox.model.Peca;
import atox.controller.orcamento.novo_orcamento.NovoOrcamento;
import atox.controller.orcamento.novo_orcamento.PecaUtilizada;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class PassoPecas extends Passos{
    private ScrollPane panePecas;
    private Label lblNenhumaPeca;

    private ChoiceBox<String> cbPecas;
    private TextField qtdPeca;
    private Button btnAdcPeca;

    private Label lblEstoqueNegativo;

    private List<PecaUtilizada> pecasUtilizadas = new ArrayList<>();

    PassoPecas(NovoOrcamento contr, AnchorPane pane){
        super(contr, pane);
    }

    @Override
    public boolean passoValido(){
        return true;
    }

    @Override
    public void carregarElementos(){
        panePecas = (ScrollPane) container.lookup("#panePecas");
        lblNenhumaPeca = (Label) ((AnchorPane) panePecas.getContent()).lookup("#lblNenhumaPeca");

        cbPecas = (ChoiceBox) container.lookup("#cbPecas");
        qtdPeca = (TextField) container.lookup("#qtdPeca");
        lblEstoqueNegativo = (Label) container.lookup("#lblEstoqueNegativo");

        btnAdcPeca = (Button) container.lookup("#btnAdcPeca");
        btnAdcPeca.setOnAction(ev -> adcPeca());

        for(Peca peca: Peca.todos())
            cbPecas.getItems().add(String.format("%03d", peca.getId()) + "-" + peca.getNome() + " (" + peca.getModelo() + ")");

        //cbPecas.setValue(cbPecas.getItems().get(0));

        lblEstoqueNegativo.setVisible(false);
    }

    @Override
    public void exibir() {

    }

    private void adcPeca() {
        lblNenhumaPeca.setVisible(false);

        String strPeca = cbPecas.getValue();

        Peca pecaSel = Peca.buscaPorId(Integer.parseInt(strPeca.substring(0, 3)));
        try {
            int qtdPecas = Integer.parseInt(qtdPeca.getText());

            if(qtdPecas == 0)
                throw new CarSystemException("Informe uma quantidade!");
            if (pecaSel == null)
                throw new CarSystemException("Erro ao encontrar a peça");


            pecasUtilizadas.add(new PecaUtilizada(pecaSel.getId(), pecaSel.getNome(), qtdPecas, pecaSel.getValUnit()));

            Label lblPeca = new Label("- " + pecaSel.getNome() + " (" + pecaSel.getModelo() + "), " + qtdPecas + " unidades");
            lblPeca.setLayoutY((pecasUtilizadas.size() - 1) * 20);

            ((AnchorPane) panePecas.getContent()).getChildren().add(lblPeca);
        }catch (CarSystemException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro!");
            alerta.setHeaderText(null);
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();

        }

    }

    public List<PecaUtilizada> getPecas(){
        return pecasUtilizadas;
    }

}
