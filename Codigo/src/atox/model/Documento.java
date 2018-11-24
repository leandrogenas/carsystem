package atox.model;

import static atox.utils.Validators.isCNPJ;
import static atox.utils.Validators.isCPF;

public class Documento {

    public enum Tipo{ CPF, CNPJ }

    private Tipo tp;
    private String doc;

    public Documento(Tipo tp, String doc){
        this.doc = doc;
        this.tp = tp;
    }

    public String getNumero(){ return doc; }

    public String tipo(){ return tp.equals(Tipo.CPF) ? "CPF" : "CNPJ"; }

    public boolean validar(){
        return (tp.equals(Tipo.CPF)) ? isCPF(doc) : isCNPJ(doc);

    }

}
