package atox.controller.novo_orcamento;

import atox.exception.CarSystemException;
import atox.model.Peca;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

class PecaUtilizada {
    private int id;
    private String peca;
    private int qtd;
    private double unit;
    private double total;

    public PecaUtilizada(int id, String peca, int qtd, double valUnit){
        this.id = id;
        this.peca = peca;
        this.qtd = qtd;
        this.unit = valUnit;
        this.total = valUnit * qtd;
    }

    public int getId(){ return id; }
    public String getPeca(){ return peca; }
    public int getQtd(){ return qtd; }
    public double getValUnit(){ return unit; }
    public double getValTotal(){ return total; }

}

public class PassoPecas extends Passos{
    private ScrollPane panePecas;
    private Label lblNenhumaPeca;

    private ChoiceBox<String> cbPecas;
    private TextField qtdPeca;
    private Button btnAdcPeca;

    private Label lblEstoqueNegativo;

    private List<PecaUtilizada> pecasUtilizadas = new ArrayList<>();

    PassoPecas(NovoOrcamento contr, AnchorPane pane){
        super(pane);
    }

    @Override
    public boolean passoValido(){
        return true;
    }

    @Override
    public void carregarElementos(){
        panePecas = (ScrollPane) container.lookup("#panePecas");
        lblNenhumaPeca = (Label) ((AnchorPane) panePecas.getContent()).lookup("#lblNenhumaPeca");

        cbPecas = (ChoiceBox) container.lookup("#cbPecas");
        qtdPeca = (TextField) container.lookup("#qtdPeca");
        lblEstoqueNegativo = (Label) container.lookup("#lblEstoqueNegativo");

        btnAdcPeca = (Button) container.lookup("#btnAdcPeca");
        btnAdcPeca.setOnAction(ev -> adcPeca());

        for(Peca peca: Peca.todos())
            cbPecas.getItems().add(String.format("%03d", peca.getId()) + "-" + peca.getNome() + " (" + peca.getModelo() + ")");

        //cbPecas.setValue(cbPecas.getItems().get(0));

        lblEstoqueNegativo.setVisible(false);
    }

    private void adcPeca() {
        lblNenhumaPeca.setVisible(false);

        String strPeca = cbPecas.getValue();

        Peca pecaSel = Peca.buscaPorId(Integer.parseInt(strPeca.substring(0, 3)));
        try {
            int qtdPecas = Integer.parseInt(qtdPeca.getText());

            if(qtdPecas == 0)
                throw new CarSystemException("Informe uma quantidade!");
            if (pecaSel == null)
                throw new CarSystemException("Erro ao encontrar a peça");


            pecasUtilizadas.add(new PecaUtilizada(pecaSel.getId(), pecaSel.getNome(), qtdPecas, pecaSel.getValUnit()));

            Label lblPeca = new Label("- " + pecaSel.getNome() + " (" + pecaSel.getModelo() + "), " + qtdPecas + " unidades");
            lblPeca.setLayoutY((pecasUtilizadas.size() - 1) * 20);

            ((AnchorPane) panePecas.getContent()).getChildren().add(lblPeca);
        }catch (CarSystemException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro!");
            alerta.setHeaderText(null);
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();

        }

    }

    public List<PecaUtilizada> getPecas(){
        return pecasUtilizadas;
    }

}
