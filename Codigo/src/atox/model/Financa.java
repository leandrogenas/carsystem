package atox.model;

import atox.BancoDeDados;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Financa {
    private static String orcamentoTitle = "Preço",
            veiculoTitle = "Veículo",
            inicioTitle = "Início Atendimento",
            terminoTitle = "Fim Atendimento";
    private Pagamento pagamento;
    private Orcamento orcamento;
    private Atendimento atendimento;
    private Veiculo veiculo;

    public Financa(Pagamento pagamento, Orcamento orcamento, Atendimento atendimento) {
        this.pagamento = pagamento;
        this.orcamento = orcamento;
        this.atendimento = atendimento;
        veiculo = orcamento.getVeiculo();
    }

    public static String precoTitle() {
        return orcamentoTitle;
    }
    public static String veiculoTitle() { return veiculoTitle; }
    public static String inicioTitle() { return inicioTitle; }
    public static String terminoTitle() { return terminoTitle; }

    public SimpleStringProperty precoProperty() { return new SimpleStringProperty(Double.toString(orcamento.getPreco())); }
    public SimpleStringProperty veiculoProperty() { return new SimpleStringProperty(veiculo.getPlaca()); }
    public SimpleStringProperty inicioProperty() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        if(atendimento.getInicio() != null)
            return new SimpleStringProperty(format.format(atendimento.getInicio()));
        else
            return new SimpleStringProperty("Não iniciado");
    }
    public SimpleStringProperty terminoProperty() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        if(atendimento.getFim() != null)
            return new SimpleStringProperty(format.format(atendimento.getFim()));
        else
            return new SimpleStringProperty("Não finalizado");
    }


    public double getPreco() { return orcamento.getPreco(); }

    public static List<Financa> buscarPorPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        List<Financa> financas = new ArrayList<Financa>();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            String selectQuery = "select atd.cod_atendimento,atd.fase,atd.data_inicio,atd.data_termino, atd.finalizado,\n" +
                    "\torcPag.* from atendimento as atd\n" +
                    "inner join (\n" +
                    "\tselect orc.cod_orcamento, orc.cod_cliente, orc.cod_veiculo,orc.preco,orc.data_criado [data_orc],orc.termino_previsto,orc.seguradora,orc.iniciado,orc.cod_pagamento,\n" +
                    "\t\tpag.forma_pagamento, pag.num_parcelas, pag.pago from orcamento as orc\n" +
                    "\tinner join pagamento as pag\n" +
                    "\ton orc.cod_pagamento = pag.cod_pagamento\n" +
                    ") as orcPag\n" +
                    "on atd.cod_orcamento = orcPag.cod_orcamento\n";
            selectQuery += "where data_termino > '"+dataInicial.toString()+"'\n";
            selectQuery += "\tand data_termino < '"+dataFinal.toString()+"'";
            ResultSet rSet = stmt.executeQuery(selectQuery);
            while(rSet.next()) {

                Pagamento pagamento = new Pagamento(
                        rSet.getInt("cod_pagamento"),
                        rSet.getString("forma_pagamento"),
                        rSet.getInt("num_parcelas"),
                        rSet.getBoolean("pago")
                );

                Orcamento orcamento = new Orcamento(
                        rSet.getInt("cod_orcamento"),
                        Veiculo.buscaPorId(rSet.getInt("cod_veiculo")),
                        Cliente.buscaPorId(rSet.getInt("cod_cliente")),
                        pagamento,
                        rSet.getDate("data_inicio"),
                        rSet.getDate("termino_previsto"),
                        rSet.getDouble("preco"),
                        rSet.getString("seguradora"),
                        rSet.getString("iniciado")
                );

                Atendimento atendimento = new Atendimento(
                        rSet.getInt("cod_atendimento"),
                        orcamento,
                        rSet.getInt("fase"),
                        rSet.getDate("data_inicio"),
                        rSet.getDate("termino_previsto"),
                        rSet.getBoolean("finalizado")
                    );

                financas.add(new Financa(pagamento, orcamento, atendimento));
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return financas;
    }

    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String detailText = "\n\nOrçamento:";
        detailText += "\n\nCódigo: "+orcamento.getId()+"\t\tPreço: R$" + orcamento.getPreco();

        return detailText;
    }
}
