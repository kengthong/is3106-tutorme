/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Owen Tay
 */
@Stateless
public class EmailSession implements EmailSessionLocal {

    public EmailSession() {
    }

    @Override
    public void send(String firstName, String email) {
        System.out.println(email);
        try {
            // Step1
            System.out.println("\n 1st ===> setup Mail Server Properties..");
            Properties mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");
            mailServerProperties.put("mail.smtp.starttls.required", "true");
            System.out.println("Mail Server Properties have been setup successfully..");

            // Step2
            System.out.println("\n\n 2nd ===> get Mail Session..");
            Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            MimeMessage generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            generateMailMessage.setSubject("Welcome to TutorMe!");
            String emailBody = "Hey " + firstName + "!" + "\nYour account has been successfully registered\n\n";
            generateMailMessage.setContent(emailBody, "text/html");
            System.out.println("Mail Session has been created successfully..");

            // Step3
            System.out.println("\n\n 3rd ===> Get Session and Send mail");
            Transport transport = getMailSession.getTransport("smtp");

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect("smtp.gmail.com", "is3106dummy@gmail.com", "#IS3106dummy");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();

//            String to = email;
//            String from = "faith.zhihong@gmail.com";
//            String subject = "Tutor-Me Account Successfully Registered";
//            String messageText = "Hey " + firstName + "!" + "\nYou seem to have forgotten your password, here's a new one:\n\n";
//            boolean sessionDebug = false;
////            String messageText = "Hey " + firstName + "!" + "\nYou seem to have forgotten your password, here's a new one:\n\n";
//            Properties props = System.getProperties();
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.host", host);
//            props.put("mail.smtp.port", "587");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.required", "true");
//
//            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//            Session mailSession = Session.getDefaultInstance(props, null);
//            mailSession.setDebug(sessionDebug);
//            Message msg = new MimeMessage(mailSession);
//            msg.setFrom(new InternetAddress(from));
//            InternetAddress[] address = {new InternetAddress(to)};
//            msg.setRecipients(Message.RecipientType.TO, address);
//            msg.setSubject(subject);
//            msg.setSentDate(new Date());
//            msg.setText(messageText);
//
//            Transport transport = mailSession.getTransport("smtp");
//            transport.connect(host, user, pass);
//            transport.sendMessage(msg, msg.getAllRecipients());
//            transport.close();
//            System.out.println("message send successfully");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
