package atox.controller.atendimento;

import atox.controller.orcamento.Fases;
import atox.exception.CarSystemException;
import atox.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.*;

public class DetalhesAtendimento {

    public Pane paneControles;
    public AnchorPane paneDetAt;
    public TableView<Fases.Fase> tblFases;
    public TableColumn<Fases.Fase, String> colEtapa, colIniciada, colConcluida;

    public Button btnIniciar, btnProxFase, btnFinalizar;
    public Label lblMarcaModelo, lblPlaca, lblCor, lblFase, lblNaoPago;

    private Fases fases;
    private Atendimento atendimento;
    private Veiculo veiculo;
    private Orcamento orcamento;

    private ObservableList<Fases.Fase> lstFases = FXCollections.observableArrayList();

    private Fases.Fase faseAtual;

    public DetalhesAtendimento(Atendimento at) {
        this.atendimento = at;
        this.orcamento = at.getOrcamento();
        this.veiculo = orcamento.getVeiculo();

        if(atendimento.getFase() > 0) {
            at.getOrcamento().getPagamento().setPago(true);

        }

        try {
            this.fases = Fases.carregar(orcamento);
        }catch (CarSystemException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao carregar fases do atendimento! " +e.getMessage());
            alert.show();
        }

    }

    private void updateTela(){
        if(tblFases.isVisible()) {
            lstFases.removeAll();
            lstFases.setAll(fases.getFases());
            tblFases.setItems(lstFases);
        }

        btnIniciar.setDisable(!podeIniciar());
        btnProxFase.setDisable(!podePassarFase());
        btnFinalizar.setDisable(!podeFinalizar());

        String fase = "Não pago";
        lblFase.setTextFill(Color.web("#e73737"));

        if(atendimento.getFase() > 0) {
            if(orcamento.getPagamento().estaPago()){
                if (atendimento.estaFinalizado()) {
                    lblFase.setTextFill(Color.web("#327535"));
                    fase = "Finalizado";
                } else {
                    lblFase.setTextFill(Color.web("#686868"));
                    fase = (faseAtual != null)
                            ? "Serviço de "+ faseAtual.descricao
                            : "Aguardando finalização";
                }
            }else {
                fase = "Não iniciado";
            }
        }

        lblFase.setText(fase);

    }

    public void initialize() {
        faseAtual = fases.getFaseAtual();

        if (tblFases.isVisible()){
            colEtapa.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().descricao));
            colIniciada.setCellValueFactory(item ->
                    new SimpleStringProperty((item.getValue().iniciada) ? "Sim" : "Não")
            );
            colConcluida.setCellValueFactory(item ->
                    new SimpleStringProperty((item.getValue().finalizada) ? "Sim" : "Não")
            );
        }

        lblMarcaModelo.setText(veiculo.getMarca() + "/" + veiculo.getModelo());
        lblPlaca.setText(veiculo.getPlaca());
        lblCor.setText(veiculo.getCor());

        btnIniciar.setOnAction(e -> iniciarAtendimento());
        btnProxFase.setOnAction(e -> proxFaseAtendimento());
        btnFinalizar.setOnAction(e -> finalizarAtendimento());

        updateTela();
    }

    private boolean podeFinalizar(){
        if(faseAtual == null)
            return true;

        if(!fases.estaNaUltimaFase())
            return false;

        return faseAtual.finalizada;

    }

    private boolean podeIniciar(){
        return atendimento.getFase() == 0;
    }

    private boolean podePassarFase(){
        return atendimento.getFase() > 0 && faseAtual != null;
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

    private void iniciarAtendimento(){
        if(!orcamento.getPagamento().estaPago()) {
            if (!confirmIniciar()) return;
        }

        try {
            if (fases.iniciarFaseAtual() && podeIniciar()) {
                if (!Atendimento.updateFase(faseAtual.codigo, atendimento.getId()))
                    throw new CarSystemException("Não foi possível alterar o atendimento");

                faseAtual = fases.getFaseAtual();
                atendimento.setFase(faseAtual.codigo);
                orcamento.getPagamento().setPago(true);

                updateTela();
            } else
                throw new CarSystemException("Não foi possível iniciar a fase atual");

        }catch (CarSystemException e){
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao iniciar o atendimento: " + e.getMessage());
            alert.showAndWait();
        }


    }

    private void proxFaseAtendimento(){
        try {
            if (!fases.proximaFase())
                throw new CarSystemException("Não foi possível ir para a próxima fase");

            faseAtual = fases.getFaseAtual();

            if(fases.estaNaUltimaFase() && faseAtual.finalizada) {
                lstFases.removeAll();
                faseAtual = null;
            }

            updateTela();
        }catch (CarSystemException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }


    private void finalizarAtendimento(){
        try {
            if(!confirmFinalizacao())
                return;

            atendimento.setFinalizado(true);

            if(!Atendimento.finalizar(atendimento.getId()))
                throw new CarSystemException("Não foi possível finalizar o atendimento!");


            Label lblFinalizado = new Label("Atendimento finalizado");
            lblFinalizado.setLayoutX(370);
            lblFinalizado.setLayoutY(20);
            lblFinalizado.setTextFill(Color.web("#3b9019"));
            lblFinalizado.setFont(Font.font("System", 20));
            paneDetAt.getChildren().add(lblFinalizado);

            ((Pane) paneDetAt.getParent()).setPrefHeight(80);

            lblNaoPago.setVisible(false);
            paneControles.setVisible(false);

            updateTela();
        }catch (CarSystemException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private boolean confirmFinalizacao(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setContentText("Deseja finalizar o atendimento?");
        alert.getButtonTypes().setAll(new ButtonType("Sim"), new ButtonType("Não"), new ButtonType("Cancelar"));

        Optional<ButtonType> escolha = alert.showAndWait();

        if(!escolha.isPresent())
            return false;

        return escolha.get().getText().equals("Sim");
    }


}
