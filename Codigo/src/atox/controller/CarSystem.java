package atox.controller;

import atox.Tela;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

//public class CarSystem extends ViewController{
public class CarSystem {
    @FXML
    public AnchorPane paneTitulo;

    @FXML
    public AnchorPane paneConteudo;

    @FXML
    public Label lblTitulo;

    private ViewController viewController;
    public void initialize(){
        viewController = new ViewController();
        viewController.paneTitulo = paneTitulo;
        viewController.paneConteudo = paneConteudo;
        viewController.lblTitulo = lblTitulo;
        inicio();
    }

    @FXML
    private void inicio(){ viewController.carregaTela(Tela.INICIAL);}
    @FXML
    private void novoOrcamento(){ viewController.carregaTela(Tela.NOVO_ORCAMENTO); }
    @FXML
    private void carregaServicos(){ viewController.carregaTela(Tela.INICIAR_ATENDIMENTO); }
    @FXML
    private void carregaFinanceiro(){ viewController.carregaTela(Tela.FINANCEIRO); }
    @FXML
    private void carregaEstoque(){ viewController.carregaTela(Tela.ESTOQUE);}
    @FXML
    private void carregaSelecaoCadastros(){ viewController.carregaTela(Tela.SELECAO_CADASTROS);}
    @FXML
    private void carregaHistoricoOrcamentos(){ viewController.carregaTela(Tela.HISTORICO_ORCAMENTOS);}
    @FXML
    private void carregaHistoricoServicos(){ viewController.carregaTela(Tela.HISTORICO_ATENDIMENTOS);}
    @FXML
    private void carregaCadastroCliente(){ viewController.carregaTela(Tela.CADASTRO_CLIENTE);}
    @FXML
    private void carregaCadastroFornecedor(){ viewController.carregaTela(Tela.CADASTRO_FORNECEDOR);}
    @FXML
    private void carregaCadastroVeiculo(){ viewController.carregaTela(Tela.CADASTRO_VEICULO);}
    @FXML
    private void carregaCadastroServico(){ viewController.carregaTela(Tela.CADASTRO_SERVICO);}
    @FXML
    private void carregaFornecedorCadastrado(){ viewController.carregaTela(Tela.FORNECEDOR_CADASTRADO);}

}
