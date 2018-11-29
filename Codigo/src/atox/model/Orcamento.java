package atox.model;


import java.util.Date;

public class Orcamento {

    public static String codigoTitle = "Cód",
            veiculoTittle = "Veículo",
            pagamentoTittle = "Forma de Pag.",
            inicioTittle = "Início",
            precoTittle = "Preço",
            seguradoraTitle = "Seguradora",
            statusTitle = "Status";

    private int id;
    private double valor;
    private Veiculo veiculo;
    private Pagamento pagamento;
    private Date dataHora;

    public void Orcamento(Veiculo veic, Cliente cli, Pagamento pag) {

    }

    public void setValor(double val){
        this.valor = val;

    }

    public void setOrcamento(){

    }



}
