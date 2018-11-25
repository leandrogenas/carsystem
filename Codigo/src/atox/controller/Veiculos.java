package atox.controller;

import atox.model.Cliente;
import atox.model.Veiculo;
import atox.utils.MaskFieldUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import static atox.utils.Validators.isCNPJ;
import static atox.utils.Validators.isCPF;
import static atox.utils.Validators.isPlaca;

public class Veiculos {
    @FXML
    private TextField docField, corField, modeloField, kmField, numParcelasField, anoField, marcaField, placaField;
    @FXML
    private CheckBox importadoCheckBox;

    private Veiculo veiculo;

    @FXML
    public void initialize() {
        placaField.textProperty().addListener((observable, oldValue, newValue) -> {
            setVeiculoFieldsDisabled(true);
        });
        MaskFieldUtil.placaMask(placaField);
        MaskFieldUtil.anoMask(anoField);
        MaskFieldUtil.parcelasMask(numParcelasField);
        MaskFieldUtil.kmMask(kmField);
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

        veiculo = Veiculo.buscaPorPlaca(placaField.getText());
        if(veiculo != null) {
            corField.setText(veiculo.getCor());
            kmField.setText(Float.toString(veiculo.getKm()));
            anoField.setText(veiculo.getAno());
            importadoCheckBox.setSelected(veiculo.isImportado());
            marcaField.setText(veiculo.getMarca());
            modeloField.setText(veiculo.getModelo());
            numParcelasField.setText(veiculo.getNumParcelas());
            docField.setText(Cliente.getDocumento(veiculo.getCodProprietario()));
        }
        setVeiculoFieldsDisabled(false);
        return true;
    }

    public void cadastrarVeiculo() {
        if(docField.getText().length() > 0) {
            if (!isCPF(docField.getText()) && !isCNPJ(docField.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("CPF ou CNPJ Inexistente");
                alert.setHeaderText(null);
                alert.setContentText("Os dados inseridos não correspondem a nehum CPF ou CNPJ existente!");

                alert.showAndWait();
                return;
            }
        }

        if (veiculo == null) {
            veiculo = new Veiculo(
                    placaField.getText(),
                    docField.getText(),
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
            veiculo.setCodProprietario(Cliente.getId(docField.getText()));
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
                alert.setContentText("Os dados do veículo foram atualizados com sucesso!");

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

    public void setVeiculoFieldsDisabled(boolean disable) {
        corField.setDisable(disable);
        modeloField.setDisable(disable);
        kmField.setDisable(disable);
        numParcelasField.setDisable(disable);
        anoField.setDisable(disable);
        marcaField.setDisable(disable);
        importadoCheckBox.setDisable(disable);
        docField.setDisable(disable);
    }
}
