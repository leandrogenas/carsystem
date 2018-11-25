package atox.controller;

import atox.exception.CarSystemException;
import atox.model.Fornecedor;
import atox.model.Servico;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Fornecedores {

    public TextField nome;
    public TextField cnpj;
    public TextField endereco;
    public TextField telefone;

    public TableView<Fornecedor> tabFornecedores;
    public TableColumn<Fornecedor, String> colNome;
    public TableColumn<Fornecedor, String> colCNPJ;
    public TableColumn<Fornecedor, String> colTelefone;
    public TableColumn<Fornecedor, String> colEndereco;

    private ObservableList<Fornecedor> dadosTabela;

    public Fornecedores(){
        dadosTabela = FXCollections.observableArrayList(Fornecedor.todos());
    }

    public void initialize(){
        // Atrela as propriedades do modelo às colunas
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colNome.setOnEditCommit(ev -> {
            // Altera o dado na classe
            ev.getRowValue().setNome(ev.getNewValue());

            // Altera no BD
            Fornecedor.alterar(ev.getRowValue());
        });

        colCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colCNPJ.setCellFactory(TextFieldTableCell.forTableColumn());
        colCNPJ.setOnEditCommit(ev -> {
            // Altera o dado na classe
            ev.getRowValue().setCNPJ(ev.getNewValue());

            // Altera no BD
            Fornecedor.alterar(ev.getRowValue());
        });

        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colEndereco.setCellFactory(TextFieldTableCell.forTableColumn());
        colEndereco.setOnEditCommit(ev -> {
            // Altera o dado na classe
            ev.getRowValue().setEndereco(ev.getNewValue());

            // Altera no BD
            Fornecedor.alterar(ev.getRowValue());
        });

        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colTelefone.setCellFactory(TextFieldTableCell.forTableColumn());
        colTelefone.setOnEditCommit(ev -> {
            // Altera o dado na classe
            ev.getRowValue().setTelefone(ev.getNewValue());

            // Altera no BD
            Fornecedor.alterar(ev.getRowValue());
        });


        // Cria menu de contexto para opções adicionais
        MenuItem iExc = new MenuItem("Excluir");
        iExc.setOnAction(ev -> {
            Fornecedor fornSel = tabFornecedores.getSelectionModel().getSelectedItem();
            Fornecedor.excluir(fornSel.getId());
            dadosTabela.remove(fornSel);
        });

        ContextMenu cmTab = new ContextMenu();
        cmTab.getItems().add(iExc);

        // Adiciona handlers para os eventos de contexto
        tabFornecedores.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                cmTab.show(tabFornecedores, event.getScreenX(), event.getScreenY());
            }
        });

        tabFornecedores.setItems(dadosTabela);

    }

    public void cadastrar(){
        try {
            if (nome.getText().isEmpty() || cnpj.getText().isEmpty() || telefone.getText().isEmpty())
                throw new CarSystemException("Preencha os campos obrigatórios! (Nome, CNPJ e Endereço)");

            Fornecedor forn = new Fornecedor(
                    nome.getText(),
                    cnpj.getText(),
                    telefone.getText()
            );
            forn.setEndereco(endereco.getText());

            Fornecedor.inserir(forn);
            dadosTabela.add(forn);
        }catch (CarSystemException e){
            System.err.println(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
