package atox.controller.novo_orcamento;

import atox.exception.CarSystemException;
import atox.model.Peca;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.List;

public class PassoPecas extends Passos{

    private TableView<Peca> tblPecas;
    private TableColumn<Peca, String> colNomeCodigo, colQtd, colValUnit, colValTotal;
    private ChoiceBox<String> cbPecas;
    private Spinner<Integer> spinQtd;
    private Button btnAdcPeca;
    private Label lblEstoqueNegativo;

    private ObservableList<Peca> pecasUtilizadas;

    PassoPecas(AnchorPane pane){
        super(pane);
    }

    @Override
    public boolean passoValido(){
        return false;
    }

    private void adcPeca() {
        String strPeca = cbPecas.getValue();

        Peca pecaSel = Peca.buscaPorId(Integer.parseInt(strPeca.substring(0, 3)));

        try {
            if (pecaSel == null)
                throw new CarSystemException("Erro ao encontrar a peça");

            pecasUtilizadas.add(pecaSel);
        }catch (CarSystemException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro!");
            alerta.setHeaderText(null);
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();

        }

    }

    @Override
    public void carregarElementos(){
        pecasUtilizadas = FXCollections.observableArrayList();

        tblPecas = (TableView) container.lookup("#tblPecas");
        colNomeCodigo = (TableColumn<Peca, String>) tblPecas.getColumns().get(0);
        colNomeCodigo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNome() + " / " + param.getValue().getModelo()));

        colQtd = (TableColumn<Peca, String>) tblPecas.getColumns().get(1);
        colQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));

        colValUnit = (TableColumn<Peca, String>) tblPecas.getColumns().get(2);
        colValUnit.setCellValueFactory(new PropertyValueFactory<>(""));

        colValTotal = (TableColumn<Peca, String>) tblPecas.getColumns().get(3);

        cbPecas = (ChoiceBox) container.lookup("#cbPecas");
        spinQtd = (Spinner) container.lookup("#spinQtd");
        btnAdcPeca = (Button) container.lookup("#btnAdcPeca");
        lblEstoqueNegativo = (Label) container.lookup("#lblEstoqueNegativo");

        btnAdcPeca.setOnAction(ev -> adcPeca());

        for(Peca peca: Peca.todos())
            cbPecas.getItems().add(String.format("%03d", peca.getId()) + "-" + peca.getNome() + " (" + peca.getModelo() + ")");

        cbPecas.setValue(cbPecas.getItems().get(0));

        lblEstoqueNegativo.setVisible(false);
    }

}
