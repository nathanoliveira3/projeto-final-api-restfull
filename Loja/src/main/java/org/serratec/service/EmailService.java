package org.serratec.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired 
    private JavaMailSender mailSender;

    public Boolean enviar(String titulo, String texto, String para) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject(titulo);
        helper.setText(texto);
        helper.setTo(para);
        

        try {
            mailSender.send(message); 

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
}
