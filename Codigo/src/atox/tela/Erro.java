package atox.tela;

public class Erro extends Tela {

    private String msg;

    public Erro(String s) {
        msg = s;
    }

    @Override
    public String getNomeFXML() {
        return "/fxml/pagina_erro.fxml";
    }

    @Override
    public String getTitulo() {
        return "Erro";
    }

}
