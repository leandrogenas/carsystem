package atox.controller.atendimento;

import atox.controller.orcamento.Fases;
import atox.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.*;

public class DetalhesAtendimento {

    public TableView<Fases.Fase> tblFases;
    public TableColumn<Fases.Fase, String> colEtapa, colIniciada, colConcluida;

    public Button btnIniciar, btnProxFase, btnFinalizar;
    public Label lblMarcaModelo, lblPlaca, lblCor, lblFase;

    private Fases fases;
    private Atendimento atendimento;
    private Veiculo veiculo;
    private Orcamento orcamento;
    private Fases.Fase faseAtual;

    public DetalhesAtendimento(Atendimento at) {
        this.atendimento = at;
        this.orcamento = at.getOrcamento();
        this.veiculo = orcamento.getVeiculo();
        this.fases = Fases.carregar(orcamento);

    }

    private Fases.Fase getFaseAtual(){
        return null;
    }

    private void carregaTabFases(){
        ObservableList<Fases.Fase> fases =FXCollections.observableArrayList();
        /*
        for(Map.Entry<Integer, Fases.Fase> fase: fases.entrySet())
            fases.add(fase.getValue());
        */
        tblFases.setItems(fases);
    }

    public void initialize(){
        carregaTabFases();

        colEtapa.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().descricao));
        colIniciada.setCellValueFactory(item ->
                new SimpleStringProperty((item.getValue().iniciada) ? "Sim" : "Não")
        );
        colConcluida.setCellValueFactory(item ->
                new SimpleStringProperty((item.getValue().finalizada) ? "Sim" : "Não")
        );

        lblMarcaModelo.setText(veiculo.getMarca() + "/" + veiculo.getModelo());
        lblPlaca.setText(veiculo.getPlaca());
        lblCor.setText(veiculo.getCor());

        lblFase.setText(updateFase());

        btnIniciar.setDisable(!(faseAtual == null));
        btnIniciar.setOnAction(e -> iniciarAtendimento());
        btnProxFase.setOnAction(e -> proxFaseAtendimento());
        btnProxFase.setDisable((faseAtual == null));
        btnFinalizar.setOnAction(e -> finalizarAtendimento());
        btnFinalizar.setDisable(!btnFinalizar.isDisabled());

    }

    public String updateFase(){
        if(orcamento.getPagamento().estaPago()){
            if(faseAtual.codigo == 0) {
                lblFase.setTextFill(Color.web("#3f5fb8"));
                return "Não iniciado";
            }

            lblFase.setTextFill(Color.web("#327535"));
            return faseAtual.descricao;
        }

        lblFase.setTextFill(Color.web("#e73737"));
        return "Não pago";

    }

    private boolean confirmIniciar(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setContentText("Atendimento não está pago! Deseja iniciar mesmo assim?");
        alert.getButtonTypes().setAll(new ButtonType("Sim"), new ButtonType("Não"), new ButtonType("Cancelar"));

        Optional<ButtonType> escolha = alert.showAndWait();

        if(!escolha.isPresent())
            return false;

        return escolha.get().getText().equals("Sim");
    }

    /*
    private Fase primeiraFase(){
        Map.Entry<Integer, Fase> entry = fases.entrySet().iterator().next();
        return entry.getValue();
    }
    */

    private void iniciarAtendimento(){
        /*
        if(!orcamento.getPagamento().estaPago()){
            if(!confirmIniciar())
                return;

            Fase proxFase;
            if(atendimento.getFase() == 0)
                proxFase = primeiraFase();
            else
                proxFase =
        }else {
            if((faseAtual.idx + 1) == fases.size())
                atendimento.setFase(fases.get(faseAtual.idx + 1).codigo);
        }

        try {
            Atendimento.updateFase(atendimento);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }

    private void proxFaseAtendimento(){

    }

    private void finalizarAtendimento(){

    }

}
