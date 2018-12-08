package atox.model;


import atox.BancoDeDados;
import atox.exception.CarSystemException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Orcamento {
    private static String codigoTitle = "Cód",
            clienteTitle = "Cliente",
            veiculoTitle = "Veículo",
            pagamentoTitle = "Forma de Pag.",
            inicioTitle = "Início",
            precoTitle = "Preço",
            seguradoraTitle = "Seguradora",
            statusTitle = "Status";

    private int id;
    private Cliente cliente;
    private Veiculo veiculo;
    private Pagamento pagamento;

    private List<OrcamentoPeca> pecas = new ArrayList<>();
    private List<OrcamentoServico> servicos = new ArrayList<>();

    private Date dataInicio, dataFim;
    private String seguradora, statusAtual;
    private double preco;

    public Orcamento(Veiculo veiculo, Cliente cli, Pagamento pagamento, double preco, String seguradora) {
        this.veiculo = veiculo;
        this.cliente = cli;
        this.pagamento = pagamento;
        this.dataInicio = new Date();
        this.dataFim = null;
        this.preco = preco;
        this.seguradora = seguradora;
        this.statusAtual = "0";
    }

    public Orcamento(int id, Veiculo veiculo, Cliente cli, Pagamento pagamento, Date dtInicio, Date dtTermino, double preco, String seguradora, String status) {
        this(veiculo, cli, pagamento, preco, seguradora);
        this.id = id;
        this.dataInicio = dtInicio;
        this.dataFim = dtTermino;
        this.statusAtual = status;
    }


    public static String codigoTitle() {
        return codigoTitle;
    }

    public static String clienteTitle(){ return clienteTitle; }
    public static String veiculoTitle() {
        return veiculoTitle;
    }
    public static String pagamentoTitle() {
        return pagamentoTitle;
    }
    public static String inicioTitle() {
        return inicioTitle;
    }
    public static String precoTitle() {
        return precoTitle;
    }
    public static String seguradoraTitle() {
        return seguradoraTitle;
    }
    public static String statusTitle() {
        return statusTitle;
    }
    public SimpleIntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public SimpleStringProperty clienteProperty(){ return new SimpleStringProperty(cliente.getNome() + ", doc: " + cliente.getDocumento()); }
    public SimpleStringProperty veiculoProperty() { return new SimpleStringProperty(veiculo.getPlaca()); }
    public SimpleStringProperty pagamentoProperty() {
        return new SimpleStringProperty(pagamento.getForma());
    }
    public SimpleStringProperty precoProperty() {
        return new SimpleStringProperty(Double.toString(preco));
    }
    public SimpleStringProperty seguradoraProperty() {
        return new SimpleStringProperty(seguradora);
    }
    public SimpleStringProperty statusProperty() {
        return new SimpleStringProperty(statusAtual);
    }
    public SimpleStringProperty inicioProperty() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return new SimpleStringProperty(format.format(dataInicio));
    }

    public void addPeca(OrcamentoPeca pc){
        if(pc != null)
            this.pecas.add(pc);
    }
    public void addServico(OrcamentoServico svc){
        if(svc != null)
            this.servicos.add(svc);
    }

    public void setPecas(List<OrcamentoPeca> pecas){
        this.pecas = pecas;
    }
    public void setServicos(List<OrcamentoServico> svcs){
        this.servicos = svcs;
    }

    public int getId() { return id; }
    public double getPreco() { return preco; }
    public Veiculo getVeiculo() { return veiculo; }
    public Cliente getCliente(){ return cliente; }
    public Pagamento getPagamento(){ return pagamento; }
    public Date getDataCriado(){ return dataInicio; }
    public Date getDataFim(){ return dataFim; }
    public String getSeguradora(){ return seguradora; }
    public String getStatus(){ return statusAtual; }
    public List<OrcamentoPeca> getPecas(){ return pecas; }
    public List<OrcamentoServico> getServicos(){ return servicos; }


    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String detailText = "Código: " + id + "\nData de início: " + format.format(dataInicio)+"\nTérmino previsto: " + format.format(dataFim);
        detailText += "\n\nPreço: R$" + preco;
        detailText += "\n\nVeículo:";
        detailText += "\n\tPlaca: " + veiculo.getPlaca() + "\tModelo: " + veiculo.getModelo() + "\tMarca: " + veiculo.getMarca() + "\tCor: " + veiculo.getCor();
        detailText += "\n\nPagamento:";
        detailText += "\n\tForma: " + pagamento.getForma() + "\t\tNúmero de parcelas: " + pagamento.getNumParcelas();
        detailText += "\n\nSeguradora: " + seguradora;
        detailText += "\n\nStatus: " + statusAtual;

        return detailText;
    }

    public static ArrayList<Orcamento> todos() {
        ArrayList<Orcamento> orcamentos = new ArrayList<Orcamento>();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM orcamento");
            rSet.next();
            Orcamento orcamento = new Orcamento(
                    rSet.getInt("cod_orcamento"),
                    Veiculo.buscaPorId(rSet.getInt("cod_veiculo")),
                    Cliente.buscaPorId(rSet.getInt("cod_cliente")),
                    Pagamento.buscaPorId(rSet.getInt("cod_pagamento")),
                    rSet.getDate("data_inicio"),
                    rSet.getDate("termino_previsto"),
                    rSet.getDouble("preco"),
                    rSet.getString("seguradora"),
                    rSet.getString("iniciado"));

            orcamento.setPecas(OrcamentoPeca.buscaPorOrcamento(rSet.getInt("cod_orcamento")));
            orcamento.setServicos(OrcamentoServico.buscaPorOrcamento(rSet.getInt("cod_orcamento")));

            orcamentos.add(orcamento);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return orcamentos;
    }


    public static Orcamento buscaPorId(int id){
        Orcamento orcamento = null;
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM orcamento WHERE cod_orcamento = "+id);
            rSet.next();
            orcamento = new Orcamento(
                    rSet.getInt("cod_orcamento"),
                    Veiculo.buscaPorId(rSet.getInt("cod_veiculo")),
                    Cliente.buscaPorId(rSet.getInt("cod_cliente")),
                    Pagamento.buscaPorId(rSet.getInt("cod_pagamento")),
                    rSet.getDate("data_inicio"),
                    rSet.getDate("termino_previsto"),
                    rSet.getDouble("preco"),
                    rSet.getString("seguradora"),
                    rSet.getString("iniciado"));

            orcamento.setPecas(OrcamentoPeca.buscaPorOrcamento(rSet.getInt("cod_orcamento")));
            orcamento.setServicos(OrcamentoServico.buscaPorOrcamento(rSet.getInt("cod_orcamento")));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return orcamento;
    }

    public static Orcamento inserir(Orcamento orc, double valTotal) throws CarSystemException{
        if(orc.getVeiculo().getId() == 0)
            throw new CarSystemException("Veículo do orçamento inválido");
        if(orc.getPagamento().getId() == 0)
            throw new CarSystemException("Pagamento do orçãmento inválido");

        String codCli = (orc.getCliente().getId() == 0) ? null : Integer.toString(orc.getCliente().getId());

        SimpleDateFormat dtFmtBd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String insertOrc = "INSERT INTO orcamento (cod_veiculo, cod_cliente, preco, data_criado, termino_previsto, seguradora, iniciado, cod_pagamento) VALUES" +
                    "(?, ?, ?, ?, null, ?, 0, ?)";

            PreparedStatement prepStmt = BancoDeDados.getNewPreparedStatement(insertOrc);
            prepStmt.setInt(1, orc.getVeiculo().getId());
            prepStmt.setString(2, codCli);
            prepStmt.setDouble(3, valTotal);
            prepStmt.setString(4, dtFmtBd.format(orc.getDataCriado()));
            prepStmt.setString(5, orc.getSeguradora());
            prepStmt.setInt(6, orc.getPagamento().getId());

            int linhas = prepStmt.executeUpdate();

            ResultSet rs = prepStmt.getGeneratedKeys();
            rs.next();

            int idOrc = rs.getInt(1);

            if(idOrc == 0)
                throw new CarSystemException("Erro ao inserir o orçamento");


            String insertAtend = "INSERT INTO atendimento (cod_orcamento, fase, data_inicio, data_termino) VALUES (" +idOrc+ ", 0, null, null)";
            Statement stmt = BancoDeDados.getNewStatement();
            if(stmt.executeUpdate(insertAtend, Statement.RETURN_GENERATED_KEYS) < 1)
                throw new CarSystemException("Erro ao criar atendimento");

            return new Orcamento(
                    idOrc,
                    orc.getVeiculo(),
                    orc.getCliente(),
                    orc.getPagamento(),
                    orc.getDataCriado(),
                    orc.getDataFim(),
                    orc.getPreco(),
                    orc.getSeguradora(),
                    orc.getStatus()
            );
        }catch (SQLException ex){
            throw new CarSystemException("Erro de SQL: " + ex.toString());

        }

    }

    public static void baixaEstoque(Orcamento orc) throws CarSystemException{
        if(orc.getPecas().isEmpty())
            throw new CarSystemException("Não há peças utilizadas para dar baixa!");

        for(OrcamentoPeca orcPc: orc.getPecas()) {
            String update = "UPDATE peca SET em_estoque=em_estoque - " + orcPc.getQuantidade() + " WHERE cod_peca = " + orcPc.getPeca().getId();
            try {
                Statement stmt = BancoDeDados.getNewStatement();
                stmt.execute(update);
            }catch (SQLException ex){
                System.out.println("Erro ao dar baixa na peça " + orcPc.getPeca().getNome() + ", " + ex.getMessage());
            }

        }

    }

}
