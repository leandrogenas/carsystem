package atox.controller.cadastro;

import atox.exception.CarSystemException;
import atox.model.Fornecedor;
import atox.model.Servico;
import atox.model.Veiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

import static atox.utils.Validators.isCNPJ;

public class CadastroServicos {

    public Label lblTopo;
    public AnchorPane paneCadastroServicos;
    public TextField nome, descricao;
    public TableView<Servico> tblServicos;
    public TableColumn<Servico, String> colNome, colDescr;
    public Button btnEfetivar;

    private ObservableList<Servico> dadosTabela;

    private Servico servico;

    public void initialize(){
        carregaServicos();

        // Cria menu de contexto para opções adicionais
        ContextMenu cmTab = new ContextMenu();
        MenuItem iExc = new MenuItem("Excluir");
        iExc.setOnAction(ev -> {
            Servico svcSel = tblServicos.getSelectionModel().getSelectedItem();
            Servico.excluir(svcSel.getId());
            dadosTabela.remove(svcSel);
        });
        cmTab.getItems().add(iExc);

        // Adiciona handlers para os eventos de contexto
        tblServicos.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
            if(ev.getButton() == MouseButton.SECONDARY) {
                cmTab.show(tblServicos, ev.getScreenX(), ev.getScreenY());
            }
        });

        tblServicos.setRowFactory(tv -> {
            TableRow<Servico> linha = new TableRow<>();
            linha.setOnMouseClicked(ev -> {
                if(ev.getClickCount() == 2 && !(linha.isEmpty())){
                    alterar(linha.getItem());
                }
            });

            return linha;
        });

    }

    public void carregaServicos(){
        // Atrela as propriedades do modelo às colunas
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescr.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        // Cria a coleção de todos os serviços cadastrados
        dadosTabela = FXCollections.observableArrayList(Servico.todos());
        tblServicos.setItems(dadosTabela);
    }

    public void limpar(){
        for(Node no: paneCadastroServicos.getChildren()){
            if(!(no instanceof TextField)) continue;

            ((TextField) no).setText("");
        }

        lblTopo.setText("Novo serviço");
        btnEfetivar.setText("Cadastrar");

        servico = null;
    }

    private void alterar(Servico item) {
        lblTopo.setText("Alterar serviço");

        nome.setText(item.getNome());
        descricao.setText(item.getDescricao());

        servico = item;

        btnEfetivar.setText("Alterar");
    }

    public void efetivar(){
        try {
            // Valida a entrada
            if (nome.getText().isEmpty() || descricao.getText().isEmpty())
                throw new CarSystemException("Preencha os campos obrigatórios! (Nome, Descrição)");

            if(servico == null){
                servico = new Servico(
                    nome.getText(),
                    descricao.getText()
                );

                // Insere, verifica e adiciona à tabela
                Servico.inserir(servico);
                dadosTabela.add(servico);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Serviço cadastrado com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados do serviço foram cadastrados com sucesso!");

            }else{
                servico.setNome(nome.getText());
                servico.setDescricao(descricao.getText());

                Servico.alterar(servico);
                dadosTabela.clear();
                dadosTabela.setAll(Servico.todos());

            }
        }catch (CarSystemException | SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falha no cadastro do serviço!");
            alert.setHeaderText(null);
            alert.setContentText("Houve um erro no cadastro do serviço! Erro: "+ e.getMessage());

            alert.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
