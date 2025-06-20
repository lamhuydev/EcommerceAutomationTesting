package utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.util.List;
import java.util.Properties;
import helpers.PropertiesHelper;
import java.util.Date;


public class EmailUtils {

    public static void sendHtmlEmail(String subject, String htmlBody, List<File> attachments) {
        final String username = "lamgiahuy03tv@gmail.com";
        final String password = PropertiesHelper.getValue("pass_email");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("lamgiahuy1612@gmail.com"));
            message.setSubject(subject);

            // Nội dung HTML
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            // Đính kèm file nếu có
            if (attachments != null) {
                for (File file : attachments) {
                    if (file.exists()) {
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        attachmentPart.attachFile(file);
                        multipart.addBodyPart(attachmentPart);
                    }
                }
            }

            message.setContent(multipart);
            Transport.send(message);

            LogUtils.info("✅ Email with attachments sent!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
