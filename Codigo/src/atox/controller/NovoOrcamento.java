package atox.controller;

import atox.exception.CarSystemException;
import atox.model.Cliente;
import atox.model.Documento;
import atox.utils.MaskFieldUtil;
import atox.utils.Mock;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import static atox.utils.Validators.isCNPJ;
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
    private TextField docCliente;
    @FXML
    private ChoiceBox<String> tpDocCliente;
    @FXML
    private Button okDocumento;
    @FXML
    private TextField nomeCliente;


    @FXML
    private void initialize(){
        passoClienteVeiculo.setVisible(true);
        passoPecas.setVisible(false);
        passoServicos.setVisible(false);
        passoPagamento.setVisible(false);

        tpDocCliente.getItems().setAll("CPF", "CNPJ");
        tpDocCliente.show();
        tpDocCliente.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                docCliente.setDisable(false);
                okDocumento.setDisable(false);
            }
        });

        //MaskFieldUtil.cpfCnpjMask(cpfField);
    }

    @FXML
    private void paraPecas(){
        if(carregaCliente() && carregaVeiculo()) {
            passoClienteVeiculo.setVisible(false);
            scroll(1);
            passoPecas.setVisible(true);
        }else{
            mensagem("Preencha os dados do cliente e veículo", Alert.AlertType.ERROR);
        }
    }

    private boolean carregaCliente(){
        return true;
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

    private boolean carregaVeiculo(){
        return true;
    }

    private void mensagem(String msg, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Novo Orçamento");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }


    public void liberaCamposCliente(){
        Pane paneFields = (Pane) passoClienteVeiculo.lookup("#paneFieldsCliente");

        Iterator it = paneFields.getChildren().iterator();
        while(it.hasNext()){
            Node noAtual = (Node) it.next();
            if(!(noAtual instanceof TextField))
                continue;

            ((TextField) noAtual).setDisable(false);
        }
    }

    public boolean docOk() {
        Cliente cli = Cliente.buscaPorDocumento(Documento.Tipo.CPF, docCliente.getText());

        if (cli == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cliente não encontrado!");
            alert.setHeaderText("Deseja cadastrar o cliente?");
            alert.getButtonTypes().setAll(new ButtonType("Sim"), new ButtonType("Não"), new ButtonType("Cancelar"));

            Optional<ButtonType> escolha = alert.showAndWait();
            if(escolha.get().getText().equals("Sim")) {
                liberaCamposCliente();
            }else if(escolha.get().getText().equals("Não")) {
                // Deve ser um cliente apenas com o documento e nome
                cli = new Cliente();
                cli.setCPF(docCliente.getText());
                nomeCliente.setEditable(true);
            }

            return false;

        }


        for (Node noAtual : passoClienteVeiculo.getChildren()) {
            if (!(noAtual instanceof TextField))
                continue;

            TextField txt = (TextField) noAtual;
            switch (txt.getId()) {
                case "nome":
                    txt.setText(cli.getNome());
                    break;
                case "email":
                    txt.setText(cli.getEmail());
                    break;
                case "endereco":
                    txt.setText(cli.getEndereco());
                    break;
                case "telefone":
                    txt.setText(cli.getTelefone());
                    break;
                case "celular":
                    txt.setText(cli.getCelular());
                    break;
            }
        }


        return true;
    }
}
