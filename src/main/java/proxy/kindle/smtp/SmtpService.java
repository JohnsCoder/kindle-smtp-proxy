//
// Source code recreated from a .class file by IntelliJ IDEA
package proxy.kindle.smtp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import proxy.kindle.Main;
import utils.EmailContentHandler;

public class SmtpService {

    public void Forward(Message message, String recipient) {
        System.out.println(recipient);
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", System.getenv("SMTP_EMAIL_SERVER_HOST"));
        props.put("mail.smtp.port", System.getenv("SMTP_EMAIL_SERVER_PORT"));
        Session session = Session.getInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            InternetAddress from = new InternetAddress();
            from.setPersonal(System.getenv("MAIN_NAME"));
            from.setAddress(System.getenv("MAIN_EMAIL"));
            msg.setFrom(from);
            msg.setContent((Multipart) message.getContent());
            msg.setRecipients(RecipientType.TO, recipient);
            Transport.send(msg, System.getenv("MAIN_EMAIL"), System.getenv("MAIN_APP_PASSWORD"));
            System.out.println("Mail delivered");
        } catch (MessagingException err) {
            System.out.println(err.getMessage());
        } catch (IOException err) {
            throw new RuntimeException(err);
        }

    }

    public void Fetch() {
        Properties props = new Properties();
        props.put("mail.smtp.host", System.getenv("IMAP_EMAIL_SERVER_HOST"));
        props.put("mail.smtp.port", System.getenv("IMAP_EMAIL_SERVER_PORT"));
        Session session = Session.getInstance(props, null);

        try {
            Store store = session.getStore("imaps");
            store.connect(System.getenv("IMAP_EMAIL_SERVER_HOST"), System.getenv("MAIN_EMAIL"), System.getenv("MAIN_APP_PASSWORD"));
            Folder folder = store.getFolder("INBOX");
            folder.open(2);
            new BufferedReader(new InputStreamReader(System.in));
            Message[] messages = folder.getMessages();
            System.out.println("messages count: " + messages.length);

            for (Message message : messages) {
                System.out.println("----------------------------------");
                message.getFrom();

            }

        } catch (MessagingException err) {
            System.out.println(err.getMessage());
        }

    }

    public void Interceptor() {
        Properties props = new Properties();
        props.put("mail.smtp.host", System.getenv("IMAP_EMAIL_SERVER_HOST"));
        props.put("mail.smtp.port", System.getenv("IMAP_EMAIL_SERVER_PORT"));
        Session session = Session.getInstance(props, null);

        try {
            Store store = session.getStore("imaps");
            store.connect(System.getenv("IMAP_EMAIL_SERVER_HOST"), System.getenv("MAIN_EMAIL"), System.getenv("MAIN_APP_PASSWORD"));
            Folder folder = store.getFolder("INBOX");
            folder.open(2);
            folder.addMessageCountListener(new MessageCountAdapter() {
                public void messagesAdded(MessageCountEvent e) {
                    Message[] messages = e.getMessages();

                    for (Message message : messages) {
                        Main.FromFilter(message);
                    }
                }
            });

            while (true) {
                if (folder.getMessageCount() > 0) {
                    folder.getMessage(folder.getMessageCount());
                }

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException err) {
                    System.out.println(err.getMessage());
                }
            }
        } catch (MessagingException err) {
            System.out.println(err.getMessage());
        }
    }

    public void DebugMail(Message message) {
        try {
            Multipart content = (Multipart) message.getContent();
            String text = content.getBodyPart(0).getContent().toString();
            System.out.println((new EmailContentHandler()).ExtractLink(text));
        } catch (IOException | MessagingException err) {
            System.out.println(((Exception) err).getMessage());
        }

    }
}
