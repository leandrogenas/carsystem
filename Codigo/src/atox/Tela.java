package atox;

import javafx.fxml.FXMLLoader;

public enum Tela {
    PRINCIPAL("principal", "Car System"),
    INICIO("inicio", "Início"),
    FINANCEIRO("financeiro", "Financeiro"),
    HISTORICO_ORCAMENTOS("historico-orcamentos", "Histórico de orçamentos"),
    HISTORICO_ATENDIMENTOS("historico-atendimentos", "Histórico de atendimentos"),
    SELECAO_CADASTROS("selecao-cadastros", "Selecione um cadastro"),
    INICIAR_ATENDIMENTO("inicia-atendimento", "Iniciar atendimentos"),
    NOVO_ORCAMENTO("novo-orcamento", "Novo orçamento"),
    ESTOQUE("estoque", "Estoque"),
    CADASTRO_CLIENTE("cadastro-cliente", "Cadastro de clientes"),
    CADASTRO_FORNECEDOR("cadastro-fornecedor", "Cadastro de fornecedores"),
    CADASTRO_VEICULO("cadastro-veiculo", "Cadastro de veículos");

    private final String nomeFXML;
    private final String titulo;

    private Tela(String nomeFXML, String titulo){
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
