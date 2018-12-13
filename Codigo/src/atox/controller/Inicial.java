package atox.controller;

import atox.BancoDeDados;
import atox.model.Atendimento;
import atox.model.Orcamento;
import atox.model.OrcamentoServico;
import atox.model.Veiculo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class Inicial {

    @FXML
    private Pane panePrincipal;

    public TableView<Atendimento> tblSvcs;
    public TableColumn<Atendimento, String> colId, colDtIni, colVeiculo, colEstagio;

    public ObservableList<Atendimento> atts;

    public void initialize(){
        atts = FXCollections.observableArrayList(Atendimento.todosIniciados());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDtIni.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        colVeiculo.setCellValueFactory(param -> {
            Veiculo veic = param.getValue().getOrcamento().getVeiculo();
            return new SimpleStringProperty(veic.getMarca() + " " + veic.getCor() + ", modelo " + veic.getModelo());
        });
        colEstagio.setCellValueFactory(param -> {
            int cConcl = 0, cRest = 0;
            for (OrcamentoServico orcSvc : param.getValue().getOrcamento().getServicos()){
                if (orcSvc.estaFinalizado())
                    cConcl++;
                else
                    cRest++;
            }
            return new SimpleStringProperty(cConcl + " de " + cRest);
        });

        tblSvcs.setItems(atts);
    }

    @FXML
    public void novoOrcamento(){
        Scene principal = (Scene) panePrincipal.getParent().getScene();
        ((SplitMenuButton) principal.lookup("#btnOrcamento")).fire();

    }

}
