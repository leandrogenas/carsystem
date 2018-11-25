package atox.model;

import atox.BancoDeDados;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Servico {

    private int id;
    private String nome;
    private String descricao;

    public Servico(String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
    }

    public Servico(int id, String nome, String descricao){
        this(nome, descricao);
        this.id = id;
    }

    public SimpleIntegerProperty idProperty(){ return new SimpleIntegerProperty(id); }
    public SimpleStringProperty nomeProperty(){ return new SimpleStringProperty(nome); }
    public SimpleStringProperty descricaoProperty(){ return new SimpleStringProperty(descricao); }

    public String getDescricao() { return descricao; }
    public String getNome() { return nome; }
    public int getId() { return id; }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setNome(String nome) { this.nome = nome; }

    public static List<Servico> todos(){
        ArrayList<Servico> saida = new ArrayList<>();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM servico");

            while(rSet.next()) {
                Servico svc = new Servico(
                        rSet.getInt("cod_servico"),
                        rSet.getString("nome"),
                        rSet.getString("descricao")
                );

                saida.add(svc);
            }

            rSet.close();

        }catch (SQLException e){
            System.err.println("Erro ao obter os fornecedores");
        }catch (Exception e){
            e.printStackTrace();
        }

        return saida;
    }

    public static boolean inserir(Servico svc) throws Exception {
        String insert = "INSERT INTO servico (nome,descricao) VALUES ('";
        insert += svc.getNome()+"','";
        insert += svc.getDescricao()+"')";

        Statement stmt = BancoDeDados.getNewStatement();
        return stmt.execute(insert);
    }

    public static boolean excluir(int id){
        String delete = "DELETE FROM servico WHERE cod_servico="+id;

        try {
            return BancoDeDados.getNewStatement().execute(delete);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static boolean alterar(Servico svc){
        String update = "UPDATE servico SET ";
        update += "nome = '"+svc.getNome()+"',";
        update += "descricao = '"+svc.getDescricao()+"'";
        update += " WHERE cod_servico = '"+svc.getId()+"'";

        try {
            return BancoDeDados.getNewStatement().execute(update);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
    }

}
