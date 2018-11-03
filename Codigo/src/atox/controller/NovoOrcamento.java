package atox.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private void initialize(){
        passoClienteVeiculo.setVisible(true);
        passoPecas.setVisible(false);
        passoServicos.setVisible(false);
        passoPagamento.setVisible(false);
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

}
