package atox.model;

import atox.BancoDeDados;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Fornecedor {

    private int id;
    private String nome;
    private String cnpj;
    private String endereco;
    private String telefone;

    public Fornecedor(String nome, String cnpj, String telefone, String endereco){
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Fornecedor(int id, String nome, String cnpj, String telefone, String endereco){
        this(nome, cnpj, telefone, endereco);
        this.id = id;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public void setNome(String nome){ this.nome = nome; }
    public void setCNPJ(String cnpj){ this.cnpj = cnpj; }
    public void setTelefone(String tel){ this.telefone = tel; }

    public SimpleStringProperty nomeProperty(){ return new SimpleStringProperty(nome); }
    public SimpleStringProperty enderecoProperty(){ return new SimpleStringProperty(endereco); }
    public SimpleStringProperty telefoneProperty(){ return new SimpleStringProperty(telefone); }
    public SimpleStringProperty cnpjProperty(){ return new SimpleStringProperty(cnpj); }

    public int getId(){ return id; }
    public String getNome() { return nome; }
    public String getCNPJ() { return cnpj; }
    public String getEndereco() { return endereco; }
    public String getTelefone() { return telefone; }

    public static List<Fornecedor> todos(){
        ArrayList<Fornecedor> saida = new ArrayList<>();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM fornecedor");

            while(rSet.next()) {
                Fornecedor forn = new Fornecedor(
                        rSet.getInt("cod_fornecedor"),
                        rSet.getString("nome"),
                        rSet.getString("cnpj"),
                        rSet.getString("telefone"),
                        rSet.getString("endereco")
                );

                saida.add(forn);
            }

            rSet.close();

        }catch (SQLException e){
            System.err.println("Erro ao obter os fornecedores");
        }catch (Exception e){
            e.printStackTrace();
        }

        return saida;
    }

    public static boolean inserir(Fornecedor fornecedor) throws Exception {
        String insert = "INSERT INTO fornecedor (cnpj,nome,telefone,endereco) VALUES ('";
        insert += fornecedor.getCNPJ()+"','";
        insert += fornecedor.getNome()+"','";
        insert += fornecedor.getTelefone()+"','";
        insert += fornecedor.getEndereco()+"')";

        Statement stmt = BancoDeDados.getNewStatement();
        return stmt.execute(insert);

    }

    public static void alterar(Fornecedor forn) throws SQLException {
        String update = "UPDATE fornecedor SET ";
        update += "nome = '"+forn.getNome()+"',";
        update += "cnpj = '"+forn.getCNPJ()+"',";
        update += "telefone = '"+forn.getTelefone()+"',";
        update += "endereco = '"+forn.getEndereco()+"'";
        update += " WHERE cod_fornecedor = '"+forn.getId()+"'";

        BancoDeDados.getNewStatement().execute(update);

    }

    public static boolean excluir(int id){
        String delete = "DELETE FROM fornecedor WHERE cod_fornecedor="+id;

        try {
            return BancoDeDados.getNewStatement().execute(delete);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
    }

}
