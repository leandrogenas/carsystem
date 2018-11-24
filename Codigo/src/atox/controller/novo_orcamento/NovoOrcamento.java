package atox.controller.novo_orcamento;

import atox.exception.CarSystemException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class NovoOrcamento {
    @FXML
    private VBox container;

    private int numPasso;
    private Passos passoAtual;

    private PassoClienteVeiculo passoCV;
    private PassoPecas passoPecas;
    private PassoServicos passoServicos;
    private PassoFinalizacao passoFinalizacao;


    @FXML
    private void initialize(){
        passoCV = new PassoClienteVeiculo((AnchorPane) container.lookup("#passoCV"));
        passoPecas = new PassoPecas((AnchorPane) container.lookup("#passoPecas"));
        passoServicos = new PassoServicos((AnchorPane) container.lookup("#passoServicos"));
        passoFinalizacao = new PassoFinalizacao((AnchorPane) container.lookup("#passoFinalizacao"));


        numPasso = 1;
        passoAtual = passoCV;
        passoAtual.setVisible(true);

        //MaskFieldUtil.cpfCnpjMask(cpfField);
    }

    public void proximo(ActionEvent ev){ navegacao(true); }
    public void anterior(ActionEvent ev){ navegacao(false); }

    private void navegacao(boolean praFrente){
        try {
            passoAtual.validarPasso();
            passoAtual.setVisible(false);

            switch (numPasso) {
                case 1: passoAtual = passoPecas; break;
                case 2: passoAtual = (praFrente) ? passoServicos : passoCV; break;
                case 3: passoAtual = (praFrente) ? passoFinalizacao : passoPecas; break;
                case 4: passoAtual = passoServicos;
            }
            scroll((praFrente) ? ++numPasso : --numPasso);
            passoAtual.setVisible(true);
        }catch (CarSystemException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public void finalizarOrcamento(){

    }

    private void scroll(int passo){
        container.setLayoutY(-530 * (passo-1));
    }

    private static void mensagem(String msg, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Novo Orçamento");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }


    public void liberaCamposCliente(){
        /*
        Pane paneFields = (Pane) passoClienteVeiculo.lookup("#paneFieldsCliente");

        Iterator it = paneFields.getChildren().iterator();
        while(it.hasNext()){
            Node noAtual = (Node) it.next();
            if(!(noAtual instanceof TextField))
                continue;

            ((TextField) noAtual).setDisable(false);
        }
        */
    }

    public boolean docOk() {
        /*
        Cliente cli = Cliente.buscaPorDocumento(Documento.Tipo.CPF, docCliente.getText());

        if (cli == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cliente não encontrado!");
            alert.setHeaderText("Deseja cadastrar o cliente?");
            alert.getButtonTypes().setAll(new ButtonType("Sim"), new ButtonType("Não"), new ButtonType("Cancelar"));

            Optional<ButtonType> escolha = alert.showAndWait();
            if(escolha.get().getText().equals("Sim")) {
                liberaCamposCliente();
            }else if(escolha.get().getText().equals("Não")) {
                // Deve ser um cliente apenas com o documento e nome
                cli = new Cliente();
                cli.setCPF(docCliente.getText());
                nomeCliente.setEditable(true);
            }

            return false;

        }


        for (Node noAtual : passoClienteVeiculo.getChildren()) {
            if (!(noAtual instanceof TextField))
                continue;

            TextField txt = (TextField) noAtual;
            switch (txt.getId()) {
                case "nome":
                    txt.setText(cli.getNome());
                    break;
                case "email":
                    txt.setText(cli.getEmail());
                    break;
                case "endereco":
                    txt.setText(cli.getEndereco());
                    break;
                case "telefone":
                    txt.setText(cli.getTelefone());
                    break;
                case "celular":
                    txt.setText(cli.getCelular());
                    break;
            }
        }


        return true;
        */
        return true;
    }
}
