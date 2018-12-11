package atox.controller.orcamento;

import atox.exception.CarSystemException;
import atox.model.Orcamento;
import atox.model.OrcamentoServico;
import atox.model.Servico;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.*;

public class Fases {

    public class Fase{

        private OrcamentoServico svcExecutado;

        public int codigo;

        public String descricao;
        public boolean iniciada;
        public boolean finalizada;

        Fase(){
            this.codigo = -1;
            this.descricao = "Não há serviços a serem executados!";
        }

        Fase(OrcamentoServico svcExecutado){
            this.svcExecutado = svcExecutado;

            Servico svc = svcExecutado.getServico();
            this.codigo = svc.getId();
            this.descricao = svc.getNome();
            this.iniciada = svcExecutado.estaIniciado();
            this.finalizada = svcExecutado.estaFinalizado();
        }

        public OrcamentoServico getServico(){
            return svcExecutado;
        }



    }
    private Queue<Fase> fasesRestantes = new LinkedList<>();
    private List<Fase> fasesFinalizadas = new ArrayList<>();

    private Orcamento orcamento;
    private Fases(Orcamento orc) {
        this.orcamento = orc;

        // Se não tem fases, devia jogar uma exception
        if(orc.getServicos().isEmpty())
            return;

        for(OrcamentoServico orcSvc: orc.getServicos())
            if(!orcSvc.estaFinalizado())
                fasesRestantes.add(new Fase(orcSvc));
            else
                fasesFinalizadas.add(new Fase(orcSvc));

    }

    public static Fases carregar(Orcamento orc) throws CarSystemException{
        if(orc == null || orc.getId() == 0)
            throw new CarSystemException("Orçamento inválido, impossível buscar fasesRestantes");

        return new Fases(orc);

    }

    private boolean iniciar(Fase fase) {
        fase.iniciada = OrcamentoServico.iniciar(fase.getServico(), orcamento.getId());

        System.out.println("[orç "+ orcamento.getId() +"]" +
                ((fase.iniciada)
                        ? " Serviço "+ fase.descricao +" iniciado"
                        : " Erro ao iniciar o serviço "+ fase.descricao)
        );

        return fase.iniciada;

    }

    private boolean finalizar(Fase fase){
        fase.finalizada = OrcamentoServico.finalizar(fase.getServico(), orcamento.getId());

        System.out.println("[orç "+ orcamento.getId() +"]" +
                ((fase.finalizada)
                        ? " Serviço "+ fase.descricao +" finalizado"
                        : " Erro ao finalizar o serviço "+ fase.descricao)
        );

        return fase.finalizada;
    }

    public boolean proximaFase(){
        Fase faseAtual = fasesRestantes.peek();

        // Caso não tenha fases restantes
        if(faseAtual == null) {
            System.out.println("Não há fases restantes");
            return false;
        }

        // Caso a fase não esteja iniciada
        //  inicia
        if(!faseAtual.iniciada)
            return iniciar(faseAtual);

        // Caso a fase não esteja finalizada
        //  finaliza
        if(!faseAtual.finalizada)
            return finalizar(faseAtual);


        // Se não estiver na última fase
        //  carrega a próxima
        if(!estaNaUltimaFase()) {
            if (!confirmProximaFase())
                return false;

            fasesFinalizadas.add(fasesRestantes.poll());

            Fase proxFase = fasesRestantes.peek();
            if(proxFase == null) {
                System.out.println("Não há próxima fase");
                return false;
            }

            return iniciar(proxFase);

        }

        // Se estiver na última fase
        return faseAtual.finalizada;


    }

    public boolean iniciarFaseAtual(){
        Fase faseAtual = fasesRestantes.peek();
        if(faseAtual == null){
            System.out.println("Não há fases restantes");
            return false;
        }

        return iniciar(faseAtual);
    }

    public boolean finalizarFaseAtual(){
        Fase faseAtual = fasesRestantes.peek();
        if(faseAtual == null){
            System.out.println("Não há fases restantes");
            return false;
        }

        return finalizar(faseAtual);
    }

    public boolean estaNaUltimaFase(){
        return fasesRestantes.size() == 1;
    }

    public int fasesRestantes(){
        return fasesRestantes.size();
    }

    private boolean confirmProximaFase(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setContentText("Deseja iniciar o próximo serviço?");
        alert.getButtonTypes().setAll(new ButtonType("Sim"), new ButtonType("Não"), new ButtonType("Cancelar"));

        Optional<ButtonType> escolha = alert.showAndWait();

        if(!escolha.isPresent())
            return false;

        return escolha.get().getText().equals("Sim");
    }

    public Fase getFaseAtual(){ return fasesRestantes.peek(); }
    public Queue<Fase> getFasesRestantes(){ return fasesRestantes; }
    public List<Fase> getFasesFinalizadas() { return fasesFinalizadas; }
    public List<Fase> getFases(){
        List<Fase> saida = new ArrayList<>();
        saida.addAll(fasesRestantes);
        saida.addAll(fasesFinalizadas);
        return saida;
    }

}
