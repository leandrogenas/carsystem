package atox.model;

import atox.BancoDeDados;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Peca {

    private int id;
    private int qtd;
    private double valUnit;
    private String nome;
    private String modelo;

    public Peca(int qtd, String nome, String modelo, double valUnit){
        this.qtd = qtd;
        this.nome = nome;
        this.modelo = modelo;
        this.valUnit = valUnit;
    }

    public Peca(int id, int qtd, String nome, String modelo, double valUnit){
        this(qtd, nome, modelo, valUnit);
        this.id = id;
    }

    public int getId() { return id; }
    public int getQuantidade() { return qtd; }
    public String getModelo() { return modelo; }
    public double getValUnit() { return valUnit; }

    public String getNome() { return nome; }
    public void setQuantidade(int qtd) { this.qtd = qtd; }
    public void setNome(String nome) { this.nome = nome; }
    public void setValUnit(double val){ this.valUnit = val; }

    public void setModelo(String modelo) { this.modelo = modelo; }
    public SimpleIntegerProperty idProperty(){ return new SimpleIntegerProperty(id); }
    public SimpleIntegerProperty qtdProperty(){ return new SimpleIntegerProperty(qtd); }
    public SimpleStringProperty nomeProperty(){ return new SimpleStringProperty(nome); }

    public SimpleStringProperty modeloProperty(){ return new SimpleStringProperty(modelo); }

    public static List<Peca> todos(){
        ArrayList<Peca> saida = new ArrayList<>();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM peca");

            while(rSet.next()) {
                Peca peca = new Peca(
                        rSet.getInt("cod_peca"),
                        rSet.getInt("em_estoque"),
                        rSet.getString("nome"),
                        rSet.getString("modelo"),
                        rSet.getDouble("valor_unit")
                );

                saida.add(peca);
            }

            rSet.close();

        }catch (SQLException e){
            System.err.println("Erro ao obter as peças: " + e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }

        return saida;
    }

    public static Peca buscaPorId(int id){
        Peca peca = null;
        try {
            String sql = "SELECT * FROM peca WHERE cod_peca=" + id;
            ResultSet rSet = BancoDeDados.getNewStatement().executeQuery(sql);

            if(!rSet.next())
                return null;

            peca =  new Peca(
                    rSet.getInt("cod_peca"),
                    rSet.getInt("em_estoque"),
                    rSet.getString("nome"),
                    rSet.getString("modelo"),
                    rSet.getDouble("valor_unit")
            );

        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return peca;
    }

    public static boolean inserir(Peca peca) throws Exception {
        String insert = "INSERT INTO peca (nome,modelo,em_estoque,valor_unit) VALUES ('";
        insert += peca.getNome()+"','";
        insert += peca.getModelo()+"','";
        insert += peca.getQuantidade()+"',";
        insert += peca.getValUnit()+")";

        Statement stmt = BancoDeDados.getNewStatement();
        return stmt.execute(insert);

    }

    public static void alterar(Peca peca) throws SQLException {
        String update = "UPDATE peca SET ";
        update += "nome = '"+peca.getNome()+"',";
        update += "modelo = '"+peca.getModelo()+"',";
        update += "em_estoque = '"+peca.getQuantidade()+"',";
        update += "valor_unit = "+peca.getValUnit()+"";
        update += " WHERE cod_peca = '"+peca.getId()+"'";

        BancoDeDados.getNewStatement().execute(update);
    }

    public static boolean excluir(int id){
        String delete = "DELETE FROM peca WHERE cod_peca="+id;

        try {
            return BancoDeDados.getNewStatement().execute(delete);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
    }
}
