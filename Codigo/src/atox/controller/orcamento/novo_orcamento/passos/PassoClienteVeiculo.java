package atox.controller.orcamento.novo_orcamento.passos;

import atox.exception.CarSystemException;
import atox.model.Cliente;
import atox.model.Veiculo;
import atox.controller.orcamento.novo_orcamento.NovoOrcamento;
import atox.utils.MaskFieldUtil;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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


    private Veiculo veiculo;
    private Cliente cliente;


    PassoClienteVeiculo(NovoOrcamento contr, AnchorPane pane){
        super(contr, pane);

        MaskFieldUtil.placaMask(placaVeiculo);
        MaskFieldUtil.cpfMask(docCliente);
    }

    @Override
    public boolean passoValido() {
        try {
            if(!clienteValido())
                throw new CarSystemException("Preencha o nome do cliente!");
            if(!veiculoValido())
                throw new CarSystemException("Preencha os campos obrigatórios (com asterisco) do veículo!");

            cliente.setNome(nomeCliente.getText());
            cliente.setDocumento(docCliente.getText());
            cliente.setEmail(emailCliente.getText());
            cliente.setEndereco(enderecoCliente.getText());
            cliente.setTelefone(telefoneCliente.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atenção");
            alert.setHeaderText(null);

            if(cliente.getId() == 0) {
                cliente = Cliente.inserir(cliente);

                alert.setContentText("Cliente inserido no BD!");
                alert.showAndWait();
            }else {
                Cliente.alterar(cliente);
            }

            veiculo.setPlaca(placaVeiculo.getText());
            veiculo.setMarca(marcaVeiculo.getText());
            veiculo.setModelo(modeloVeiculo.getText());
            veiculo.setCor(corVeiculo.getText());
            veiculo.setAno(anoVeiculo.getText());
            veiculo.setImportado(importadoVeiculo.isSelected());
            veiculo.setKm(Integer.valueOf(kmVeiculo.getText()));

            if(veiculo.getId() == 0) {
                veiculo = Veiculo.inserir(veiculo);

                alert.setContentText("Veículo inserido no BD!");
                alert.showAndWait();
            }else {
                Veiculo.alterar(veiculo);
            }

            return true;
        }catch (Exception e){
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
        cliente = Cliente.buscaPorDocumento(doc);

        // Caso o cliente não exista, pergunta se quer cadastrar
        if (cliente == null) {
            if (promptCadastro("Cliente não encontrado!", "Deseja cadastrar o cliente?")){
                liberaCamposCliente();

                cliente = new Cliente(docCliente.getText());

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
        nomeCliente.setText(cliente.getNome());
        emailCliente.setText(cliente.getEmail());
        enderecoCliente.setText(cliente.getEndereco());
        telefoneCliente.setText(cliente.getTelefone());

    }

    public void consultarPlaca(String placa){
        limpaCamposVeiculo();

        // Busca o cliente
        veiculo = Veiculo.buscaPorPlaca(placa);

        liberaCamposVeiculo();
        if(veiculo != null){
            marcaVeiculo.setText(veiculo.getMarca());
            modeloVeiculo.setText(veiculo.getModelo());
            corVeiculo.setText(veiculo.getCor());
            anoVeiculo.setText(veiculo.getAno());
            kmVeiculo.setText(Integer.toString(veiculo.getKm()));
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

        //  Ação ao clicar em Ok na lblPlaca
        okPlaca = (Button) this.container.lookup("#okPlaca");
        okPlaca.setOnAction(event -> consultarPlaca(placaVeiculo.getText()));

    }

    @Override
    public void exibir() {

    }

    public Cliente getDadosCliente(){
        return cliente;
    }

    public Veiculo getDadosVeiculo(){
        return veiculo;
    }
}
