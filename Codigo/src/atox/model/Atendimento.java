package atox.model;

import atox.BancoDeDados;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Atendimento {
    private static String codigoTitle = "Cód",
            orcamentoTitle = "Orcamento",
            faseTitle = "Fase",
            inicioTitle = "Inicio",
            terminoTitle = "Fim Previsto";

    private int id;
    private Orcamento orcamento;
    private int fase;
    private Date dataInicio, dataFim;

    public Atendimento(int id, Orcamento orcamento, int fase, Date dataInicio, Date dataFim) {
        this.id = id;
        this.orcamento = orcamento;
        this.fase = fase;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public static ArrayList<Atendimento> todos() {
        ArrayList<Atendimento> atendimentos = new ArrayList<Atendimento>();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM atendimento");

            while(rSet.next())
                atendimentos.add(new Atendimento(
                    rSet.getInt("cod_atendimento"),
                    Orcamento.buscaPorId(rSet.getInt("cod_orcamento")),
                    rSet.getInt("lblFase"),
                    rSet.getDate("data_inicio"),
                    rSet.getDate("data_termino")));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return atendimentos;
    }


    public static String codigoTitle() {
        return codigoTitle;
    }

    public static String orcamentoTitle() {
        return orcamentoTitle;
    }

    public static String faseTitle() {
        return faseTitle;
    }

    public static String inicioTitle() {
        return inicioTitle;
    }

    public static String terminoTitle() {
        return terminoTitle;
    }

    public SimpleIntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public SimpleStringProperty orcamentoProperty() {
        return new SimpleStringProperty(Double.toString(orcamento.getPreco()));
    }

    public SimpleStringProperty inicioProperty() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        if(dataInicio != null)
            return new SimpleStringProperty(format.format(dataInicio));
        else
            return new SimpleStringProperty("Não iniciado");
    }

    public SimpleStringProperty terminoProperty() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        if(dataFim != null)
            return new SimpleStringProperty(format.format(dataFim));
        else
            return new SimpleStringProperty("Não finalizado");
    }

    public Date getInicio() { return dataInicio; }

    public Date getFim() { return dataFim; }
    public int getId() { return id; }
    public int getFase(){ return fase; }
    public Orcamento getOrcamento(){ return orcamento; }
    public void setFase(int fase){ this.fase = fase; }

    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String detailText = "Código: " + id + "\nData de início: " + format.format(dataInicio)+"\nTérmino previsto: " + format.format(dataFim);
        detailText += "\n\nOrçamento:";
        detailText += "\n\nCódigo: "+orcamento.getId()+"\t\tPreço: R$" + orcamento.getPreco();
        detailText += "\n\nFase: " + fase;

        return detailText;
    }

    public static boolean updateFase(Atendimento atendimento) throws SQLException {
        String update = "UPDATE atendimento SET lblFase=" +atendimento.getFase()+ " WHERE cod_atendimento=" +atendimento.getId();

        Statement stmt = BancoDeDados.getNewStatement();
        return stmt.execute(update);

    }

}
