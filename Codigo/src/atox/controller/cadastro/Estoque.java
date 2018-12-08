package atox.controller.cadastro;

import atox.exception.CarSystemException;
import atox.model.Fornecedor;
import atox.model.Peca;
import atox.utils.MaskFieldUtil;
import javafx.beans.property.SimpleStringProperty;
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

public class Estoque {

    public AnchorPane paneEstoque;

    public TextField nome, modelo, quantidade, valUnit;
    public Label lblTopo;
    public Button btnEfetivar;
    public TableView<Peca> tblPecas;
    public TableColumn<Peca, String> colNome, colModelo, colEmEstoque, colValUnit;

    private ObservableList<Peca> dadosTabela;
    private Peca peca;

    public void initialize(){
        carregaPecas();

        // Cria menu de contexto para opções adicionais
        MenuItem iExc = new MenuItem("Excluir");
        iExc.setOnAction(ev -> {
            Peca pecaSel = tblPecas.getSelectionModel().getSelectedItem();
            Peca.excluir(pecaSel.getId());
            dadosTabela.remove(pecaSel);
        });

        tblPecas.setRowFactory(tv -> {
            TableRow<Peca> linha = new TableRow<>();
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
        tblPecas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                cmTab.show(tblPecas, event.getScreenX(), event.getScreenY());
            }
        });



    }

    private void carregaPecas(){
        // Atrela as propriedades do modelo às colunas
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colEmEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValUnit.setCellValueFactory(param -> {
            SimpleStringProperty propr = new SimpleStringProperty();
            propr.setValue(Double.toString(param.getValue().getValUnit()));
            return propr;
        });

        dadosTabela = FXCollections.observableArrayList(Peca.todos());
        tblPecas.setItems(dadosTabela);
    }

    public void limpar(){
        for(Node no: paneEstoque.getChildren()){
            if(!(no instanceof TextField)) continue;

            ((TextField) no).setText("");
        }

        lblTopo.setText("Nova peça");
        btnEfetivar.setText("Cadastrar");

        peca = null;
    }

    private void alterar(Peca item) {
        lblTopo.setText("Alterar peça");

        nome.setText(item.getNome());
        modelo.setText(item.getModelo());
        quantidade.setText(String.valueOf(item.getQuantidade()));
        valUnit.setText(String.valueOf(item.getValUnit()));

        peca = item;

        btnEfetivar.setText("Alterar");
    }

    public void efetivar(){
        try {
            if(peca == null) {
                peca = new Peca(
                        Integer.parseInt(quantidade.getText()),
                        nome.getText(),
                        modelo.getText(),
                        Double.parseDouble(valUnit.getText())
                );

                Peca.inserir(peca);

                dadosTabela.add(peca);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fornecedor cadastrado com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados da peça foram cadastrados com sucesso!");
            }else{
                peca.setNome(nome.getText());
                peca.setModelo(modelo.getText());
                peca.setValUnit(Double.parseDouble(valUnit.getText()));
                peca.setQuantidade(Integer.parseInt(quantidade.getText()));

                Peca.alterar(peca);
                dadosTabela.clear();
                dadosTabela.setAll(Peca.todos());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dados atualizados com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados da peça foram atualizados com sucesso!");

            }

        }catch (CarSystemException | SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falha no cadastro do fornecedor!");
            alert.setHeaderText(null);
            alert.setContentText("Houve um erro no cadastro da peça! Erro: "+e.getMessage());

            alert.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
