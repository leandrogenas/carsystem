package atox.controller;

import atox.Tela;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
    public void inicio               (){ viewController.carregaTela(Tela.INICIO); }
    public void novoOrcamento        (){ viewController.carregaTela(Tela.NOVO_ORCAMENTO); }
    public void atendimentos         (){ viewController.carregaTela(Tela.INICIAR_ATENDIMENTO); }
    public void financeiro           (){ viewController.carregaTela(Tela.FINANCEIRO); }
    public void estoque              (){ viewController.carregaTela(Tela.ESTOQUE); }
    public void selecaoCadastros     (){ viewController.carregaTela(Tela.SELECAO_CADASTROS); }
    public void historicoOrcamentos  (){ viewController.carregaTela(Tela.HISTORICO_ORCAMENTOS); }
    public void historicoAtendimentos(){ viewController.carregaTela(Tela.HISTORICO_ATENDIMENTOS); }
    public void cadastroCliente      (){ viewController.carregaTela(Tela.CADASTRO_CLIENTE); }
    public void cadastroFornecedor   (){ viewController.carregaTela(Tela.CADASTRO_FORNECEDOR); }
    public void cadastroVeiculo      (){ viewController.carregaTela(Tela.CADASTRO_VEICULO); }
    public void cadastroServico      (){ viewController.carregaTela(Tela.CADASTRO_SERVICO);}
}
