package atox.model;

import atox.BancoDeDados;

import java.sql.ResultSet;

public class Pagamento {
    public static String getForma() { return "Forma"; }
    public static String getNParcelas() { return "X vezes"; }

    public static Pagamento buscaPorId(int id){
        Pagamento pagamento = null;
        try {
            String sql = "SELECT * FROM pagamento WHERE cod_pagamento= " + id;
            ResultSet rSet = BancoDeDados.getNewStatement().executeQuery(sql);
            rSet.next();

            pagamento = new Pagamento();
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return pagamento;
    }

}
