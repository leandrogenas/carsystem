package atox;

import atox.controller.Principal;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public final class CarSystem {
    private static final int LARG_TELA = 900;
    private static final int ALT_TELA = 600;

    private static CarSystem instancia;

    private Stage stageCarSystem;
    private Scene scenePrincipal;

    private Label labelTitulo;
    private AnchorPane paneConteudo;

    public enum Tipo {
        PRINCIPAL("principal", "Car System"),
        INICIAL("inicial", "Início"),
        FINANCEIRO("financeiro", "Financeiro"),
        HISTORICO_ORCAMENTOS("historico-orcamentos", "Histórico de orçamentos"),
        HISTORICO_ATENDIMENTOS("historico-atendimentos", "Histórico de atendimentos"),
        SELECAO_CADASTROS("selecao-cadastros", "Selecione um cadastro"),
        ATENDIMENTOS("atendimentos", "Atendimentos"),
        NOVO_ORCAMENTO("novo-orcamento", "Novo orçamento"),
        ESTOQUE("estoque", "Estoque"),
        CADASTRO_CLIENTE("clientes", "Clientes"),
        CADASTRO_FORNECEDOR("fornecedores", "Fornecedores"),
        CADASTRO_VEICULO("veiculos", "Veículos"),
        CADASTRO_SERVICO("servicos", "Serviços");

        private final String nomeFXML;
        private final String titulo;

        Tipo(String nomeFXML, String titulo){
            this.nomeFXML = nomeFXML;
            this.titulo = titulo;
        }

        public String getTitulo(){
            return titulo;
        }

        public String getNomeFXML(){
            return "tela_" + nomeFXML + ".fxml";
        }

        public FXMLLoader getFXMLLoader(){
            return new FXMLLoader(getClass().getResource("/atox/view/" + getNomeFXML()));
        }

    }

    private CarSystem(Stage stage){
        stageCarSystem = stage;

        if(setupStage())
            mudaTela(Tipo.INICIAL);
    }

    private boolean setupStage(){
        try {
            FXMLLoader loader = Tipo.PRINCIPAL.getFXMLLoader();
            loader.setController(new Principal(this));

            scenePrincipal = new Scene(loader.load(), LARG_TELA, ALT_TELA);
            labelTitulo = (Label) scenePrincipal.lookup("#lblTitulo");
            paneConteudo = (AnchorPane) scenePrincipal.lookup("#paneConteudo");


            stageCarSystem.setTitle("Car System");
            stageCarSystem.setScene(scenePrincipal);
            stageCarSystem.setResizable(false);
            stageCarSystem.show();
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Erro ao carregar tela principal, fechando...");

            return false;
        }

        return true;

    }

    public void mudaTela(Tipo para){
        labelTitulo.setText(para.getTitulo());
        try {
            paneConteudo.getChildren().clear();
            paneConteudo.getChildren().add(para.getFXMLLoader().load());
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Erro ao carregar tela_" + para.nomeFXML + ".fxml");
        }
    }

    public static CarSystem getInstancia() {
        if(instancia == null)
            System.err.println("CarSystem não foram inicializadas previamente!");

        return instancia;

    }

    public static CarSystem init(Stage stage){
        if(instancia == null)
            instancia = new CarSystem(stage);

        return instancia;

    }

}
