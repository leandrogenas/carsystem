package atox.controller.orcamento.novo_orcamento.passos;

import atox.exception.CarSystemException;
import atox.model.Servico;
import atox.controller.orcamento.novo_orcamento.NovoOrcamento;
import atox.controller.orcamento.novo_orcamento.ServicoEscolhido;
import atox.utils.MaskFieldUtil;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class PassoServicos extends Passos {

    private ScrollPane paneServicos;
    private Label lblNenhumServico;
    private ChoiceBox<String> cbServicos;
    private TextField custoServico;
    private Button adcServico;

    private List<ServicoEscolhido> servicos = new ArrayList<>();

    public PassoServicos(NovoOrcamento contr, AnchorPane pane){
        super(contr, pane);
    }


    @Override
    public boolean passoValido(){
        try{
            if(lblNenhumServico.isVisible())
                throw new CarSystemException("Selecione pelo menos um serviço!");

            return true;
        }catch (CarSystemException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro!");
            alerta.setHeaderText(null);
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();

            return false;
        }
    }

    @Override
    public void carregarElementos() {
        paneServicos = (ScrollPane) container.lookup("#paneServicos");
        lblNenhumServico = (Label) ((AnchorPane) paneServicos.getContent()).lookup("#lblNenhumServico");;
        custoServico = (TextField) container.lookup("#custoServico");
        adcServico = (Button) container.lookup("#adcServico");
        cbServicos = (ChoiceBox) container.lookup("#cbServicos");

        adcServico.setOnAction(ev -> adcServico());

        for(Servico svc: Servico.todos())
            cbServicos.getItems().add(String.format("%03d", svc.getId()) + "-" + svc.getNome());

        //cbServicos.setValue(cbServicos.getItems().get(0));

        MaskFieldUtil.valorMask(custoServico);
    }

    @Override
    public void exibir() {

    }

    private void adcServico(){
        lblNenhumServico.setVisible(false);

        String strSvc = cbServicos.getValue();

        Servico svcSel = Servico.buscaPorId(Integer.parseInt(strSvc.substring(0, 3)));
        try {
            if (svcSel == null)
                throw new CarSystemException("Erro ao encontrar o serviço");


            String strCustoSvc = custoServico.getText();
            strCustoSvc = strCustoSvc.replace(".", "").replace(",", ".");

            servicos.add(new ServicoEscolhido(svcSel.getId(), svcSel.getNome(), Double.parseDouble(strCustoSvc)));

            Label lblPeca = new Label("- " + svcSel.getNome());
            lblPeca.setLayoutY((servicos.size() - 1) * 20);

            ((AnchorPane) paneServicos.getContent()).getChildren().add(lblPeca);
        }catch (CarSystemException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro!");
            alerta.setHeaderText(null);
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();

        }
    }

    public List<ServicoEscolhido> getServicos(){
        return servicos;
    }

}
