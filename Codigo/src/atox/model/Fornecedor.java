package atox.model;

import atox.BancoDeDados;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Fornecedor {

    private String nome;
    private String cnpj;
    private String endereco;
    private String telefone;

    public Fornecedor(String nome, String cnpj, String telefone){
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public SimpleStringProperty nomeProperty(){ return new SimpleStringProperty(nome); }
    public SimpleStringProperty enderecoProperty(){ return new SimpleStringProperty(endereco); }
    public SimpleStringProperty telefoneProperty(){ return new SimpleStringProperty(telefone); }
    public SimpleStringProperty cnpjProperty(){ return new SimpleStringProperty(cnpj); }

    public String getNome() {
        return nome;
    }
    public String getCNPJ() {
        return cnpj;
    }
    public String getEndereco() {
        return endereco;
    }
    public String getTelefone() {
        return telefone;
    }

    public static ArrayList<Fornecedor> todos(){
        ArrayList<Fornecedor> saida = new ArrayList<>();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM fornecedor");

            while(rSet.next()) {
                Fornecedor forn = new Fornecedor(
                        rSet.getString("nome"),
                        rSet.getString("cnpj"),
                        rSet.getString("telefone")
                );
                forn.setEndereco(rSet.getString("endereco"));

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
}
