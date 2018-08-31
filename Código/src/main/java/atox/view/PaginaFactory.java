package atox.view;

import atox.controller.PrincipalController;
import atox.view.PaginaInterface;

public class PaginaFactory {

    public static String PAG_PRINCIPAL = "principal";
    public static String PAG_ORCAMENTO = "orcamento";

    public static PaginaInterface getPagina(String nomePag) throws Exception{
        if(nomePag.equals(PAG_PRINCIPAL))
            return new PaginaPrincipal();
//        else if(nomePag.equals(PAG_ORCAMENTO))
//            return new PaginaOrcamento();
        else
            throw new Exception("Página não encontrada!");

    }

}
