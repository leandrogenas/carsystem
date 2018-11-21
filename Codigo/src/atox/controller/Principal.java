package atox.controller;

import atox.Telas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CarSystem {

    private Telas telas;

    public CarSystem(Telas t){
        telas = t;
    }

    public void inicio               (){ telas.muda(Telas.Tipo.INICIAL); }
    public void novoOrcamento        (){ telas.muda(Telas.Tipo.NOVO_ORCAMENTO); }
    public void atendimentos         (){ telas.muda(Telas.Tipo.INICIAR_ATENDIMENTO); }
    public void financeiro           (){ telas.muda(Telas.Tipo.FINANCEIRO); }
    public void estoque              (){ telas.muda(Telas.Tipo.ESTOQUE); }
    public void selecaoCadastros     (){ telas.muda(Telas.Tipo.SELECAO_CADASTROS); }
    public void historicoOrcamentos  (){ telas.muda(Telas.Tipo.HISTORICO_ORCAMENTOS); }
    public void historicoAtendimentos(){ telas.muda(Telas.Tipo.HISTORICO_ATENDIMENTOS); }
    public void cadastroCliente      (){ telas.muda(Telas.Tipo.CADASTRO_CLIENTE); }
    public void cadastroFornecedor   (){ telas.muda(Telas.Tipo.CADASTRO_FORNECEDOR); }
    public void cadastroVeiculo      (){ telas.muda(Telas.Tipo.CADASTRO_VEICULO); }
    public void cadastroServico      (){ telas.muda(Telas.Tipo.CADASTRO_SERVICO);}
}
