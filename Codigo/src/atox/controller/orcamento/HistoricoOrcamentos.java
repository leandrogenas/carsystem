package atox.controller.orcamento;

import atox.model.Cliente;
import atox.model.Orcamento;
import atox.model.Veiculo;
import atox.utils.TableWithCustomRow;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

import static atox.utils.TableWithCustomRow.column;

public class HistoricoOrcamentos {
    @FXML
    private TableView<Orcamento> tabHistOrcamentos;

    public void initialize() {
        List<Orcamento> orcamentos = Orcamento.todos();
        TableWithCustomRow.createTable(tabHistOrcamentos, "Orçamentos: ");
        tabHistOrcamentos.getItems().addAll(orcamentos);

        tabHistOrcamentos.getColumns().add(column(Orcamento.codigoTitle(), Orcamento::idProperty));

        TableColumn<Orcamento, String> colVeiculo = column(Orcamento.veiculoTitle(), null);
        colVeiculo.setCellValueFactory(param -> {
            Veiculo veic = param.getValue().getVeiculo();
            return new SimpleStringProperty(veic.getMarca() + " " + veic.getCor() + ", modelo " + veic.getModelo());
        });

        TableColumn<Orcamento, String> colCliente = column(Orcamento.clienteTitle(), null);
        colCliente.setCellValueFactory(param -> {
            Cliente cli = param.getValue().getCliente();
            return new SimpleStringProperty(cli.getNome() + ", doc: " + cli.getDocumento());
        });
        tabHistOrcamentos.getColumns().add(column(Orcamento.inicioTitle(), Orcamento::inicioProperty));
        tabHistOrcamentos.getColumns().add(column(Orcamento.precoTitle(), Orcamento::precoProperty));
        tabHistOrcamentos.getColumns().add(column(Orcamento.seguradoraTitle(), Orcamento::seguradoraProperty));
        tabHistOrcamentos.getColumns().add(column(Orcamento.statusTitle(), Orcamento::statusProperty));
    }
}
