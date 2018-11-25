package atox.controller;

import atox.model.Cliente;
import atox.utils.MaskFieldUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import static atox.utils.Validators.isCNPJ;
import static atox.utils.Validators.isCPF;

public class Clientes {
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
    public void initialize() {
        MaskFieldUtil.cpfCnpjMask(cpfField);
        MaskFieldUtil.telefoneMask(telefoneField);
        MaskFieldUtil.telefoneMask(celField);
        cpfField.textProperty().addListener((observable, oldValue, newValue) -> {
            setClienteFieldsDisabled(true);
        });
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

        cliente = Cliente.buscaPorDocumento(cpfField.getText());
        if (cliente != null) {
            nomeField.setText(cliente.getNome());
            emailField.setText(cliente.getEmail());
            enderecoField.setText(cliente.getEndereco());
            telefoneField.setText(cliente.getTelefone());
            celField.setText(cliente.getCelular());
        }
        setClienteFieldsDisabled(false);

        //TODO: puxar carro cadastrado no CPF/CNPJ
        setPanelVeiculoDisabled(false);
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

    public void cadastrarCliente() {
        if (cliente == null) {
            cliente = new Cliente(
                    cpfField.getText(),
                    nomeField.getText(),
                    emailField.getText(),
                    telefoneField.getText(),
                    celField.getText(),
                    enderecoField.getText()
            );
            try{
                Cliente.inserir(cliente);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cliente cadastrado com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados do cliente foram cadastrados com sucesso!");

                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Falha no cadastro do cliente!");
                alert.setHeaderText(null);
                alert.setContentText("Houve um erro no cadastro do cliente! Erro: "+ex.getMessage());

                alert.showAndWait();
            }
        } else {
            cliente.setDocumento(cpfField.getText());
            cliente.setNome(nomeField.getText());
            cliente.setEmail(emailField.getText());
            cliente.setEndereco(enderecoField.getText());
            cliente.setTelefone(telefoneField.getText());
            cliente.setCelular(celField.getText());
            try {
                Cliente.alterar(cliente);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dados atualizados com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados do cliente foram atualizados com sucesso!");

                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Falha na atualização de dados!");
                alert.setHeaderText(null);
                alert.setContentText("Houve um erro na atualização dos dados do cliente! Erro: "+ex.getMessage());

                alert.showAndWait();
            }
        }
    }

    public void setClienteFieldsDisabled(boolean disable) {
        nomeField.setDisable(disable);
        emailField.setDisable(disable);
        enderecoField.setDisable(disable);
        telefoneField.setDisable(disable);
        celField.setDisable(disable);
        setPanelVeiculoDisabled(true);
    }

    public void setPanelVeiculoDisabled(boolean disable) {
        paneVeiculo.setDisable(disable);
        paneVeiculo.setVisible(!disable);
        corField.setDisable(disable);
        modeloField.setDisable(disable);
        kmField.setDisable(disable);
        numParcelasField.setDisable(disable);
        anoField.setDisable(disable);
        marcaField.setDisable(disable);
        importadoCheckBox.setDisable(disable);
    }
}
