package atox.controller.novo_orcamento;

import atox.exception.CarSystemException;
import atox.model.Cliente;
import atox.model.Veiculo;
import atox.utils.MaskFieldUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class PassoClienteVeiculo extends Passos {


    // Campos do cliente
    private Pane dadosCliente;
    private Pane dadosVeiculo;
    private Button okDocumento;
    private Button okPlaca;
    private TextField docCliente, nomeCliente, emailCliente, enderecoCliente, telefoneCliente;
    private TextField placaVeiculo, marcaVeiculo, modeloVeiculo, corVeiculo, kmVeiculo, anoVeiculo;
    private CheckBox importadoVeiculo;


    PassoClienteVeiculo(AnchorPane pane){
        super(pane);

        MaskFieldUtil.placaMask(placaVeiculo);
    }

    @Override
    public boolean passoValido() {
        try {
            if(!clienteValido())
                throw new CarSystemException("Preencha o nome do cliente!");
            if(!veiculoValido())
                throw new CarSystemException("Preencha os campos obrigatórios (com asterisco) do veículo!");

            return true;
        }catch (CarSystemException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atenção!");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();

            return false;
        }

    }

    private boolean veiculoValido() {
        return (!placaVeiculo.getText().isEmpty());
    }

    private boolean clienteValido() {
        return (!nomeCliente.getText().isEmpty());
    }

    public void liberaCamposCliente(){
        nomeCliente.setDisable(false);

        for(Node no: dadosCliente.getChildren()){
            if(!(no instanceof TextField)) continue;

            no.setDisable(false);
        }

    }

    private void liberaCamposVeiculo() {
        for(Node no: dadosVeiculo.getChildren()){
            if(!(no instanceof TextField) && !(no instanceof CheckBox)) continue;

            no.setDisable(false);
        }
    }

    private void limpaCamposCliente(){
        nomeCliente.setText("");

        TextField txt = null;
        for(Node no: dadosCliente.getChildren()){
            if(!(no instanceof TextField)) continue;

            txt = (TextField) no;

            if(txt.getId().equals("docCliente"))
                continue;

            txt.setText("");
        }
    }

    private void limpaCamposVeiculo(){
        TextField txt = null;
        for(Node no: dadosVeiculo.getChildren()){
            if(!(no instanceof TextField)) continue;

            txt = (TextField) no;

            if(txt.getId().equals("placaVeiculo"))
                continue;

            txt.setText("");
        }
    }

    public boolean promptCadastro(String titulo, String conteudo){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atenção");
        alert.setContentText(conteudo);
        alert.setHeaderText(titulo);
        alert.getButtonTypes().setAll(new ButtonType("Sim"), new ButtonType("Não"), new ButtonType("Cancelar"));

        Optional<ButtonType> escolha = alert.showAndWait();

        if(!escolha.isPresent())
            return false;

        return escolha.get().getText().equals("Sim");

    }

    public void consultarCliente(String doc){
        limpaCamposCliente();

        // Busca o cliente
        Cliente cli = Cliente.buscaPorDocumento(doc);

        // Caso o cliente não exista, pergunta se quer cadastrar
        if (cli == null) {
            if (promptCadastro("Cliente não encontrado!", "Deseja cadastrar o cliente?")){
                liberaCamposCliente();

                return;
            }else{
                // Avisa que é necesário preencher o nome do cliente
                Alert aviso = new Alert(Alert.AlertType.INFORMATION);
                aviso.setTitle("Atenção");
                aviso.setHeaderText(null);
                aviso.setContentText("Preencha o nome do cliente antes de continuar");
                aviso.showAndWait();

                nomeCliente.setDisable(false);
                return;
            }
        }

        // Carrega os dados do cliente caso encontrado
        nomeCliente.setText(cli.getNome());
        emailCliente.setText(cli.getEmail());
        enderecoCliente.setText(cli.getEndereco());
        telefoneCliente.setText(cli.getTelefone());

    }

    public void consultarPlaca(String placa){
        limpaCamposVeiculo();

        // Busca o cliente
        Veiculo veiculo = Veiculo.buscaPorPlaca(placa);

        liberaCamposVeiculo();
        if(veiculo != null){
            marcaVeiculo.setText(veiculo.getMarca());
            modeloVeiculo.setText(veiculo.getModelo());
            corVeiculo.setText(veiculo.getCor());
            anoVeiculo.setText(veiculo.getAno());
            kmVeiculo.setText(Float.toString(veiculo.getKm()));
            importadoVeiculo.setSelected(veiculo.isImportado());
            // ?
            //((TextField) container.lookup("#parcelasVeiculo")).setText(veiculo.get());
        }
    }

    @Override
    public void carregarElementos(){
        // Carrega os campos de dados do cliente
        dadosCliente = (Pane) this.container.lookup("#paneDadosCliente");
        docCliente = (TextField) container.lookup("#docCliente");
        nomeCliente = (TextField) container.lookup("#nomeCliente");
        emailCliente = (TextField) container.lookup("#emailCliente");
        enderecoCliente = (TextField) container.lookup("#enderecoCliente");
        telefoneCliente = (TextField) container.lookup("#telefoneCliente");

        //  Ação ao clicar em Ok no documento
        okDocumento = (Button) container.lookup("#okDoc");
        okDocumento.setOnAction(event -> consultarCliente(docCliente.getText()));


        // Carrega os campos de dados do veículo
        dadosVeiculo = (Pane) container.lookup("#paneDadosVeiculo");
        placaVeiculo = (TextField) container.lookup("#placaVeiculo");
        marcaVeiculo = (TextField) container.lookup("#marcaVeiculo");
        modeloVeiculo = (TextField) container.lookup("#modeloVeiculo");
        corVeiculo = (TextField) container.lookup("#corVeiculo");
        anoVeiculo = (TextField) container.lookup("#anoVeiculo");
        kmVeiculo = (TextField) container.lookup("#kmVeiculo");
        importadoVeiculo = (CheckBox) container.lookup("#importadoVeiculo");

        //  Ação ao clicar em Ok na placa
        okPlaca = (Button) this.container.lookup("#okPlaca");
        okPlaca.setOnAction(event -> consultarPlaca(placaVeiculo.getText()));

    }

    public Cliente getDadosCliente(){
        return new Cliente(
                docCliente.getText(),
                nomeCliente.getText(),
                emailCliente.getText(),
                telefoneCliente.getText(),
                enderecoCliente.getText()
        );
    }

    public Veiculo getDadosVeiculo(){
        return new Veiculo(
                placaVeiculo.getText(),
                getDadosCliente(),
                marcaVeiculo.getText(),
                modeloVeiculo.getText(),
                anoVeiculo.getText(),
                corVeiculo.getText(),
                Boolean.parseBoolean(importadoVeiculo.getText()),
                (!kmVeiculo.getText().isEmpty()) ? Integer.parseInt(kmVeiculo.getText()) : 0
        );
    }

}
