package atox.controller;

import atox.model.Financa;
import atox.utils.TableWithCustomRow;
import com.sun.javafx.font.FontFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.swing.text.Document;
import java.io.FileOutputStream;
import java.util.List;

import static atox.utils.TableWithCustomRow.column;

public class Financeiro {
    @FXML
    private DatePicker dataInicial, dataFinal;
    @FXML
    private TableView<Financa> tabFinanceiro;
    @FXML
    private Label atendimentosLabel, caixaLabel;

    public void initialize() {
        tabFinanceiro.getColumns().add(column(Financa.veiculoTitle(), Financa::veiculoProperty));
        tabFinanceiro.getColumns().add(column(Financa.inicioTitle(), Financa::inicioProperty));
        tabFinanceiro.getColumns().add(column(Financa.terminoTitle(), Financa::terminoProperty));
        tabFinanceiro.getColumns().add(column(Financa.precoTitle(), Financa::precoProperty));
    }

    public void pesquisaFinanceira() {
        List<Financa> financas = Financa.buscarPorPeriodo(dataInicial.getValue(), dataFinal.getValue());
        if(financas.isEmpty() || financas == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pesquisa Vazia");
            alert.setHeaderText(null);
            alert.setContentText("Não foi retornado nenhum registro no intervalo estipulado!");
            alert.showAndWait();
        } else {
            System.out.println(financas);
            tabFinanceiro.getItems().addAll(financas);
            caixaLabel.setText(String.valueOf(financas.stream().mapToDouble(financa -> financa.getPreco()).sum()));
            atendimentosLabel.setText(String.valueOf(financas.size()));
        }
    }
    public void salvarPdf() {
        try {

            PDDocument document = new PDDocument();

            for (int i = 0; i < 2; i++) {
                //Creating a blank page
                PDPage blankPage = new PDPage();

                //Adding the blank page to the document
                document.addPage(blankPage);
            }

            //Saving the document
            document.save("C:/temp/my_doc.pdf");
            System.out.println("PDF created");

            //Closing the document
            document.close();

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro Ao Salvar Arquivo");
            alert.setHeaderText(null);
            alert.setContentText("Houve um erro ao salvar o arquivo!\nErro: "+ex.getMessage());
            alert.showAndWait();
        }
    }

}
