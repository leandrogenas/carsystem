package atox.controller;

import atox.model.Cliente;
import atox.utils.MaskFieldUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static atox.BancoDeDados.getCliente;
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
    private TextField cpfField, nomeField, emailField, enderecoField, telefoneField, celField,
            corField, modeloField, kmField, numParcelasField, anoField, marcaField;
    @FXML
    private ComboBox<String> placaComboBox;
    @FXML
    private AnchorPane paneVeiculo;
    @FXML
    private CheckBox importadoCheckBox;

    private Cliente cliente;

    @FXML
    private void initialize(){
        passoClienteVeiculo.setVisible(true);
        passoPecas.setVisible(false);
        passoServicos.setVisible(false);
        passoPagamento.setVisible(false);

        MaskFieldUtil.cpfCnpjMask(cpfField);
        MaskFieldUtil.telefoneMask(telefoneField);
        MaskFieldUtil.telefoneMask(celField);
        cpfField.textProperty().addListener((observable, oldValue, newValue) -> {
            setClienteFieldsDisabled(true);
        });
    }

    @FXML
    private void paraPecas(){
        if(validaCliente() && verificaVeiculo()) {
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

    public boolean validaCliente() {
        if (!isCPF(cpfField.getText()) && !isCNPJ(cpfField.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CPF ou CNPJ Inexistente");
            alert.setHeaderText(null);
            alert.setContentText("Os dados inseridos não correspondem a nehum CPF ou CNPJ existente!");

            alert.showAndWait();
            return false;
        }

        cliente = getCliente(cpfField.getText());
        if (cliente != null) {
            nomeField.setText(cliente.getNome());
            emailField.setText(cliente.getEmail());
            enderecoField.setText(cliente.getEndereco());
            telefoneField.setText(cliente.getTelefone());
            celField.setText(cliente.getCelular());
        }
        setClienteFieldsDisabled(false);

        //TODO: puxar carro cadastrado no CPF/CNPJ
        placaComboBox.getItems().add("AAA-0000");
        placaComboBox.getSelectionModel().select("AAA-0000");
        corField.setText("Preto");
        kmField.setText("10000");
        anoField.setText("2018");
        importadoCheckBox.setSelected(false);
        marcaField.setText("Chevrolet");
        modeloField.setText("Chips");
        numParcelasField.setText("1");

        return true;
    }

    public void setClienteFieldsDisabled(boolean disable) {
        nomeField.setDisable(disable);
        emailField.setDisable(disable);
        enderecoField.setDisable(disable);
        telefoneField.setDisable(disable);
        celField.setDisable(disable);
    }
}
