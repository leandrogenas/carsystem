package atox.controller.novo_orcamento;

import atox.BancoDeDados;
import atox.exception.CarSystemException;
import atox.model.*;
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
    public TextField numParcelas, nomeSeguradora;

    public ChoiceBox<String> cbFormasPag;
    public CheckBox chkEnviarOrcamentoEmail;

    private double totalMDOVal = 0.0;
    private double totalPecasVal = 0.0;

    PassoFinalizacao(NovoOrcamento contr, AnchorPane pane){
        super(contr, pane);
    }

    @Override
    public boolean passoValido() {
        return false;
    }

    @Override
    public void carregarElementos() {
        numParcelas = (TextField) container.lookup("#numParcelas");
        nomeSeguradora = (TextField) container.lookup("#nomeSeguradora");
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

    @Override
    public void exibir() {
        totalMDOVal = totalPecasVal = 0;

        for(ServicoEscolhido svc: contrNovoOrcamento.getServicos())
            totalMDOVal += svc.getMaoDeObra();
        totalMaoDeObra.setText("R$"+ totalMDOVal);

        for(PecaUtilizada peca: contrNovoOrcamento.getPecas())
            totalPecasVal += peca.getValTotal();
        totalPecas.setText("R$"+ totalPecasVal);

    }

    public double getTotalMDO(){ return totalMDOVal; }
    public double getTotalPecas(){ return totalPecasVal; }
    public boolean enviaPorEmail(){ return chkEnviarOrcamentoEmail.isSelected(); }
    public String getFormaPag(){ return cbFormasPag.getValue(); }
    public String getSeguradora(){ return nomeSeguradora.getText(); }
    public int getNumParcelas(){
        if(numParcelas.getText().isEmpty())
            return 1;

        return Integer.valueOf(numParcelas.getText());
    }


}
