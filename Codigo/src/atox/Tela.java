package atox;

import javafx.fxml.FXMLLoader;

public enum Tela {
    PRINCIPAL("principal", "Car System", 1),
    INICIAL("inicial", "Início", 1),
    FINANCEIRO("financeiro", "Financeiro", 1),
    HISTORICO_ORCAMENTOS("historico_orcamentos", "Histórico de orçamentos", 1),
    HISTORICO_ATENDIMENTOS("historico_atendimentos", "Histórico de atendimentos", 1),
    SELECAO_CADASTROS("selecao-cadastros", "Selecione um cadastro", 1),
    INICIAR_ATENDIMENTO("iniciar-atendimento", "Iniciar atendimentos", 1),
    NOVO_ORCAMENTO("novo-orcamento", "Novo orçamento", 4),
    ESTOQUE("estoque", "Estoque", 1),
    CADASTRO_CLIENTE("cadastro-cliente", "Cadastro de clientes", 1),
    CADASTRO_FORNECEDOR("cadastro-fornecedor", "Cadastro de fornecedores", 1),
    CADASTRO_VEICULO("cadastro-veiculo", "Cadastro de veículos", 1);

    private final String nomeFXML;
    private final String titulo;
    private final int qtdPags;

    private Tela(String nomeFXML, String titulo, int qtdPags){
        this.nomeFXML = nomeFXML;
        this.titulo = titulo;
        this.qtdPags = qtdPags;
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
