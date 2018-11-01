package atox;

import java.util.HashMap;

public class DadosSessao {
    private HashMap<String, String> dados = new HashMap<>();

    private static DadosSessao instancia = new DadosSessao();

    public static DadosSessao getInstance() {
        return instancia;
    }

    private DadosSessao() {
    }

    public void add(String chave, String valor){
        dados.put(chave, valor);
    }

    public String get(String chave, String valor){
        return dados.get(chave);
    }

    public boolean deletar(String chave){
        String ret = dados.remove(chave);
        return (ret == null);
    }

}
