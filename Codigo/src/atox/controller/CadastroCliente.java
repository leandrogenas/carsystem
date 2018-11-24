package atox.controller;

import atox.model.Cliente;
import atox.model.Veiculo;
import atox.utils.MaskFieldUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import static atox.BancoDeDados.*;
import static atox.utils.Validators.isCNPJ;
import static atox.utils.Validators.isCPF;

public class CadastroCliente {
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
    private Veiculo veiculo;

    @FXML
    public void initialize() {
        MaskFieldUtil.cpfCnpjMask(cpfField);
        MaskFieldUtil.telefoneMask(telefoneField);
        MaskFieldUtil.telefoneMask(celField);
        cpfField.textProperty().addListener((observable, oldValue, newValue) -> { setClienteFieldsDisabled(true); });
        MaskFieldUtil.placaMask(placaComboBox);
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

        if(placaComboBox.getValue() != null) {
            veiculo = getVeiculo(placaComboBox.getValue());
        }

        if (veiculo != null) {
            placaComboBox.getItems().add(veiculo.getPlaca());
            cpfField.setText(cliente.getCPF());
            numParcelasField.setText(veiculo.getNumParcelas());
            corField.setText(veiculo.getCor());
            modeloField.setText(veiculo.getModelo());
            marcaField.setText(veiculo.getMarca());
            importadoCheckBox.setSelected(veiculo.isImportado());
            kmField.setText(Float.toString(veiculo.getKm()));
        }
        setPanelVeiculoDisabled(false);

        return true;
    }

    public void cadastrarCliente() {
        if (cliente == null) {
            cliente = new Cliente(
                    cpfField.getText(),
                    nomeField.getText(),
                    emailField.getText(),
                    enderecoField.getText(),
                    telefoneField.getText(),
                    celField.getText()
            );
            try{
                setCliente(cliente);
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
            cliente.setCPF(cpfField.getText());
            cliente.setNome(nomeField.getText());
            cliente.setEmail(emailField.getText());
            cliente.setEndereco(enderecoField.getText());
            cliente.setTelefone(telefoneField.getText());
            cliente.setCelular(celField.getText());
            try {
                updateCliente(cliente);
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


            veiculo.setPlaca(placaComboBox.getValue());
            veiculo.setCpfProprietario(cpfField.getText());
            veiculo.setNumParcelas(numParcelasField.getText());
            veiculo.setCor(corField.getText());
            veiculo.setModelo(modeloField.getText());
            veiculo.setMarca(marcaField.getText());
            veiculo.setImportado(importadoCheckBox.isSelected());
            veiculo.setKm(Float.valueOf(kmField.getText()));
            try {
                updateVeiculo(veiculo);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dados atualizados com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados do veiculo foram atualizados com sucesso!");

                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Falha na atualização de dados!");
                alert.setHeaderText(null);
                alert.setContentText("Houve um erro na atualização dos dados do veiculo! Erro: "+ex.getMessage());

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
        setPanelVeiculoDisabled(disable);
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
