package Util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailUtil {

    public static void sendEmailWithAttachment(String to, String subject, String body, String filePath) {
        final String username = "14mbaye28@gmail.com"; // Remplacez par votre adresse email
        final String password = "hwhz cxgn anxd fnax"; // Remplacez par votre mot de passe ou un mot de passe d'application spécifique

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP de Gmail
        props.put("mail.smtp.port", "587"); // Port SMTP de Gmail

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Corps du message
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            // Création d'un message multipart pour la pièce jointe
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Ajout de la pièce jointe
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filePath);
            multipart.addBodyPart(messageBodyPart);

            // Ajout des parties au message
            message.setContent(multipart);

            // Envoi du message
            Transport.send(message);

            System.out.println("Email envoyé avec succès avec la pièce jointe");

        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }
}
