package atox.controller;

import atox.CarSystem;

public class Principal {

    private CarSystem cs;

    public Principal(CarSystem cs){
        this.cs = cs;
    }

    public void inicio               (){ cs.mudaTela(atox.CarSystem.Tipo.INICIAL); }
    public void novoOrcamento        (){ cs.mudaTela(atox.CarSystem.Tipo.NOVO_ORCAMENTO); }
    public void atendimentos         (){ cs.mudaTela(atox.CarSystem.Tipo.ATENDIMENTOS); }
    public void financeiro           (){ cs.mudaTela(atox.CarSystem.Tipo.FINANCEIRO); }
    public void estoque              (){ cs.mudaTela(atox.CarSystem.Tipo.ESTOQUE); }
    public void selecaoCadastros     (){ cs.mudaTela(atox.CarSystem.Tipo.SELECAO_CADASTROS); }
    public void historicoOrcamentos  (){ cs.mudaTela(atox.CarSystem.Tipo.HISTORICO_ORCAMENTOS); }
    public void historicoAtendimentos(){ cs.mudaTela(atox.CarSystem.Tipo.HISTORICO_ATENDIMENTOS); }
    public void cadastroCliente      (){ cs.mudaTela(atox.CarSystem.Tipo.CADASTRO_CLIENTE); }
    public void cadastroFornecedor   (){ cs.mudaTela(atox.CarSystem.Tipo.CADASTRO_FORNECEDOR); }
    public void cadastroVeiculo      (){ cs.mudaTela(atox.CarSystem.Tipo.CADASTRO_VEICULO); }
    public void cadastroServico      (){ cs.mudaTela(atox.CarSystem.Tipo.CADASTRO_SERVICO);}
}
