package atox.controller.novo_orcamento;

import atox.BancoDeDados;
import atox.exception.CarSystemException;
import atox.model.Cliente;
import atox.model.Peca;
import atox.model.Servico;
import atox.model.Veiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.xml.soap.Text;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PassoFinalizacao extends Passos {

    public Label totalPecas, totalMaoDeObra;
    public TextField numParcelas;

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
        numParcelas = (TextField) container.lookup("#numParcelas");
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
            ResultSet rSet;
            st.execute("INSERT INTO pagamento (forma_pagamento, num_parcelas, pago) VALUES ('"+cbFormasPag.getValue()+"',"+Integer.valueOf(numParcelas.getText())+ ", 0)");

            rSet = st.executeQuery("SELECT max(cod_pagamento) AS cod_pagamento FROM pagamento");
            rSet.next();
            int cod_pagamento = rSet.getInt("cod_pagamento");

            double precoTotal = totalMDOVal + totalPecasVal;
            st.execute("INSERT INTO orcamento (cod_veiculo, preco, seguradora, cod_pagamento) VALUES ("+veiculo.getId()+", "+ precoTotal+",'',"+ cod_pagamento+")");

            rSet = st.executeQuery("SELECT max(cod_orcamento) AS cod_orcamento FROM orcamento");
            rSet.next();
            int cod_orcamento = rSet.getInt("cod_orcamento");

            for(PecaUtilizada pc: pecas) {
                st.execute("INSERT into orcamento_peca (cod_orcamento, cod_peca, quantidade) VALUES ("+cod_orcamento+","+pc.getId()+", "+pc.getQtd()+")");
            }

            for(ServicoEscolhido svc: servicos)
                st.execute("INSERT INTO orcamento_servico (cod_servico) VALUES ("+svc.getId()+ ")");

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
