package atox.model;

import atox.BancoDeDados;
import atox.exception.CarSystemException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pagamento {

    private int id;
    private String forma;
    private int numParcelas;
    private boolean pago;

    public Pagamento(String forma, int numParcelas){
        this.forma = forma;
        this.numParcelas = numParcelas;
        this.pago = false;
    }

    public Pagamento(int id, String forma, int numParcelas, boolean pago){
        this(forma, numParcelas);
        this.id = id;
        this.pago = pago;
    }

    // Getters
    public String getForma() { return forma; }
    public int getNumParcelas() { return numParcelas; }
    public int getId() { return id; }
    public boolean estaPago(){ return pago; }

    // Setters
    public void setNumParcelas(int numParcelas){ this.numParcelas = numParcelas; }
    public void setPago(boolean pago){ this.pago = pago; }

    public static Pagamento buscaPorId(int id){
        Pagamento pagamento = null;
        try {
            String sql = "SELECT * FROM pagamento WHERE cod_pagamento= " + id;
            ResultSet rSet = BancoDeDados.getNewStatement().executeQuery(sql);
            rSet.next();

            pagamento = new Pagamento(
                    rSet.getInt("cod_pagamento"),
                    rSet.getString("forma_pagamento"),
                    rSet.getInt("num_parcelas"),
                    rSet.getBoolean("pago")
            );
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return pagamento;
    }

    public static Pagamento inserir(Pagamento pag) throws CarSystemException {
        if(pag.getForma().isEmpty())
            throw new CarSystemException("Forma de pagamento vazia!");
        if(pag.getNumParcelas() == 0)
            pag.setNumParcelas(1);

        String insert = "INSERT INTO pagamento (forma_pagamento, num_parcelas, pago) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = BancoDeDados.getNewPreparedStatement(insert);
            stmt.setString(1, pag.getForma());
            stmt.setInt(2, pag.getNumParcelas());
            stmt.setBoolean(3, pag.estaPago());

            int linhas = stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            return new Pagamento(
                    id,
                    pag.getForma(),
                    pag.getNumParcelas(),
                    pag.estaPago()
            );
        }catch (SQLException ex){
            throw new CarSystemException("Erro de SQL: " + ex.getMessage());
        }

    }
}
