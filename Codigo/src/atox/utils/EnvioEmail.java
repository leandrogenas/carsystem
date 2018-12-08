package atox.utils;

import atox.model.Orcamento;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnvioEmail{

    private static final String HOST = "smtp.gmail.com";
    private static final String PORTA = "465";
    private static final String LOGIN = "carsystematox@gmail.com";
    private static final String SENHA = "4t0x102030";

    private static EnvioEmail instancia;

    private Session session;

    private EnvioEmail(){
        Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.socketFactory.port", PORTA);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", PORTA);

        session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(LOGIN, SENHA);
                }
        });

        session.setDebug(true);
    }

    public static EnvioEmail getInstancia(){
        if(instancia == null)
            instancia = new EnvioEmail();

        return instancia;
    }

    public void enviarOrcamento(Orcamento orc, String para){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(LOGIN));

            Address[] toUser = InternetAddress.parse(para);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Orçamento feito na BoutCar");
            message.setText("SALVE QUEBRADAAA  PS. VSF   PS2. BOLA UM BECK");

            /* Método para enviar a mensagem criada */
            Transport.send(message);

            System.out.println("Email enviado!!!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erro ao enviar email");
        }

    }

}
