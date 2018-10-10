package atox.view;

import atox.controller.PrincipalController;
import atox.view.PaginaInterface;

public class PaginaFactory {

    public static String PAG_PRINCIPAL = "principal";
    public static String PAG_ORCAMENTO = "orcamento";

    public static PaginaInterface getPagina(String nomePag){
        try {
            if (nomePag.equals(PAG_PRINCIPAL))
                return new PaginaPrincipal();
                //        else if(nomePag.equals(PAG_ORCAMENTO))
                //            return new PaginaOrcamento();
            else
                return new PaginaErro("Página não encontrada!");
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

}
