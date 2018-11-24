package atox.model;


import atox.BancoDeDados;

import java.sql.ResultSet;
import java.sql.Statement;

public class Cliente {
    private String CPF;
    private String nome;
    private String endereco;
    private String telefone;
    private String celular;
    private String email;

    public Cliente(String cpf, String nome, String endereco, String telefone, String celular, String email) {
        setCPF(cpf);
        setNome(nome);
        setEndereco(endereco);
        setTelefone(telefone);
        setCelular(celular);
        setEmail(email);
    }

    public String getNome() { return nome; }
    public String getCPF() { return CPF; }
    public String getTelefone() { return telefone; }
    public String getCelular() { return celular; }
    public String getEmail() { return email; }
    public String getEndereco() { return endereco; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCPF(String CPF) { this.CPF = CPF; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setCelular(String celular) { this.celular = celular; }
    public void setEmail(String email) { this.email = email; }


    public static Cliente buscaPorCPF(String cpf){
        Cliente cliente = null;

        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM Cliente WHERE cpf = '"+cpf+"'");
            rSet.next();
            cliente = new Cliente(
                rSet.getString("cpf"),
                rSet.getString("nome"),
                rSet.getString("endereco"),
                rSet.getString("telefone"),
                rSet.getString("celular"),
                rSet.getString("email")
            );
        }
        catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return cliente;
    }

    public static boolean inserir(Cliente cliente) throws Exception {
        String insert = "INSERT INTO Cliente (cpf,nome,email,telefone,celular,endereco) VALUES ('";
        insert += cliente.getCPF()+"','";
        insert += cliente.getNome()+"','";
        insert += cliente.getEmail()+"','";
        insert += cliente.getTelefone()+"','";
        insert += cliente.getCelular()+"','";
        insert += cliente.getEndereco()+"')";

        Statement stmt = BancoDeDados.getNewStatement();
        return stmt.execute(insert);
    }

    public static boolean alterar(Cliente cliente) throws Exception {
        String update = "UPDATE Cliente SET ";
        update += "nome = '"+cliente.getNome()+"',";
        update += "email = '"+cliente.getEmail()+"',";
        update += "telefone = '"+cliente.getTelefone()+"',";
        update += "celular = '"+cliente.getCelular()+"',";
        update += "endereco = '"+cliente.getEndereco()+"'";
        update += " WHERE cpf = '"+cliente.getCPF()+"'";

        Statement stmt = BancoDeDados.getNewStatement();
        return stmt.execute(update);
    }

}

