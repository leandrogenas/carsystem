package atox.controller.orcamento.novo_orcamento;

public class PecaUtilizada {
    private int id;
    private String peca;
    private int qtd;
    private double unit;
    private double total;

    public PecaUtilizada(int id, String peca, int qtd, double valUnit){
        this.id = id;
        this.peca = peca;
        this.qtd = qtd;
        this.unit = valUnit;
        this.total = valUnit * qtd;
    }

    public int getId(){ return id; }
    public String getPeca(){ return peca; }
    public int getQtd(){ return qtd; }
    public double getValUnit(){ return unit; }
    public double getValTotal(){ return total; }

}
