package atox.controller.orcamento;

import atox.exception.CarSystemException;
import atox.model.Orcamento;
import atox.model.OrcamentoServico;
import atox.model.Servico;

import java.util.List;
import java.util.Stack;

public class Fases {

    public class Fase{

        private OrcamentoServico svcExecutado;
        private int idOrc;

        public int idx;
        public int codigo;
        public String descricao;
        public boolean iniciada;
        public boolean finalizada;

        Fase(int idx, OrcamentoServico svcExecutado){
            this.idx = idx;
            this.idOrc = idOrc;
            this.svcExecutado = svcExecutado;

            Servico svc = svcExecutado.getServico();
            this.codigo = svc.getId();
            this.descricao = svc.getNome();
            this.iniciada = svcExecutado.estaIniciado();
            this.finalizada = svcExecutado.estaFinalizado();
        }

        public boolean iniciarFase(Fase fase) {
            boolean altBd = OrcamentoServico.iniciar(svcExecutado, idOrc);

            if (altBd)
                System.out.println("[orç ]"+ idOrc +" Serviço "+ descricao +" iniciado");
            else
                System.out.println("[orç ]"+ idOrc +" Erro ao iniciar o serviço "+ descricao);

            return altBd;
        }

        public boolean finalizar(){
            boolean altBd = OrcamentoServico.finalizar(svcExecutado, idOrc);

            if (altBd)
                System.out.println("[orç ]"+ idOrc +" Serviço "+ descricao +" finalizado");
            else
                System.out.println("[orç ]"+ idOrc +" Erro ao finalizar o serviço "+ descricao);

            return altBd;
        }


    }

    private Stack<Fase> fases = new Stack<>();

    private Fases(Orcamento orc) throws CarSystemException {
        List<OrcamentoServico> svcs = orc.getServicos();

        if(svcs.isEmpty())
            throw new CarSystemException("Orçamento não possui serviços");

        int idx = 0;
        for(OrcamentoServico orcSvc: orc.getServicos()){
            Servico svc = orcSvc.getServico();
            fases.push(new Fase(idx++, orcSvc));

        }

    }

    public static Fases carregar(Orcamento orc){
        return null;
    }

}
