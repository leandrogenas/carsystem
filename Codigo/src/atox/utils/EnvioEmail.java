package atox.utils;

import atox.model.Orcamento;
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

    public interface Dado{
        Dado getDado();
    }

    public enum Tipo{
        STATUS("email-status"),
        ORCAMENTO("email-orcamento");

        private Template template;

        Tipo(String template){
            VelocityEngine vE = new VelocityEngine();
            vE.init();

            this.template = vE.getTemplate("template_" + template + ".html");
        }

        public String geraTemplate(VelocityContext vCtx){
            StringWriter sW = new StringWriter();
            template.merge(vCtx, sW);

            return sW.toString();

        }

    }

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
        msg.setText(corpo);
    }

    public void enviar() throws MessagingException{
        Transport.send(msg);
    }

    public static void enviarOrcamento(Orcamento orc, String para){
        try {
            EnvioEmail envio = new EnvioEmail(para);

            VelocityContext vCtx = new VelocityContext();

            vCtx.put("cliente", orc.getCliente());
            vCtx.put("veiculo", orc.getVeiculo());
            vCtx.put("orcamento", orc);
            vCtx.put("pagamento", orc.getPagamento());

            envio.setAssunto("Seu orçamento na BoutCar");
            envio.setCorpo(Tipo.ORCAMENTO.geraTemplate(vCtx));
            envio.enviar();

            System.out.println("Email enviado!!!");

        } catch (MessagingException e) {
             e.printStackTrace();
            System.err.println("Erro ao enviar email");
        }
    }

}
