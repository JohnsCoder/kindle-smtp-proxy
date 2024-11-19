

package proxy.kindle;

import java.io.IOException;
import java.util.Objects;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;

import proxy.kindle.amazon.EbookAuthorization;
import proxy.kindle.smtp.SmtpService;
import utils.EmailContentHandler;

public class Main {

    public static void main(String[] args) {
//        VerifyContent();
        SmtpService.Interceptor();
//        SmtpService.ListFolders();
    }


    public static void FromFilter(Message message) {
        try {
            Address[] addresses = message.getFrom();

            for (Address address : addresses) {
                if (address instanceof InternetAddress) {
                    String email = ((InternetAddress) address).getAddress();
                    System.out.println("new mail: " + email);
                    if (Objects.equals(email, System.getenv("ZLIBRARY_EMAIL"))) {

                        SmtpService.Forward(message, System.getenv("KINDLE_EMAIL"));
                    }

//                    if (Objects.equals(email, System.getenv("AMAZON_EMAIL"))) {
//                        VerifyContent(message);
//                    }
                }
            }
        } catch (MessagingException err) {
            System.out.println(err.getMessage());
        }

    }

    public static void VerifyContent(Message message) {
        try {
            Multipart content = (Multipart) message.getContent();
            String text = content.getBodyPart(0).getContent().toString();
            String link = (new EmailContentHandler()).ExtractLink(text);
            System.out.println("link atual: " + link);
            if (link != null) {
                Thread.sleep(14629);
                new EbookAuthorization().OkHttpConnection(link);
            }

        } catch (MessagingException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void VerifyContent() {
        String link = "https://www.amazon.com/sendtokindle/verification/confirm/A2Q3Y263D00KWC/162a8579-9529-4b64-95eb-280a98a540c8";
        System.out.println("link atual: " + link);
        new EbookAuthorization().OkHttpConnection(link);
    }
}
