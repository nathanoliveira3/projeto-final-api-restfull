package org.serratec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired 
    private JavaMailSender mailSender;

    public Boolean enviar(String titulo, String texto, String de, String para) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject(titulo);
        message.setText(texto);
        message.setTo(para);
        message.setFrom(de);

        try {
            mailSender.send(message); 

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
}
