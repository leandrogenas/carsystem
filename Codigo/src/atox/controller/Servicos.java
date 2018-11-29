package atox.controller;

import atox.exception.CarSystemException;
import atox.model.Fornecedor;
import atox.model.Servico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Servicos {

    public TextField nome;
    public TextField descricao;

    public TableView<Servico> tabServicos;
    public TableColumn<Servico, String> colNome;
    public TableColumn<Servico, String> colDescr;

    private ObservableList<Servico> dadosTabela;

    public Servicos(){
        // Cria a coleção de todos os serviços cadastrados
        dadosTabela = FXCollections.observableArrayList(Servico.todos());

    }

    public void initialize(){
        // Atrela as propriedades do modelo às colunas
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colNome.setOnEditCommit(ev -> {
            // Altera o dado na classe
            ev.getRowValue().setNome(ev.getNewValue());

            // Altera no BD
            Servico.alterar(ev.getRowValue());
        });

        colDescr.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colDescr.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescr.setOnEditCommit(ev -> {
            // Altera o dado na classe
            ev.getRowValue().setNome(ev.getNewValue());

            // Altera no BD
            Servico.alterar(ev.getRowValue());
        });

        // Cria menu de contexto para opções adicionais
        ContextMenu cmTab = new ContextMenu();
        MenuItem iExc = new MenuItem("Excluir");
        iExc.setOnAction(ev -> {
            Servico svcSel = tabServicos.getSelectionModel().getSelectedItem();
            Servico.excluir(svcSel.getId());
            dadosTabela.remove(svcSel);
        });
        cmTab.getItems().add(iExc);

        // Adiciona handlers para os eventos de contexto
        tabServicos.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
            if(ev.getButton() == MouseButton.SECONDARY) {
                cmTab.show(tabServicos, ev.getScreenX(), ev.getScreenY());
            }
        });

        // Seta os itens da tabela
        tabServicos.setItems(dadosTabela);

    }

    public void cadastrar(){
        try {
            // Valida a entrada
            if (nome.getText().isEmpty() || descricao.getText().isEmpty())
                throw new CarSystemException("Preencha os campos obrigatórios! (Nome, Descrição)");

            // Cria o novo objeto
            Servico svc = new Servico(
                nome.getText(),
                descricao.getText()
            );

            // Insere, verifica e adiciona à tabela
            Servico.inserir(svc);
            dadosTabela.add(svc);
        }catch (CarSystemException e){
            System.err.println(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
