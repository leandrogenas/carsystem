package atox.controller;

import atox.model.Orcamento;
import atox.utils.TableWithCustomRow;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.List;

import static atox.utils.TableWithCustomRow.column;

public class HistoricoOrcamentos {
    @FXML
    private TableView<Orcamento> tabHistOrcamentos;

    public void initialize() {
        List<Orcamento> orcamentos = Orcamento.buscarTodos();
        TableWithCustomRow.createTable(tabHistOrcamentos, "Orçamento");
        tabHistOrcamentos.getItems().addAll(orcamentos);
        tabHistOrcamentos.getColumns().add(column(Orcamento.codigoTitle(), Orcamento::codigoProperty));
        tabHistOrcamentos.getColumns().add(column(Orcamento.veiculoTittle(), Orcamento::veiculoProperty));
        tabHistOrcamentos.getColumns().add(column(Orcamento.pagamentoTittle(), Orcamento::pagamentoProperty));
        tabHistOrcamentos.getColumns().add(column(Orcamento.inicioTittle(), Orcamento::inicioProperty));
        tabHistOrcamentos.getColumns().add(column(Orcamento.precoTitle(), Orcamento::precoProperty));
        tabHistOrcamentos.getColumns().add(column(Orcamento.seguradoraTitle(), Orcamento::seguradoraProperty));
        tabHistOrcamentos.getColumns().add(column(Orcamento.statusTitle(), Orcamento::statusProperty));
    }
}
