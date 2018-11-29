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
    private String nome;
    private String modelo;

    public Peca(int qtd, String nome, String modelo){
        this.qtd = qtd;
        this.nome = nome;
        this.modelo = modelo;
    }

    public Peca(int id, int qtd, String nome, String modelo){
        this(qtd, nome, modelo);
        this.id = id;
    }

    public int getId() { return id; }
    public int getQtd() { return qtd; }
    public String getModelo() { return modelo; }
    public String getNome() { return nome; }

    public void setQtd(int qtd) { this.qtd = qtd; }
    public void setNome(String nome) { this.nome = nome; }
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
                        rSet.getInt("quantidade"),
                        rSet.getString("nome"),
                        rSet.getString("modelo")
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
                    rSet.getInt("quantidade"),
                    rSet.getString("nome"),
                    rSet.getString("modelo")
            );

        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return peca;
    }

}
