package atox.model;


import atox.BancoDeDados;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Orcamento {
    private static String codigoTitle = "Cód",
            veiculoTittle = "Veículo",
            pagamentoTittle = "Forma de Pag.",
            dataHoraTittle = "Data",
            precoTittle = "Preço",
            seguradoraTittle = "Seguradora",
            statusTitle = "Status";

    private int codigo;
    private Veiculo veiculo;
    private Pagamento pagamento;
    private static Date dataInicio, dataFim;
    private String seguradora, statusAtual, preco;

    public Orcamento(int codigo, Veiculo veiculo, Pagamento pagamento, Date dataInicio, Date dataFim, String preco, String seguradora, String statusAtual) {
        this.codigo = codigo;
        this.veiculo = veiculo;
        this.pagamento = pagamento;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.preco = preco;
        this.seguradora = seguradora;
        this.statusAtual = statusAtual;
    }

    public static ArrayList<Orcamento> buscarTodos() {
        ArrayList<Orcamento> orcamentos = new ArrayList<Orcamento>();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM orcamento");
            rSet.next();
            orcamentos.add(new Orcamento(
                    rSet.getInt("cod_orcamento"),
                    Veiculo.buscaPorId(rSet.getInt("cod_veiculo")),
                    Pagamento.buscaPorId(rSet.getInt("cod_pagamento")),
                    rSet.getDate("data_inicio"),
                    rSet.getDate("termino_previsto"),
                    rSet.getString("preco"),
                    rSet.getString("seguradora"),
                    rSet.getString("iniciado")));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        //TODO: retirar esse treco abaixo
        for (int i = 1; i <= 10; i++) {
            orcamentos.add(new Orcamento(i,
                    new Veiculo(),
                    new Pagamento(),
                    new Date(),
                    new Date(),
                    "10.00",
                    "Porto Seguro",
                    "Iniciado"));
        }

        return orcamentos;
    }

    public static String codigoTitle() {
        return codigoTitle;
    }

    public static String veiculoTittle() {
        return veiculoTittle;
    }

    public static String pagamentoTittle() {
        return pagamentoTittle;
    }

    public static String dataHoraTittle() {
        return dataHoraTittle;
    }

    public static String precoTitle() {
        return precoTittle;
    }

    public static String seguradoraTitle() {
        return seguradoraTittle;
    }

    public static String statusTitle() {
        return statusTitle;
    }

    public SimpleIntegerProperty codigoProperty() {
        return new SimpleIntegerProperty(codigo);
    }

    public SimpleStringProperty veiculoProperty() {
        return new SimpleStringProperty(veiculo.getPlaca());
    }

    public SimpleStringProperty pagamentoProperty() {
        return new SimpleStringProperty(pagamento.getForma());
    }

    public SimpleStringProperty dataHoraProperty() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return new SimpleStringProperty(format.format(dataInicio));
    }

    public SimpleStringProperty precoProperty() {
        return new SimpleStringProperty(preco);
    }

    public SimpleStringProperty seguradoraProperty() {
        return new SimpleStringProperty(seguradora);
    }

    public SimpleStringProperty statusProperty() {
        return new SimpleStringProperty(statusAtual);
    }

    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String detailText = "Código: " + codigo + "\nData de início: " + format.format(dataInicio)+"\nTérmino previsto: " + format.format(dataInicio);
        detailText += "\n\nPreço: R$" + preco;
        detailText += "\n\nVeículo:";
        detailText += "\n\tPlaca: " + veiculo.getPlaca() + "\tModelo: " + veiculo.getModelo() + "\tMarca: " + veiculo.getMarca() + "\tCor: " + veiculo.getCor();
        detailText += "\n\nPagamento:";
        detailText += "\n\tForma: " + pagamento.getForma() + "\t\tNúmero de parcelas: " + pagamento.getNParcelas();
        detailText += "\n\nSeguradora: " + seguradora;
        detailText += "\n\nStatus: " + statusAtual;

        return detailText;
    }
}
