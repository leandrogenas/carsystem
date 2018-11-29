package atox.model;


import atox.BancoDeDados;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Atendimento {
    private static String codigoTitle = "Cód",
            orcamentoTittle = "Orcamento",
            faseTittle = "Fase",
            inicioTittle = "Inicio",
            terminoTittle = "Fim Previsto";

    private int codigo;
    private Orcamento orcamento;
    private String fase;
    private static Date dataInicio, dataFim;

    public Atendimento(int codigo, Orcamento orcamento, String fase, Date dataInicio, Date dataFim) {
        this.codigo = codigo;
        this.orcamento = orcamento;
        this.fase = fase;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public static ArrayList<Atendimento> buscarTodos() {
        ArrayList<Atendimento> atendimentos = new ArrayList<Atendimento>();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM orcamento");
            rSet.next();
            atendimentos.add(new Atendimento(
                    rSet.getInt("cod_orcamento"),
                    Orcamento.buscaPorId(rSet.getInt("cod_pagamento")),
                    rSet.getString("fase"),
                    rSet.getDate("data_inicio"),
                    rSet.getDate("termino_previsto")));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        //TODO: retirar esse treco abaixo
        for (int i = 1; i <= 10; i++) {
            atendimentos.add(new Atendimento(i,
                    new Orcamento(),
                    "fase",
                    new Date(),
                    new Date()));
        }

        return atendimentos;
    }

    public static String codigoTitle() {
        return codigoTitle;
    }

    public static String orcamentoTittle() {
        return orcamentoTittle;
    }

    public static String faseTittle() {
        return faseTittle;
    }

    public static String inicioTittle() {
        return inicioTittle;
    }

    public static String terminoTittle() {
        return terminoTittle;
    }

    public SimpleIntegerProperty codigoProperty() {
        return new SimpleIntegerProperty(codigo);
    }

    public SimpleStringProperty orcamentoProperty() {
        return new SimpleStringProperty(orcamento.getPreco());
    }

    public SimpleStringProperty faseProperty() {
        return new SimpleStringProperty(fase);
    }

    public SimpleStringProperty inicioProperty() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return new SimpleStringProperty(format.format(dataInicio));
    }

    public SimpleStringProperty terminoProperty() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return new SimpleStringProperty(format.format(dataFim));
    }

    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String detailText = "Código: " + codigo + "\nData de início: " + format.format(dataInicio)+"\nTérmino previsto: " + format.format(dataFim);
        detailText += "\n\nOrçamento:";
        detailText += "\n\nCódigo: "+orcamento.getId()+"\t\tPreço: R$" + orcamento.getPreco();
        detailText += "\n\nFase: " + fase;

        return detailText;
    }
}
