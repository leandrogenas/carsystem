package atox.utils;

import atox.controller.orcamento.Fases;
import atox.controller.orcamento.novo_orcamento.passos.Passos;
import atox.model.*;
import atox.exception.CarSystemException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;
import org.slf4j.impl.SimpleLoggerFactory;

import java.io.StringWriter;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnvioEmail{
    private static final String HOST = "smtp.gmail.com";
    private static final String PORTA = "465";
    private static final String LOGIN = "carsystematox@gmail.com";
    private static final String SENHA = "4t0x102030";

    private static EnvioEmail instancia;

    private String para;
    private Message msg;

    private EnvioEmail(String para) throws MessagingException{
        Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.socketFactory.port", PORTA);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", PORTA);

        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(LOGIN, SENHA);
                }
        });

        session.setDebug(true);

        this.msg = new MimeMessage(session);
        this.msg.setFrom(new InternetAddress(LOGIN));
        this.msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));

        this.para = para;

    }

    public void setAssunto(String assunto) throws MessagingException{
        msg.setSubject(assunto);
    }

    public void setCorpo(String corpo) throws MessagingException{
        msg.setContent(corpo, "text/html; charset=utf-8");
    }

    public void enviar() throws MessagingException{

        Transport.send(msg);
    }

    public static void finalizarAtendimento(Atendimento at){
        try{
            EnvioEmail envio = new EnvioEmail(at.getOrcamento().getCliente().getEmail());

            StringBuilder sb = new StringBuilder();
            sb.append("<style> div{ font-family: monospaced; }</style>");
            sb.append("<div>");
            sb.append("<h5>O seu atendimento na BoutCar foi finalizado!</h5> <br />");
            sb.append("</div>");

            envio.setAssunto("Seu carro está pronto!");
            envio.setCorpo(sb.toString());
            envio.enviar();
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public static void enviarStatus(Atendimento at){
        try{
            EnvioEmail envio = new EnvioEmail(at.getOrcamento().getCliente().getEmail());

            Fases fase = Fases.carregar(at.getOrcamento());

            StringBuilder sb = new StringBuilder();
            sb.append("<style> div{ font-family: monospaced; }</style>");
            sb.append("<div>");
            sb.append("<h5>Boa tarde!</h5> <br />");
            sb.append("<p>O serviço de ");
            sb.append(fase.getFaseAtual().descricao);
            sb.append(" foi terminado na BoutCar funilaria</p>");
            sb.append("</div>");

            envio.setAssunto("Atualização do status do seu veículo");
            envio.setCorpo(sb.toString());
            envio.enviar();
        }catch (MessagingException | CarSystemException e){
            e.printStackTrace();
        }
    }

    public static void enviarOrcamento(Orcamento orc, String para){
        try {
            EnvioEmail envio = new EnvioEmail(para);

            Veiculo veic = orc.getVeiculo();

            StringBuilder sb = new StringBuilder();
            sb.append("<style> div{ font-family: monospaced; }</style>");
            sb.append("<div>");
            sb.append("<h4>Segue seu orçamento feito conosco na BoutCar</h4> <br />");
            sb.append("+ Cliente ");
            sb.append("-    Nome: "+ orc.getCliente().getNome() + "<br />");
            sb.append("-    Doc: "+ orc.getCliente().getDocumento() + "<br />");
            sb.append("+ Veículo <br />");
            sb.append("-   " + veic.getMarca() + " " + veic.getCor() + ", " + veic.getModelo() + "<br />");
            sb.append("-   Placa: "+ veic.getPlaca() + "<br /><br />");
            sb.append("+ Serviços a serem executados <br />");

            double valTotal = 0.0;
            for(OrcamentoServico orcSvc: orc.getServicos()) {
                sb.append("-   " + orcSvc.getServico().getNome() + "<br />");
                valTotal += orcSvc.getValTotal();
            }
            sb.append("+ Peças utilizadas <br />");
            for(OrcamentoPeca pc: orc.getPecas()) {
                sb.append("-   " + pc.getQuantidade() + " " + pc.getPeca().getNome() + "<br />");
                valTotal += pc.getQuantidade() * pc.getPeca().getValUnit();
            }

            sb.append("<br />");
            sb.append("<br />+ Valor total: R$"+ valTotal);

            envio.setAssunto("Seu orçamento na BoutCar");
            envio.setCorpo(sb.toString());
            envio.enviar();

            System.out.println("Email enviado!!!");

        } catch (MessagingException e) {
             e.printStackTrace();
            System.err.println("Erro ao enviar email");
        }
    }

}
