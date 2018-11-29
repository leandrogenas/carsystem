package atox.controller;

import atox.CarSystem;
import atox.exception.CarSystemException;
import atox.model.Fornecedor;
import atox.model.Veiculo;
import atox.utils.MaskFieldUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

import static atox.utils.Validators.isCNPJ;
import static atox.utils.Validators.isCPF;

public class CadastroFornecedores {

    public AnchorPane paneCadastroFornecedores;
    public Label lblTopo;
    public TextField nome, cnpj, endereco, telefone;
    public TableView<Fornecedor> tblFornecedores;
    public TableColumn<Fornecedor, String> colNome, colCNPJ, colTelefone, colEndereco;
    public Button btnEfetivar;

    private ObservableList<Fornecedor> dadosTabela;
    private Fornecedor fornecedor;

    public void initialize(){
        carregaFornecedores();

        MaskFieldUtil.cpfCnpjMask(cnpj);
        MaskFieldUtil.telefoneMask(telefone);

        // Cria menu de contexto para opções adicionais
        MenuItem iExc = new MenuItem("Excluir");
        iExc.setOnAction(ev -> {
            Fornecedor fornSel = tblFornecedores.getSelectionModel().getSelectedItem();
            Fornecedor.excluir(fornSel.getId());
            dadosTabela.remove(fornSel);
        });

        tblFornecedores.setRowFactory(tv -> {
            TableRow<Fornecedor> linha = new TableRow<>();
            linha.setOnMouseClicked(ev -> {
                if(ev.getClickCount() == 2 && !(linha.isEmpty())){
                    alterar(linha.getItem());
                }
            });

            return linha;
        });

        ContextMenu cmTab = new ContextMenu();
        cmTab.getItems().add(iExc);

        // Adiciona handlers para os eventos de contexto
        tblFornecedores.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                cmTab.show(tblFornecedores, event.getScreenX(), event.getScreenY());
            }
        });



    }

    private void carregaFornecedores(){
        // Atrela as propriedades do modelo às colunas
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        dadosTabela = FXCollections.observableArrayList(Fornecedor.todos());
        tblFornecedores.setItems(dadosTabela);
    }

    public void limpar(){
        for(Node no: paneCadastroFornecedores.getChildren()){
            if(!(no instanceof TextField)) continue;

            ((TextField) no).setText("");
        }

        lblTopo.setText("Novo Fornecedor");
        btnEfetivar.setText("Cadastrar");

        fornecedor = null;
    }

    private void alterar(Fornecedor item) {
        lblTopo.setText("Alterar fornecedor");

        nome.setText(item.getNome());
        cnpj.setText(item.getCNPJ());
        endereco.setText(item.getEndereco());
        telefone.setText(item.getTelefone());

        fornecedor = item;

        btnEfetivar.setText("Alterar");
    }

    public void efetivar(){
        try {
            if (nome.getText().isEmpty() || cnpj.getText().isEmpty() || telefone.getText().isEmpty())
                throw new CarSystemException("Preencha os campos obrigatórios! (Nome, CNPJ e Endereço)");

            if(fornecedor == null) {
                fornecedor = new Fornecedor(
                        nome.getText(),
                        cnpj.getText(),
                        telefone.getText(),
                        endereco.getText()
                );
                fornecedor.setEndereco(endereco.getText());

                if(!Fornecedor.inserir(fornecedor))
                    throw new CarSystemException("Erro no SQL");

                dadosTabela.add(fornecedor);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fornecedor cadastrado com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados do fornecedor foram cadastrados com sucesso!");
            }else{
                if (isCNPJ(cnpj.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("CNPJ inválido");
                    alert.setHeaderText(null);
                    alert.setContentText("O documento inserido é inválido!");

                    alert.showAndWait();
                    return;
                }

                fornecedor.setNome(nome.getText());
                fornecedor.setEndereco(endereco.getText());
                fornecedor.setCNPJ(cnpj.getText());
                fornecedor.setTelefone(telefone.getText());

                Fornecedor.alterar(fornecedor);
                dadosTabela.clear();
                dadosTabela.setAll(Fornecedor.todos());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dados atualizados com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados do fornecedor foram atualizados com sucesso!");

            }

        }catch (CarSystemException | SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falha no cadastro do fornecedor!");
            alert.setHeaderText(null);
            alert.setContentText("Houve um erro no cadastro do fornecedor! Erro: "+e.getMessage());

            alert.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
