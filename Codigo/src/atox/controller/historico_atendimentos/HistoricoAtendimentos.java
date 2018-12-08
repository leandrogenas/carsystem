package atox.controller.historico_atendimentos;
import atox.CarSystem;
import atox.controller.atendimento.DetalhesAtendimento;
import atox.model.Atendimento;
import atox.utils.TableWithCustomRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static atox.utils.TableWithCustomRow.column;


public class HistoricoAtendimentos {


    @FXML
    private TableView<Atendimento> tabHistAtendimento;

    private Map<Integer, AnchorPane> detalhes = new HashMap<>();
    private ObservableList<Atendimento> atts;

    private void carregaLinha(BorderPane pDet, Atendimento item){
        AnchorPane pane = detalhes.get(item.getId());

        pDet.setPrefHeight(170);
        pDet.getChildren().add(pane);
    }

    public void initialize() {
        atts = FXCollections.observableArrayList();

        for(Atendimento at: Atendimento.todos()) {
            try {
                FXMLLoader loaderDetalhes = CarSystem.Tipo.DETALHES_ATENDIMENTO.getFXMLLoader();
                loaderDetalhes.setController(new DetalhesAtendimento(at));
                detalhes.put(at.getId(), loaderDetalhes.load());
                atts.add(at);
            }catch (IOException e){
                System.err.println("Erro ao carregar detalhes do atendimento " + at.getId());
                e.printStackTrace();
            }
        }

        TableWithCustomRow.createTable(tabHistAtendimento, (pane, item) -> carregaLinha(pane, (Atendimento) item));
        tabHistAtendimento.getItems().addAll(atts);
        tabHistAtendimento.getColumns().add(column("Código", Atendimento::idProperty));
        tabHistAtendimento.getColumns().add(column("Cliente", Atendimento::orcamentoProperty));
        tabHistAtendimento.getColumns().add(column("Status", Atendimento::inicioProperty));
    }
}
