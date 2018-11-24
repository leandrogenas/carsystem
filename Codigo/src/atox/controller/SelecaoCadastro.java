package atox.controller;

import atox.CarSystem;
import javafx.fxml.FXML;

public class SelecaoCadastro {
    private CarSystem carSystem = CarSystem.getInstancia();
    
    @FXML
    private void carregaCadastroCliente(){ carSystem.mudaTela(CarSystem.Tipo.CADASTRO_CLIENTE);}
    @FXML
    private void carregaCadastroVeiculo(){ carSystem.mudaTela(CarSystem.Tipo.CADASTRO_VEICULO); }
    @FXML
    private void carregaCadastroFornecedor(){ carSystem.mudaTela(CarSystem.Tipo.CADASTRO_FORNECEDOR); }
    @FXML
    private void carregaCadastroServico(){ carSystem.mudaTela(CarSystem.Tipo.CADASTRO_SERVICO); }
}
