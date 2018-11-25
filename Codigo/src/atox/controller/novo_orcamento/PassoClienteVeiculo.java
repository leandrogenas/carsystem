package atox.controller.novo_orcamento;

import atox.exception.CarSystemException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PassoClienteVeiculo extends Passos {


    private TextField docCliente;
    private Button okDocumento;
    private Pane dadosCliente;
    private Pane dadosVeiculo;
    private ChoiceBox<String> tpDocCliente;

    PassoClienteVeiculo(AnchorPane pane){
        super(pane);
    }

    @Override
    public void validarPasso() throws CarSystemException {

    }

    private void validarDocumento(){

    }

    @Override
    public void carregar(){
        docCliente = (TextField) this.container.lookup("#docCliente");

        okDocumento = (Button) this.container.lookup("#okDoc");
        okDocumento.setOnAction(event -> validarDocumento());

        tpDocCliente = (ChoiceBox) this.container.lookup("#tpDocCliente");
        tpDocCliente.getItems().setAll("CPF", "CNPJ");
        tpDocCliente.show();
        tpDocCliente.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                docCliente.setDisable(false);
                okDocumento.setDisable(false);
            }
        });

        dadosCliente = (Pane) this.container.lookup("#paneDadosCliente");
        dadosVeiculo = (Pane) this.container.lookup("#paneDadosVeiculo");
    }

}
