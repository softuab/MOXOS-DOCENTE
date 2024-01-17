package com.moxos.uab.service;

import com.moxos.uab.util.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;


@Service
public class EmailService {

    @Value("${app.upload.path}")
    private String path;

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public boolean sendEmail(Mail mail) throws FileNotFoundException, IOException {

        File documento = new File(path + File.separator + "imagenes" + File.separator + "template.txt");
        //Creamos el objeto con el que vamos a leer y obtener la plantilla html
        BufferedReader in = new BufferedReader(new FileReader(documento.getAbsolutePath()));
        String inputLine;
        String contenido = "";
        while ((inputLine = in.readLine()) != null) {
            contenido += inputLine + "\n";
        }
        in.close();
        String cad = contenido.replace("${nombrecompleto}", mail.getModel().get("nombrecompleto").toString());
        String html = cad.replace("${token}", mail.getModel().get("token").toString());

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(html, true); // Enable HTML content if needed
        };

        emailSender.send(messagePreparator);
        return true;
    }
}
