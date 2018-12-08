package atox.controller;

import atox.exception.CarSystemException;
import atox.model.Cliente;
import atox.model.Servico;
import atox.model.Veiculo;
import atox.utils.MaskFieldUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Iterator;
import java.util.List;

import static atox.utils.Validators.*;

public class CadastroClientes {

    public AnchorPane paneCadastroCliente;
    public TextField cpfField, nomeField, emailField, enderecoField, telefoneField;
    public TableView<Cliente> tblClientes;
    public TableColumn<Cliente, String> colNome, colCPF, colEmail, colEndereco, colTelefone;
    public Button btnEfetivar;
    public Label lblTopo;

    private Cliente cliente;
    private ObservableList<Cliente> dados;

    @FXML
    public void initialize() {
        carregaClientes();

        MaskFieldUtil.cpfCnpjMask(cpfField);
        MaskFieldUtil.telefoneMask(telefoneField);

        tblClientes.setRowFactory(tv -> {
            TableRow<Cliente> linha = new TableRow<>();
            linha.setOnMouseClicked(ev -> {
                if(ev.getClickCount() == 2 && !(linha.isEmpty())){
                    alterar(linha.getItem());
                }
            });

            return linha;
        });

        // Cria menu de contexto para opções adicionais
        ContextMenu cmTab = new ContextMenu();
        MenuItem iExc = new MenuItem("Excluir");
        iExc.setOnAction(ev -> {
            Cliente cliSel = tblClientes.getSelectionModel().getSelectedItem();

            try {
                if (!Cliente.excluir(cliSel.getId()))
                    throw new CarSystemException("Falha no SQL");

                dados.remove(cliSel);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dados atualizados com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("O cliente foi excluído com sucesso!");
            }catch (CarSystemException ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erro ao excluir veículo!");
                alert.setHeaderText(null);
                alert.setContentText("Falha ao excluir o cliente, erro: " + ex.getMessage());
            }
        });
        cmTab.getItems().add(iExc);

        // Adiciona handlers para os eventos de contexto
        tblClientes.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
            if(ev.getButton() == MouseButton.SECONDARY) {
                cmTab.show(tblClientes, ev.getScreenX(), ev.getScreenY());
            }
        });
    }

    private void carregaClientes(){
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        dados = FXCollections.observableArrayList(Cliente.todos());
        tblClientes.setItems(dados);
    }

    public void limpar(){
        for(Node no: paneCadastroCliente.getChildren()){
            if(!(no instanceof TextField)) continue;

            ((TextField) no).setText("");
        }

        lblTopo.setText("Novo cliente");
        btnEfetivar.setText("Cadastrar");

        cliente = null;
    }

    private void alterar(Cliente item){
        lblTopo.setText("Alterar cliente");

        cpfField.setText(item.getDocumento());
        nomeField.setText(item.getNome());
        emailField.setText(item.getEmail());
        enderecoField.setText(item.getEndereco());
        telefoneField.setText(item.getTelefone());

        cliente = item;

        btnEfetivar.setText("Alterar");
    }

    public void efetivar() {
        try {
            if (cliente == null) {
                cliente = new Cliente(
                        cpfField.getText(),
                        nomeField.getText(),
                        emailField.getText(),
                        telefoneField.getText(),
                        enderecoField.getText()
                );

                cliente = Cliente.inserir(cliente);

                dados.add(cliente);


                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Cliente cadastrado com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText(cliente.getNome() + " foi cadastrado com sucesso!");alert.showAndWait();

            } else {
                if (!isCPF(cpfField.getText()) && !isCNPJ(cpfField.getText()))
                    throw new CarSystemException("CPF ou CNPJ inválido");

                cliente.setDocumento(cpfField.getText());
                cliente.setNome(nomeField.getText());
                cliente.setEmail(emailField.getText());
                cliente.setEndereco(enderecoField.getText());
                cliente.setTelefone(telefoneField.getText());

                Cliente.alterar(cliente);
                dados.clear();
                dados.setAll(Cliente.todos());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dados atualizados com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados do cliente foram atualizados com sucesso!");
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falha na atualização de dados!");
            alert.setHeaderText(null);
            alert.setContentText("Houve um erro na atualização dos dados do cliente! Erro: " + ex.getMessage());
            alert.showAndWait();

        }
    }

}