package atox.model;

import atox.BancoDeDados;
import atox.exception.CarSystemException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoPeca {

    private Orcamento orcamento;
    private Peca peca;
    private int quantidade;

    private OrcamentoPeca(Orcamento orc, Peca peca, int qtd){
        this.orcamento = orc;
        this.peca = peca;
        this.quantidade = qtd;
    }

    // Getters
    public int getQuantidade() { return quantidade; }
    public Orcamento getOrcamento() { return orcamento; }
    public Peca getPeca() { return peca; }

    // Buscas
    public static List<OrcamentoPeca> buscaPorOrcamento(int codOrc){
        List<OrcamentoPeca> orcPeca = new ArrayList<>();

        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM orcamento_peca WHERE cod_orcamento = '"+codOrc+"'");
            while(rSet.next())
                orcPeca.add(new OrcamentoPeca(
                        Orcamento.buscaPorId(codOrc),
                        Peca.buscaPorId(rSet.getInt("cod_peca")),
                        rSet.getInt("quantidade")
                ));

        }
        catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return orcPeca;

    }

    public static OrcamentoPeca inserir(Orcamento orc, int idPc, int qtd) throws CarSystemException {
        if(orc.getId() == 0)
            throw new CarSystemException("Orçamento não possui ID");
        if(idPc == 0)
            throw new CarSystemException("Peça não possui ID");

        String insert = "INSERT INTO orcamento_peca (cod_orcamento, cod_peca, quantidade) VALUES (" +
                orc.getId() + ", " + idPc + ", " +  qtd + ")";

        try {
            Statement stmt = BancoDeDados.getNewStatement();
            int id = stmt.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);

            return new OrcamentoPeca(
                    orc,
                    Peca.buscaPorId(idPc),
                    qtd
            );
        }catch (SQLException ex){
            throw new CarSystemException("Erro de SQL: " + ex.getMessage());
        }

    }

}
