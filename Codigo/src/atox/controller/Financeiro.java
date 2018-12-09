package atox.controller;

import atox.model.Financa;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static atox.utils.TableWithCustomRow.column;

public class Financeiro {
    @FXML
    private DatePicker dataInicial, dataFinal;
    @FXML
    private TableView<Financa> tabFinanceiro;
    @FXML
    private Label atendimentosLabel, caixaLabel;
    @FXML
    private Button salvarPdfButton;
    private List<Financa> financas;

    public void initialize() {
        tabFinanceiro.getColumns().add(column(Financa.veiculoTitle(), Financa::veiculoProperty));
        tabFinanceiro.getColumns().add(column(Financa.inicioTitle(), Financa::inicioProperty));
        tabFinanceiro.getColumns().add(column(Financa.terminoTitle(), Financa::terminoProperty));
        tabFinanceiro.getColumns().add(column(Financa.precoTitle(), Financa::precoProperty));
        salvarPdfButton.setDisable(true);

    }

    public void pesquisaFinanceira() {
        List<Financa> financas = Financa.buscarPorPeriodo(dataInicial.getValue(), dataFinal.getValue());
        if (financas.isEmpty() || financas == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pesquisa Vazia");
            alert.setHeaderText(null);
            alert.setContentText("Não foi retornado nenhum registro no intervalo estipulado!");
            alert.showAndWait();
        } else {
            tabFinanceiro.getItems().clear();
            tabFinanceiro.getItems().addAll(financas);
            caixaLabel.setText(String.valueOf(financas.stream().mapToDouble(financa -> financa.getPreco()).sum()));
            atendimentosLabel.setText(String.valueOf(financas.size()));
            this.financas = financas;
            salvarPdfButton.setDisable(false);
        }
    }

    public void salvarPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar PDF");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (final PDDocument document = new PDDocument()) {
                for (Financa financa : financas) {
                    PDPage page = new PDPage();
                    document.addPage(page);
                    PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
                    //Begin the Content stream
                    contentStream.beginText();
                    //Setting the font to the Content stream
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                    //Setting the leading
                    contentStream.setLeading(14.5f);
                    //Setting the position for the line
                    contentStream.newLineAtOffset(25, 750);

                    //Adding text in the form of string
                    String[] financaStrings = financa.toString().split("\n");
                    for (int i = 0; i < financaStrings.length; i++) {
                        contentStream.newLine();
                        contentStream.showText(financaStrings[i]);
                    }

                    //Ending the content stream
                    contentStream.endText();
                    //Closing the content stream
                    contentStream.close();
                }
                document.save(file);
            } catch (IOException ioEx) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erro Ao Salvar Arquivo");
                alert.setHeaderText(null);
                alert.setContentText("Houve um erro ao salvar o arquivo!\nErro: " + ioEx);
                alert.showAndWait();
            }
        }
    }
}
