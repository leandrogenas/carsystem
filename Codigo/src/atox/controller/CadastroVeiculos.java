package atox.controller;

import atox.CarSystem;
import atox.exception.CarSystemException;
import atox.model.Cliente;
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
import javafx.util.Callback;

import java.sql.SQLException;

import static atox.utils.Validators.isCNPJ;
import static atox.utils.Validators.isCPF;
import static atox.utils.Validators.isPlaca;

public class CadastroVeiculos {

    public AnchorPane paneCadastroVeiculo;
    public TableView<Veiculo> tblVeiculo;
    public TableColumn<Veiculo, String> colPlaca, colVeiculo, colKm, colAno, colPropr;
    public TextField docField, corField, modeloField, kmField, anoField, marcaField, placaField;
    public CheckBox importadoCheckBox;
    public Button btnEfetivar;
    public Label lblTopo;

    private Veiculo veiculo;
    private ObservableList<Veiculo> dados;

    @FXML
    public void initialize() {
        carregaVeiculos();

        MaskFieldUtil.placaMask(placaField);
        MaskFieldUtil.anoMask(anoField);
        MaskFieldUtil.kmMask(kmField);
        MaskFieldUtil.cpfMask(docField);

        tblVeiculo.setRowFactory(tv -> {
            TableRow<Veiculo> linha = new TableRow<>();
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
            Veiculo veicSel = tblVeiculo.getSelectionModel().getSelectedItem();

            try {
                if (!Veiculo.excluir(veicSel.getId()))
                    throw new CarSystemException("Falha no SQL");

                dados.remove(veicSel);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dados atualizados com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("O veículo foi excluído com sucesso!");
            }catch (CarSystemException ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erro ao excluir veículo!");
                alert.setHeaderText(null);
                alert.setContentText("Falha ao excluir o veículo, erro: " + ex.getMessage());
            }

        });
        cmTab.getItems().add(iExc);

        // Adiciona handlers para os eventos de contexto
        tblVeiculo.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
            if(ev.getButton() == MouseButton.SECONDARY) {
                cmTab.show(tblVeiculo, ev.getScreenX(), ev.getScreenY());
            }
        });
    }

    private void carregaVeiculos() {
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colVeiculo.setCellValueFactory(param -> {
            SimpleStringProperty propr = new SimpleStringProperty();

            propr.setValue(
                    param.getValue().getMarca() + ", " +
                            param.getValue().getCor() + ", " +
                            param.getValue().getModelo()
            );

            return propr;
        });
        colKm.setCellValueFactory(new PropertyValueFactory<>("km"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colPropr.setCellValueFactory(new PropertyValueFactory<>("cpfPropr"));

        dados = FXCollections.observableArrayList(Veiculo.todos());
        tblVeiculo.setItems(dados);
    }

    public void limpar(){
        for(Node no: paneCadastroVeiculo.getChildren()){
            if(!(no instanceof TextField)) continue;

            ((TextField) no).setText("");
        }

        lblTopo.setText("Novo Veículo");
        btnEfetivar.setText("Cadastrar");

        veiculo = null;
    }

    private void alterar(Veiculo item){
        lblTopo.setText("Alterar veículo");

        placaField.setText(item.getPlaca());
        corField.setText(item.getCor());
        marcaField.setText(item.getMarca());
        kmField.setText(String.valueOf(item.getKm()));
        modeloField.setText(item.getModelo());
        anoField.setText(item.getAno());
        docField.setText(item.getProprietario().getDocumento());

        veiculo = item;

        btnEfetivar.setText("Alterar");
    }

    public void efetivar() {
        try {
            if (veiculo == null) {
                veiculo = new Veiculo(
                        placaField.getText(),
                        Cliente.buscaPorDocumento(docField.getText()),
                        marcaField.getText(),
                        modeloField.getText(),
                        anoField.getText(),
                        corField.getText(),
                        importadoCheckBox.isSelected(),
                        Integer.parseInt(kmField.getText())
                );

                if (!Veiculo.inserir(veiculo))
                    throw new CarSystemException("Erro no SQL");

                dados.add(veiculo);

            } else {
                veiculo.setPlaca(placaField.getText());
                veiculo.setProprietario(Cliente.buscaPorDocumento(docField.getText()));
                veiculo.setCor(corField.getText());
                veiculo.setModelo(modeloField.getText());
                veiculo.setMarca(marcaField.getText());
                veiculo.setAno(anoField.getText());
                veiculo.setImportado(importadoCheckBox.isSelected());
                veiculo.setKm(Integer.parseInt(kmField.getText()));

                Veiculo.alterar(veiculo);
                dados.clear();
                dados.setAll(Veiculo.todos());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dados atualizados com sucesso!");
                alert.setHeaderText(null);
                alert.setContentText("Os dados do veículo foram atualizados com sucesso!");
                alert.showAndWait();

            }
        } catch (CarSystemException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falha na atualização de dados!");
            alert.setHeaderText(null);
            alert.setContentText("Houve um erro na atualização dos dados do veículo! Erro: " + ex.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
