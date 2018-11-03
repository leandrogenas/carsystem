package atox.controller;

import atox.Tela;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CarSystem {

    @FXML
    private AnchorPane paneTitulo;

    @FXML
    private AnchorPane paneConteudo;

    @FXML
    private Label lblTitulo;

    private void carregaTela(Tela tela){
        try {
            Parent conteudo = tela.getFXMLLoader().load();

            lblTitulo.setText(tela.getTitulo());
            paneConteudo.getChildren().clear();
            paneConteudo.getChildren().add(conteudo);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize(){
        inicio();
    }

    @FXML
    private void inicio(){ carregaTela(Tela.INICIAL);}
    @FXML
    private void novoOrcamento(){ carregaTela(Tela.NOVO_ORCAMENTO); }
    @FXML
    private void carregaServicos(){ carregaTela(Tela.INICIAR_ATENDIMENTO); }
    @FXML
    private void carregaFinanceiro(){ carregaTela(Tela.FINANCEIRO); }
    @FXML
    private void carregaEstoque(){ carregaTela(Tela.ESTOQUE);}
    @FXML
    private void carregaSelecaoCadastros(){ carregaTela(Tela.SELECAO_CADASTROS);}
    @FXML
    private void carregaHistoricoOrcamentos(){ carregaTela(Tela.HISTORICO_ORCAMENTOS);}
    @FXML
    private void carregaHistoricoServicos(){ carregaTela(Tela.HISTORICO_ATENDIMENTOS);}
    @FXML
    private void carregaCadastroCliente(){ carregaTela(Tela.CADASTRO_CLIENTE);}
    @FXML
    private void carregaCadastroFornecedor(){ carregaTela(Tela.CADASTRO_FORNECEDOR);}
    @FXML
    private void carregaCadastroVeiculo(){ carregaTela(Tela.CADASTRO_VEICULO);}

}
