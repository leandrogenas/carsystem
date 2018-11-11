package atox.controller;

import atox.BancoDeDados;
import atox.utils.MaskFieldUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import static atox.utils.Validators.isCNPJ;
import static atox.utils.Validators.isCPF;
import static atox.utils.DBManager.getCliente;

public class CadastroCliente {
    @FXML
    private TextField cpfField, nomeField, emailField, enderecoField, telefoneField, celField,
            corField, modeloField, kmField, numParcelasField, anoField, marcaField;
    @FXML
    private ComboBox<String> placaCBox;
    @FXML
    private AnchorPane paneVeiculo;
    @FXML
    private CheckBox importadoCBox;

    private BancoDeDados instancia;

    @FXML
    public void initialize() {
        MaskFieldUtil.cpfCnpjMask(cpfField);
        MaskFieldUtil.telefoneMask(telefoneField);
        MaskFieldUtil.telefoneMask(celField);
        cpfField.textProperty().addListener((observable, oldValue, newValue) -> {
            setClienteFieldsDisabled(true);
        });

        instancia = BancoDeDados.getInstancia();
    }

    public boolean validaCliente() {
        if(!isCPF(cpfField.getText()) && !isCNPJ(cpfField.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CPF ou CNPJ Inexistente");
            alert.setHeaderText(null);
            alert.setContentText("Os dados inseridos não correspondem a nehum CPF ou CNPJ existente!");

            alert.showAndWait();
            return false;
        }

        //TODO: validar se já existe no banco
        if(getCliente(cpfField.getText()) != null) {
            nomeField.setText("Nome");
            emailField.setText("email");
            enderecoField.setText("end");
            telefoneField.setText("Tel");
            celField.setText("cel");
        }
        setClienteFieldsDisabled(false);

        //TODO: puxar carro cadastrado no CPF/CNPJ
        setPanelVeiculoDisabled(false);
        placaCBox.getItems().add("AAA-0000");
        placaCBox.getSelectionModel().select("AAA-0000");
        corField.setText("Preto");
        kmField.setText("10000");
        anoField.setText("2018");
        importadoCBox.setSelected(false);
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
        setPanelVeiculoDisabled(true);
    }

    public void setPanelVeiculoDisabled(boolean disable) {
        paneVeiculo.setDisable(disable);
        paneVeiculo.setVisible(!disable);
    }
}
