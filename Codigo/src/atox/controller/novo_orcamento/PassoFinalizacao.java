package atox.controller.novo_orcamento;

import atox.BancoDeDados;
import atox.exception.CarSystemException;
import atox.model.Cliente;
import atox.model.Peca;
import atox.model.Servico;
import atox.model.Veiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PassoFinalizacao extends Passos {

    public Label totalPecas, totalMaoDeObra;

    public ChoiceBox<String> cbFormasPag;
    public CheckBox chkEnviarOrcamentoEmail;

    private Cliente cliente;
    private Veiculo veiculo;
    private List<PecaUtilizada> pecas;
    private List<ServicoEscolhido> servicos;

    private double totalMDOVal = 0.0;
    private double totalPecasVal = 0.0;

    PassoFinalizacao(AnchorPane pane){
        super(pane);
    }

    @Override
    public boolean passoValido() {
        return false;
    }

    @Override
    public void carregarElementos() {
        totalMaoDeObra = (Label) container.lookup("#totalMaoDeObra");
        totalPecas = (Label) container.lookup("#totalPecas");
        cbFormasPag = (ChoiceBox) container.lookup("#cbFormasPag");
        chkEnviarOrcamentoEmail = (CheckBox) container.lookup("#chkEnviarOrcamentoEmail");

        ObservableList<String> formasPag = FXCollections.observableArrayList(
                "A vista", "Crédito", "Débito"
        );
        cbFormasPag.setItems(formasPag);
        cbFormasPag.setValue(formasPag.get(0));

    }

    public void setDados(Cliente dadosCliente, Veiculo dadosVeiculo, List<PecaUtilizada> pecas, List<ServicoEscolhido> svcs){
        this.cliente = dadosCliente;
        this.veiculo = dadosVeiculo;
        this.pecas = pecas;
        this.servicos = svcs;




        for(ServicoEscolhido svc: servicos)
            totalMDOVal += svc.getMaoDeObra();
        totalMaoDeObra.setText("R$"+ Double.toString(totalMDOVal));

        for(PecaUtilizada peca: pecas)
            totalPecasVal += peca.getValTotal();
        totalPecas.setText("R$"+ Double.toString(totalPecasVal));

    }

    public void cadastrarOrcamento(){

        try {
            Statement st = BancoDeDados.getNewStatement();
            for(PecaUtilizada pc: pecas) {
                st.execute("INSERT into atendimento_peca (cod_peca, quantidade) VALUES ("+pc.getId()+", "+pc.getQtd()+")");
            }

            for(ServicoEscolhido svc: servicos)
                st.execute("INSERT INTO atendimento_servico (cod_servico) VALUES ("+svc.getId()+ ")");

            st.execute("INSERT INTO orcamento (cod_veiculo, preco) VALUES ("+veiculo.getId()+", "+ totalMDOVal + totalPecasVal+ ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finalizarOrcamento(){
        if(chkEnviarOrcamentoEmail.isSelected()){
            // Enviar por email
        }

        cadastrarOrcamento();

    }


}
