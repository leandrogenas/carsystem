package atox.controller;

import atox.Tela;
import javafx.fxml.FXML;

public class SelecaoCadastro {
    private ViewController viewController;
    
    @FXML
    private void carregaCadastroCliente(){ viewController.carregaTela(Tela.CADASTRO_CLIENTE);}
    @FXML
    private void carregaCadastroVeiculo(){ viewController.carregaTela(Tela.CADASTRO_VEICULO); }
    @FXML
    private void carregaCadastroFornecedor(){ viewController.carregaTela(Tela.CADASTRO_FORNECEDOR); }
    @FXML
    private void carregaCadastroServico(){ viewController.carregaTela(Tela.CADASTRO_SERVICO); }
}
