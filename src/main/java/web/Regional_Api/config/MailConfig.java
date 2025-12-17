package web.Regional_Api.config; // 👈 Esto debe coincidir con tu nueva carpeta

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        // --- CONFIGURACIÓN DEL SERVIDOR ---
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(465); // Puerto SSL
        
        // --- TUS CREDENCIALES ---
        mailSender.setUsername("anllyriva14@gmail.com");
        
        // 👇 IMPORTANTE: PEGA AQUÍ TU CLAVE NUEVA DE 16 DÍGITOS (SIN ESPACIOS)
        // Borra la anterior, genera una nueva en Google y pégala aquí.
        mailSender.setPassword("moxtqhkjjkraxdsm"); 

        // --- PROPIEDADES DE SEGURIDAD ---
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false"); 
        props.put("mail.smtp.starttls.required", "false");
        props.put("mail.smtp.ssl.enable", "true");
        
        // --- SALTAR ERRORES DE CERTIFICADOS (ANTIVIRUS/RED) ---
        props.put("mail.smtps.ssl.trust", "*");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtps.ssl.checkserveridentity", "false");
        props.put("mail.smtp.ssl.checkserveridentity", "false");
        
        // Logs para ver errores en consola
        props.put("mail.debug", "true");

        return mailSender;
    }
}