package atox.controller.orcamento.novo_orcamento;

public class ServicoEscolhido {
    private int id;
    private String nome;
    private double maoDeObra;

    public ServicoEscolhido(int id, String nome, double maoDeObra){
        this.id = id;
        this.nome = nome;
        this.maoDeObra = maoDeObra;
    }

    public int getId(){ return id; }
    public String getNome(){ return nome; }
    public double getMaoDeObra(){ return maoDeObra; }

}
