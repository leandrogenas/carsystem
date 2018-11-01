package atox.tela;

import java.util.ArrayList;

public class TelaFactory {


    public static Tela getPagina(String tela, ArrayList<String> parametros){
        try {
            if (tela.equals("principal"))
                return new Principal();
            else if(tela.equals("novo_orcamento"))
                return new NovoOrcamento();
            else
                return new Erro("Página não encontrada!");
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

}
