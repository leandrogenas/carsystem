package atox.controller;

import atox.utils.MaskFieldUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static atox.utils.Validators.isCPF;

public class NovoOrcamento {
    @FXML
    private VBox container;
    @FXML
    private AnchorPane passoClienteVeiculo;
    @FXML
    private AnchorPane passoPecas;
    @FXML
    private AnchorPane passoServicos;
    @FXML
    private AnchorPane passoPagamento;
    @FXML
    private TextField cpfField;

    @FXML
    private void initialize(){
        passoClienteVeiculo.setVisible(true);
        passoPecas.setVisible(false);
        passoServicos.setVisible(false);
        passoPagamento.setVisible(false);

        MaskFieldUtil.cpfMask(cpfField);
    }

    @FXML
    private void paraPecas(){
        if(verificaCliente() && verificaVeiculo()) {
            passoClienteVeiculo.setVisible(false);
            scroll(1);
            passoPecas.setVisible(true);
        }else{
            mensagem("Preencha os dados do cliente e veículo", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void voltaClienteVeiculo(){
        passoPecas.setVisible(false);
        scroll(0);
        passoClienteVeiculo.setVisible(true);
    }

    @FXML
    private void paraServicos(){
        passoPecas.setVisible(false);
        scroll(2);
        passoServicos.setVisible(true);
    }

    @FXML
    private void voltaPecas(){
        passoServicos.setVisible(false);
        scroll(1);
        passoPecas.setVisible(true);
    }


    @FXML
    private void paraPagamento(){
        passoServicos.setVisible(false);
        scroll(3);
        passoPagamento.setVisible(true);
    }

    @FXML
    private void voltaServicos(){
        passoPagamento.setVisible(false);
        scroll(2);
        passoServicos.setVisible(true);
    }

    @FXML
    private void finalizarOrcamento(){

    }

    private void scroll(int passo){
        container.setLayoutY(-530 * passo);
    }

    private boolean verificaCliente(){
        return true;
    }

    private boolean verificaVeiculo(){
        return true;
    }

    private void mensagem(String msg, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Novo Orçamento");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public void validaCliente() {
        if(!isCPF(cpfField.getText())) {
            return;
        }
    }
}
