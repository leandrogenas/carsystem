package atox.controller;

import atox.exception.CarSystemException;
import atox.model.Fornecedor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Fornecedores {

    class FornecedorTable{
        private SimpleStringProperty nome;
        private SimpleStringProperty cnpj;
        private SimpleStringProperty endereco;
        private SimpleStringProperty telefone;

        public FornecedorTable(Fornecedor forn){
            nome = new SimpleStringProperty(forn.getNome());
            cnpj = new SimpleStringProperty(forn.getCNPJ());
            endereco = new SimpleStringProperty(forn.getEndereco());
            telefone = new SimpleStringProperty(forn.getTelefone());
        }

        public void setNome(String nome){ this.nome.set(nome); }
        public void setCnpj(String cnpj){ this.cnpj.set(cnpj); }
        public void setEndereco(String endereco){ this.endereco.set(endereco); }
        public void setTelefone(String telefone){ this.telefone.set(telefone); }
        public String getNome(){ return nome.get(); }
        public String getCnpj(){ return cnpj.get(); }
        public String getEndereco(){ return endereco.get(); }
        public String getTelefone(){ return telefone.get(); }
    }

    public TextField nome;
    public TextField cnpj;
    public TextField endereco;
    public TextField telefone;

    public TableView tabFornecedores;
    public TableColumn colNome;
    public TableColumn colCNPJ;
    public TableColumn colTelefone;
    public TableColumn colEndereco;

    private ObservableList<Fornecedor> dadosTabela;

    public void initialize(){
        dadosTabela = FXCollections.observableArrayList(Fornecedor.todos());

        colNome.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("nome"));
        colCNPJ.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("cnpj"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("endereco"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("telefone"));

        tabFornecedores.setItems(dadosTabela);

        ContextMenu cmTab = new ContextMenu();
        cmTab.getItems().add(new MenuItem("Excluir fornecedor"));

        tabFornecedores.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.SECONDARY)
                cmTab.show(tabFornecedores, event.getScreenX(), event.getScreenY());
        });

        cmTab.setOnAction((ActionEvent event) -> {
            Fornecedor sel = (Fornecedor) tabFornecedores.getSelectionModel().getSelectedItem();
            System.out.println(sel.getNome());
        });

    }

    private void inserirLinha(Fornecedor forn){

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
