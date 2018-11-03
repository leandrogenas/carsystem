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

    public void inicio               (){ carregaTela(Tela.INICIO); }
    public void novoOrcamento        (){ carregaTela(Tela.NOVO_ORCAMENTO); }
    public void atendimentos         (){ carregaTela(Tela.INICIAR_ATENDIMENTO); }
    public void financeiro           (){ carregaTela(Tela.FINANCEIRO); }
    public void estoque              (){ carregaTela(Tela.ESTOQUE); }
    public void selecaoCadastros     (){ carregaTela(Tela.SELECAO_CADASTROS); }
    public void historicoOrcamentos  (){ carregaTela(Tela.HISTORICO_ORCAMENTOS); }
    public void historicoAtendimentos(){ carregaTela(Tela.HISTORICO_ATENDIMENTOS); }
    public void cadastroCliente      (){ carregaTela(Tela.CADASTRO_CLIENTE); }
    public void cadastroFornecedor   (){ carregaTela(Tela.CADASTRO_FORNECEDOR); }
    public void cadastroVeiculo      (){ carregaTela(Tela.CADASTRO_VEICULO); }

}
