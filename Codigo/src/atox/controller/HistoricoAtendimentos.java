package atox.controller;
import atox.model.Atendimento;
import atox.utils.TableWithCustomRow;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import java.util.List;
import static atox.utils.TableWithCustomRow.column;

import atox.model.Atendimento;
import atox.utils.TableWithCustomRow;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.List;

import static atox.utils.TableWithCustomRow.column;

public class HistoricoAtendimentos {
    @FXML
    private TableView<Atendimento> tabHistAtendimento;

    public void initialize() {
        List<Atendimento> Atendimentos = Atendimento.buscarTodos();
        TableWithCustomRow.createTable(tabHistAtendimento, "Atendimento");
        tabHistAtendimento.getItems().addAll(Atendimentos);
        tabHistAtendimento.getColumns().add(column(Atendimento.codigoTitle(), Atendimento::codigoProperty));
        tabHistAtendimento.getColumns().add(column(Atendimento.orcamentoTitle(), Atendimento::orcamentoProperty));
        tabHistAtendimento.getColumns().add(column(Atendimento.faseTitle(), Atendimento::faseProperty));
        tabHistAtendimento.getColumns().add(column(Atendimento.inicioTitle(), Atendimento::inicioProperty));
        tabHistAtendimento.getColumns().add(column(Atendimento.terminoTitle(), Atendimento::terminoProperty));
    }
}
