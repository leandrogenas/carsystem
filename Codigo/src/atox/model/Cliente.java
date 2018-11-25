package atox.model;


import atox.BancoDeDados;
import atox.utils.Mock;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.Statement;

public class Cliente {

    private int id;
    private String documento;
    private String nome;
    private String endereco;
    private String telefone;
    private String celular;
    private String email;

    public Cliente(String documento){
        this.documento = documento;
    }

    public Cliente(String documento, String nome, String email, String telefone, String celular, String endereco) {
        this.documento = documento;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Cliente(int id, String documento, String nome, String email, String telefone, String celular, String endereco) {
        this.documento = documento;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.telefone = telefone;
        this.endereco = endereco;
        this.id = id;
    }

    public SimpleIntegerProperty idProperty(){ return new SimpleIntegerProperty(id); }
    public SimpleStringProperty documentoProperty(){ return new SimpleStringProperty(documento); }
    public SimpleStringProperty nomeProperty(){ return new SimpleStringProperty(nome); }
    public SimpleStringProperty emailProperty(){ return new SimpleStringProperty(email); }
    public SimpleStringProperty telefoneProperty(){ return new SimpleStringProperty(telefone); }
    public SimpleStringProperty celularProperty(){ return new SimpleStringProperty(celular); }
    public SimpleStringProperty enderecoProperty(){ return new SimpleStringProperty(endereco); }

    public int getId(){ return id; }
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public String getTelefone() { return telefone; }
    public String getCelular() { return celular; }
    public String getEmail() { return email; }
    public String getEndereco() { return endereco; }

    public void setNome(String nome) { this.nome = nome; }
    public void setDocumento(String doc){ this.documento = doc; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setCelular(String celular) { this.celular = celular; }
    public void setEmail(String email) { this.email = email; }

    public static Cliente buscaPorId(int id){
        Cliente cliente = null;

        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM cliente WHERE cod_cliente="+id+"");
            rSet.next();
            cliente = new Cliente(
                    rSet.getInt("cod_cliente"),
                    rSet.getString("nr_documento"),
                    rSet.getString("nome"),
                    rSet.getString("email"),
                    rSet.getString("telefone"),
                    rSet.getString("celular"),
                    rSet.getString("endereco")
            );
        }
        catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return cliente;
    }

    public static Cliente buscaPorDocumento(String doc){
        Cliente cliente = null;

        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM cliente WHERE nr_documento = '"+doc+"'");
            rSet.next();
            cliente = new Cliente(
                    rSet.getInt("cod_cliente"),
                    rSet.getString("nr_documento"),
                    rSet.getString("nome"),
                    rSet.getString("email"),
                    rSet.getString("telefone"),
                    rSet.getString("celular"),
                    rSet.getString("endereco")
            );
        }
        catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return cliente;
    }

    public static boolean inserir(Cliente cliente) throws Exception {
        String insert = "INSERT INTO Cliente (nr_documento,nome,email,telefone,celular,endereco) VALUES ('";
        insert += cliente.getDocumento()+"','";
        insert += cliente.getNome()+"','";
        insert += cliente.getEmail()+"','";
        insert += cliente.getTelefone()+"','";
        insert += cliente.getCelular()+"','";
        insert += cliente.getEndereco()+"')";

        Statement stmt = BancoDeDados.getNewStatement();
        return stmt.execute(insert);
    }

    public static boolean alterar(Cliente cliente) throws Exception {
        String update = "UPDATE cliente SET ";
        update += "nome = '"+cliente.getNome()+"',";
        update += "email = '"+cliente.getEmail()+"',";
        update += "telefone = '"+cliente.getTelefone()+"',";
        update += "celular = '"+cliente.getCelular()+"',";
        update += "endereco = '"+cliente.getEndereco()+"'";
        update += " WHERE nr_documento = '"+cliente.getDocumento()+"'";

        Statement stmt = BancoDeDados.getNewStatement();
        return stmt.execute(update);
    }
}

