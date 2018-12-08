package atox.model;

import atox.BancoDeDados;
import atox.exception.CarSystemException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoServico {

    private Orcamento orcamento;
    private Servico servico;
    private double valTotal;

    private OrcamentoServico(Orcamento orc, Servico svc, double valTotal){
        this.orcamento = orc;
        this.servico = svc;
        this.valTotal = valTotal;
    }

    // Getters
    public Orcamento getOrcamento() { return orcamento; }
    public Servico getServico() { return servico; }
    public double getValTotal() { return valTotal; }

    // Buscas
    public static List<OrcamentoServico> buscaPorOrcamento(int codOrc){
        List<OrcamentoServico> orcSvc = new ArrayList<>();

        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM orcamento_peca WHERE cod_orcamento = '"+codOrc+"'");

            while(rSet.next())
                orcSvc.add(new OrcamentoServico(
                        Orcamento.buscaPorId(codOrc),
                        Servico.buscaPorId(rSet.getInt("cod_servico")),
                        rSet.getDouble("valor_total")
                ));

        }
        catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return orcSvc;

    }

    public static OrcamentoServico inserir(Orcamento orc, int idSvc, double valTotal) throws CarSystemException {
        if(orc.getId() == 0)
            throw new CarSystemException("Orçamento não possui ID");
        if(idSvc == 0)
            throw new CarSystemException("Serviço não possui ID");

        String insert = "INSERT INTO orcamento_servico (cod_orcamento, cod_servico, valor_total) VALUES (" +
                orc.getId() + ", " + idSvc + ", " +  valTotal + ")";

        try {
            Statement stmt = BancoDeDados.getNewStatement();
            int id = stmt.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);

            return new OrcamentoServico(
                orc,
                Servico.buscaPorId(idSvc),
                valTotal
            );
        }catch (SQLException ex){
            throw new CarSystemException("Erro de SQL: " + ex.getMessage());
        }

    }

}
