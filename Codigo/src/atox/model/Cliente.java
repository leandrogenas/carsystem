package atox.model;

public class Cliente {
    private String CPF;
    private String nome;
    private String endereco;
    private String telefone;
    private String celular;
    private String email;

    public Cliente() {}
    public Cliente(String cpf, String nome, String endereco, String telefone, String celular, String email) {
        setCPF(cpf);
        setNome(nome);
        setEndereco(endereco);
        setTelefone(telefone);
        setCelular(celular);
        setEmail(email);
    }

    public String getNome() {return nome; }

    public String getCPF() {
        return CPF;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCelular() { return celular; }

    public String getEmail() { return email; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

