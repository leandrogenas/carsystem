package atox.model;

import atox.BancoDeDados;
import atox.exception.CarSystemException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoServico {

    private Servico servico;
    private double valTotal;
    private boolean finalizado;
    private boolean iniciado;

    private OrcamentoServico(Servico svc, double valTotal, boolean iniciado, boolean finalizado){
        this.servico = svc;
        this.valTotal = valTotal;
        this.iniciado = iniciado;
        this.finalizado = finalizado;
    }

    // Getters
    public Servico getServico() { return servico; }
    public double getValTotal() { return valTotal; }
    public boolean estaFinalizado(){ return finalizado; }
    public boolean estaIniciado(){ return iniciado; }

    // Buscas
    public static List<OrcamentoServico> buscaPorOrcamento(int codOrc){
        List<OrcamentoServico> orcSvc = new ArrayList<>();

        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM orcamento_servico WHERE cod_orcamento = '"+codOrc+"'");

            while(rSet.next())
                orcSvc.add(new OrcamentoServico(
                        Servico.buscaPorId(rSet.getInt("cod_servico")),
                        rSet.getDouble("valor_total"),
                        rSet.getBoolean("iniciado"),
                        rSet.getBoolean("finalizado")
                ));

        }
        catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return orcSvc;

    }

    public static boolean iniciar(OrcamentoServico svc, int codOrc){
        String update = "UPDATE orcamento_servico SET iniciado=1 WHERE cod_orcamento="+ codOrc +" AND cod_servico="+ svc.getServico().getId();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            return (stmt.executeUpdate(update, Statement.RETURN_GENERATED_KEYS) > 0);
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("Erro na finalização do serviço do orçamento: " + e.getMessage());
            return false;
        }
    }

    public static boolean finalizar(OrcamentoServico svc, int codOrc){
        String update = "UPDATE orcamento_servico SET finalizado=1 WHERE cod_orcamento="+ codOrc +" AND cod_servico="+ svc.getServico().getId();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            return (stmt.executeUpdate(update, Statement.RETURN_GENERATED_KEYS) > 0);
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("Erro na finalização do serviço do orçamento: " + e.getMessage());
            return false;
        }
    }

    public static OrcamentoServico inserir(Orcamento orc, int idSvc, double valTotal) throws CarSystemException {
        if(orc.getId() == 0)
            throw new CarSystemException("Orçamento não possui ID");
        if(idSvc == 0)
            throw new CarSystemException("Serviço não possui ID");

        String insert = "INSERT INTO orcamento_servico (cod_orcamento, cod_servico, valor_total, iniciado, finalizado) VALUES (" +
                orc.getId() + ", " + idSvc + ", " +  valTotal + ", 0, 0)";

        try {
            Statement stmt = BancoDeDados.getNewStatement();
            int id = stmt.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);

            return new OrcamentoServico(
                Servico.buscaPorId(idSvc),
                valTotal,
                    false,
                    false
            );
        }catch (SQLException ex){
            throw new CarSystemException("Erro de SQL: " + ex.getMessage());
        }

    }

}
