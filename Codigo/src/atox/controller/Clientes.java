package atox.controller;

import atox.model.Cliente;
import atox.model.Veiculo;
import atox.utils.MaskFieldUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.Iterator;
import java.util.List;

import static atox.utils.Validators.*;

public class Clientes {
    @FXML
    private TextField cpfField, nomeField, emailField, enderecoField, telefoneField, celField,
            corField, modeloField, kmField, numParcelasField, anoField, marcaField, placaField;
    @FXML
    private CheckBox importadoCheckBox;

    private Cliente cliente;
    private Veiculo veiculo;

    @FXML
    public void initialize() {
        MaskFieldUtil.cpfCnpjMask(cpfField);
        MaskFieldUtil.telefoneMask(telefoneField);
        MaskFieldUtil.telefoneMask(celField);
        cpfField.textProperty().addListener((observable, oldValue, newValue) -> {
            setClienteFieldsDisabled(true);
            setVeiculoFieldsDisabled(true);
        });
        MaskFieldUtil.placaMask(placaField);
        MaskFieldUtil.anoMask(anoField);
        MaskFieldUtil.parcelasMask(numParcelasField);
        MaskFieldUtil.kmMask(kmField);
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
        } else {
            nomeField.setText("");
            emailField.setText("");
            enderecoField.setText("");
            telefoneField.setText("");
            celField.setText("");
        }
        setClienteFieldsDisabled(false);

        List<Veiculo> veiculos = Veiculo.buscaPorCliente(cliente.getDocumento());
        if(veiculos != null) {
            setVeiculoFieldsDisabled(false);
            for (Iterator<Veiculo> i = veiculos.iterator(); i.hasNext();) {
                veiculo = i.next();
            }
            placaField.setText(veiculo.getPlaca());
            corField.setText(veiculo.getCor());
            kmField.setText(Float.toString(veiculo.getKm()));
            anoField.setText(veiculo.getAno());
            importadoCheckBox.setSelected(veiculo.isImportado());
            marcaField.setText(veiculo.getMarca());
            modeloField.setText(veiculo.getModelo());
            numParcelasField.setText(veiculo.getNumParcelas());
        } else {
            placaField.setText("");
            corField.setText("");
            kmField.setText("");
            anoField.setText("");
            importadoCheckBox.setSelected(false);
            marcaField.setText("");
            modeloField.setText("");
            numParcelasField.setText("");
        }
        return true;
    }

    public boolean validaVeiculo() {
        if(placaField.getText().length() < 1) {
            return false;
        }
        if(!isPlaca(placaField.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Placa Inexistente");
            alert.setHeaderText(null);
            alert.setContentText("Os dados inseridos não correspondem a nehuma placa de veículo existente!");

            alert.showAndWait();
            return false;
        }

        Veiculo veiculo = Veiculo.buscaPorPlaca(placaField.getText());
        if(veiculo != null) {
            corField.setText(veiculo.getCor());
            kmField.setText(Float.toString(veiculo.getKm()));
            anoField.setText(veiculo.getAno());
            importadoCheckBox.setSelected(veiculo.isImportado());
            marcaField.setText(veiculo.getMarca());
            modeloField.setText(veiculo.getModelo());
            numParcelasField.setText(veiculo.getNumParcelas());
            if(veiculo.getCodProprietario() != cliente.getId()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Veículo de Outro Cliente!");
                alert.setHeaderText(null);
                alert.setContentText("O veículo procurado pertence a outro cliente! O dono do veículo procurado possui o seguinte documento: "+veiculo.getCodProprietario());

                alert.showAndWait();
                return false;
            }
        }
        setVeiculoFieldsDisabled(false);
        return true;
    }

    public void cadastrarCliente() {
        if (cliente == null) {
            cliente = new Cliente(
                    cpfField.getText(),
                    nomeField.getText(),
                    emailField.getText(),
                    telefoneField.getText(),
                    enderecoField.getText()
            );
            try{
                Cliente.inserir(cliente);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Falha no cadastro do cliente!");
                alert.setHeaderText(null);
                alert.setContentText("Houve um erro no cadastro do cliente! Erro: "+ex.getMessage());

                alert.showAndWait();
                return;
            }
        } else {
            cliente.setDocumento(cpfField.getText());
            cliente.setNome(nomeField.getText());
            cliente.setEmail(emailField.getText());
            cliente.setEndereco(enderecoField.getText());
            cliente.setTelefone(telefoneField.getText());
            try {
                Cliente.alterar(cliente);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Falha na atualização de dados!");
                alert.setHeaderText(null);
                alert.setContentText("Houve um erro na atualização dos dados do cliente! Erro: "+ex.getMessage());

                alert.showAndWait();
                return;
            }
        }


        if (veiculo == null) {
            veiculo = new Veiculo(
                    placaField.getText(),
                    cliente.getId(),
                    numParcelasField.getText(),
                    corField.getText(),
                    modeloField.getText(),
                    marcaField.getText(),
                    anoField.getText(),
                    importadoCheckBox.isSelected(),
                    Float.valueOf(kmField.getText())
            );
            try{
                Veiculo.inserir(veiculo);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cliente cadastrado com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados do cliente foram cadastrados com sucesso!");

                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Falha no cadastro do veículo!");
                alert.setHeaderText(null);
                alert.setContentText("Houve um erro no cadastro do veículo! Erro: "+ex.getMessage());

                alert.showAndWait();
                return;
            }
        } else {
            veiculo.setPlaca(placaField.getText());
            veiculo.setCodProprietario(Integer.valueOf(cpfField.getText()));
            veiculo.setNumParcelas(numParcelasField.getText());
            veiculo.setCor(corField.getText());
            veiculo.setModelo(modeloField.getText());
            veiculo.setMarca(marcaField.getText());
            veiculo.setAno(anoField.getText());
            veiculo.setImportado(importadoCheckBox.isSelected());
            veiculo.setKm(Float.valueOf(kmField.getText()));
            try {
                Veiculo.alterar(veiculo);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dados atualizados com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados do cliente foram atualizados com sucesso!");

                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Falha na atualização de dados!");
                alert.setHeaderText(null);
                alert.setContentText("Houve um erro na atualização dos dados do veículo! Erro: "+ex.getMessage());

                alert.showAndWait();
                return;
            }
        }
    }

    public void setClienteFieldsDisabled(boolean disable) {
        nomeField.setDisable(disable);
        emailField.setDisable(disable);
        enderecoField.setDisable(disable);
        telefoneField.setDisable(disable);
        celField.setDisable(disable);
        setVeiculoFieldsDisabled(true);
    }

    public void setVeiculoFieldsDisabled(boolean disable) {
        corField.setDisable(disable);
        modeloField.setDisable(disable);
        kmField.setDisable(disable);
        numParcelasField.setDisable(disable);
        anoField.setDisable(disable);
        marcaField.setDisable(disable);
        importadoCheckBox.setDisable(disable);
    }
}