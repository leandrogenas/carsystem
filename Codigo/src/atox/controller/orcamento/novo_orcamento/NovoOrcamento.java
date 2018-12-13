package atox.controller.orcamento.novo_orcamento;

import atox.CarSystem;
import atox.exception.CarSystemException;
import atox.model.*;
import atox.controller.orcamento.novo_orcamento.passos.*;
import atox.utils.EnvioEmail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    private Orcamento orcamento;

    @FXML
    private void initialize(){
        passoCV = new PassoClienteVeiculo(this, (AnchorPane) container.lookup("#passoCV"));
        passoPecas = new PassoPecas(this, (AnchorPane) container.lookup("#passoPecas"));
        passoServicos = new PassoServicos(this, (AnchorPane) container.lookup("#passoServicos"));
        passoFinalizacao = new PassoFinalizacao(this, (AnchorPane) container.lookup("#passoFinalizacao"));

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

        scroll((praFrente) ? ++numPasso : --numPasso);
        passoAtual.setVisible(true);
        passoAtual.exibir();

    }

    private void scroll(int passo){
        container.setLayoutY(-530 * (passo-1));
    }

    public void cadastrarOrcamento(){
        try {
            double precoTotal = passoFinalizacao.getTotalMDO() + passoFinalizacao.getTotalPecas();

            Pagamento pag = Pagamento.inserir(new Pagamento(
                    passoFinalizacao.getFormaPag(),
                    passoFinalizacao.getNumParcelas()
            ));

            if(pag.getId() == 0)
                throw new CarSystemException("Erro ao gravar o pagamento no BD");


            orcamento = Orcamento.inserir(new Orcamento(
                    getDadosVeiculo(),
                    getDadosCliente(),
                    pag,
                    precoTotal,
                    passoFinalizacao.getSeguradora()
            ), precoTotal);

            if(orcamento.getId() == 0)
                throw new CarSystemException("Erro ao gravar o orçamento no BD");

            // Inclui as peças utilizadas
            List<PecaUtilizada> pecas = getPecas();
            if(pecas.isEmpty())
                throw new CarSystemException("Não há peças no orçamento!");

            for(PecaUtilizada pc: pecas) {
                OrcamentoPeca orcPeca = OrcamentoPeca.inserir(orcamento, pc.getId(), pc.getQtd());
                orcamento.addPeca(orcPeca);
            }

            // Inclui os serviços escolhidos
            List<ServicoEscolhido> svcs = getServicos();
            if(svcs.isEmpty()) {
                throw new CarSystemException("Não há serviços a serem feitos!");
            }

            for(ServicoEscolhido svc: svcs)
                orcamento.addServico(OrcamentoServico.inserir(orcamento, svc.getId(), svc.getMaoDeObra()));

            // Dá baixa no estoque
            Orcamento.baixaEstoque(orcamento);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText(null);
            alert.setContentText("Orçamento cadastrado com sucesso! Inicie o pagamento em 'Atendimentos'");
            alert.showAndWait();

            CarSystem.getInstancia().mudaTela(CarSystem.Tipo.ATENDIMENTOS);
        } catch (CarSystemException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao inserir o orçamento no BD: " + e.getMessage());
            alert.showAndWait();

        }
    }

    public void finalizarOrcamento(){
        if(!(passoAtual instanceof PassoFinalizacao))
            return;

        cadastrarOrcamento();

        if(passoFinalizacao.enviaPorEmail())
            EnvioEmail.enviarOrcamento(orcamento, "leandragem96@gmail.com");

    }

    public Cliente getDadosCliente(){
        return passoCV.getDadosCliente();
    }
    public Veiculo getDadosVeiculo(){
        return passoCV.getDadosVeiculo();
    }
    public List<PecaUtilizada> getPecas(){
        return passoPecas.getPecas();
    }
    public List<ServicoEscolhido> getServicos(){
        return passoServicos.getServicos();
    }


}
