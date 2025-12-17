package web.Regional_Api.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import web.Regional_Api.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // 👇 IMPORTANTE: Asegúrate que este correo sea IDÉNTICO al de tu MailConfig.java
    private final String REMITENTE_OFICIAL = "admin@comidas.spring.informaticapp.com";

    @Override
    @org.springframework.scheduling.annotation.Async
    public void enviarTokenSuperAdmin(String destinatario, String token, String nombreUsuario) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // --- AQUÍ ESTABA EL ERROR: FALTABA EL REMITENTE ---
            helper.setFrom(REMITENTE_OFICIAL); 
            // --------------------------------------------------

            helper.setTo(destinatario);
            String asuntoUnico = "🔐 Token SuperAdmin - " + System.currentTimeMillis();
            helper.setSubject(asuntoUnico);
            helper.setText(construirEmailHTML(token, nombreUsuario), true);

            mailSender.send(message);
            System.out.println("✅ Email enviado exitosamente a: " + destinatario);
        } catch (MessagingException e) {
            System.err.println("❌ Error al enviar email: " + e.getMessage());
            e.printStackTrace(); 
            throw new RuntimeException("Error al enviar el correo electrónico: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("❌ Error inesperado al enviar email: " + e.getMessage());
            e.printStackTrace(); 
            throw new RuntimeException("Error inesperado al enviar el correo electrónico: " + e.getMessage(), e);
        }
    }

    private String construirEmailHTML(String token, String nombreUsuario) {
        // ... (Tu código HTML se mantiene igual, no hace falta cambiarlo) ...
        return String.format(
            "<!DOCTYPE html><html>...</html>", // Resumido para no llenar la pantalla
            nombreUsuario, token);
    }

    @Override
    public void sendEmail(String destinatario, String asunto, String contenido) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // --- AQUÍ TAMBIÉN FALTABA ---
            helper.setFrom(REMITENTE_OFICIAL);
            // ----------------------------

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(contenido, true);

            mailSender.send(message);
            System.out.println("✅ Email enviado exitosamente a: " + destinatario);
        } catch (MessagingException e) {
            System.err.println("❌ Error al enviar email: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo electrónico: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("❌ Error inesperado al enviar email: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error inesperado al enviar el correo electrónico: " + e.getMessage(), e);
        }
    }
}