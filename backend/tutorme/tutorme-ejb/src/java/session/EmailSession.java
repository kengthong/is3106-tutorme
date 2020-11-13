package session;

import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import util.email.SMTPAuthenticator;

/**
 *
 * @author Owen Tay
 */
@Stateless
public class EmailSession implements EmailSessionLocal {

    public EmailSession() {
    }

    @Override
    public void send(String firstName, String toEmail) {
        try {
            String fromEmail = "is3106dummy@gmail.com";
            String password = "#IS3106dummy";

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.debug", "true");

            Authenticator auth = new SMTPAuthenticator(fromEmail, password);
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);            
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail.toLowerCase()));
            msg.setSubject("TutorMe Registration Complete!");
            msg.setText("Hi " + firstName+ "! \n\nYour account has been successfully registered with us!\n\n Regards, \n TutorMe Team");

            Date timestamp = new Date();
            msg.setSentDate(timestamp);
            Transport.send(msg);
            
            System.out.println("message send successfully");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void ban(String firstName, String toEmail) {
        try {
            String fromEmail = "tutormecare3106@gmail.com";
            String password = "#IS3106dummy";

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.debug", "true");

            Authenticator auth = new SMTPAuthenticator(fromEmail, password);
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);            
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail.toLowerCase()));
            msg.setSubject("TutorMe Registration Complete!");
            msg.setText("Hi " + firstName+ "! \n\nSorry to inform you that you've been banned for inappropriate behaviour\n\n Regards, \n TutorMe Team");

            Date timestamp = new Date();
            msg.setSentDate(timestamp);
            Transport.send(msg);
            
            System.out.println("message send successfully");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    @Override
    public void unban(String firstName, String toEmail) {
        try {
            String fromEmail = "tutormecare3106@gmail.com";
            String password = "#IS3106dummy";

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.debug", "true");

            Authenticator auth = new SMTPAuthenticator(fromEmail, password);
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);            
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail.toLowerCase()));
            msg.setSubject("TutorMe Registration Complete!");
            msg.setText("Hi " + firstName+ "! \n\nGlad to inform you that your ban has been lifted.\n\n Regards, \n TutorMe Team");

            Date timestamp = new Date();
            msg.setSentDate(timestamp);
            Transport.send(msg);
            
            System.out.println("message send successfully");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
