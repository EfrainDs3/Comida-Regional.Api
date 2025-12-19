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

            // 1. Remitente Oficial
            helper.setFrom(REMITENTE_OFICIAL);

            helper.setTo(destinatario);
            
            // 2. Asunto Único (Para evitar los 3 puntitos de Gmail)
            String asuntoUnico = "🔐 Token SuperAdmin - " + System.currentTimeMillis();
            helper.setSubject(asuntoUnico);
            
            // 3. HTML (Versión corregida Inline)
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

    // 👇 ESTA ES LA PARTE QUE CORREGÍ: HTML CON ESTILOS EN LÍNEA (INLINE)
    // Gmail no borrará esto y se verá perfecto con colores.
    private String construirEmailHTML(String token, String nombreUsuario) {
        return String.format(
            "<div style='font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f3f4f6;'>" +
            "  <div style='max-width: 600px; margin: 0 auto; background-color: white; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>" +
            
            "    " +
            "    <div style='background-color: #DC2626; color: white; padding: 30px 20px; text-align: center;'>" +
            "      <h1 style='margin: 0; font-size: 24px;'>🔐 Token de Acceso</h1>" +
            "    </div>" +

            "    " +
            "    <div style='padding: 30px 20px; color: #374151;'>" +
            "      <p style='font-size: 16px; margin-bottom: 20px;'>Hola <strong>%s</strong>,</p>" +
            "      <p style='margin-bottom: 20px;'>Has solicitado acceso al Panel SuperAdmin. Aquí está tu token:</p>" +
            
            "      " +
            "      <div style='background-color: #f8fafc; border: 2px solid #DC2626; padding: 15px; border-radius: 6px; margin: 25px 0;'>" +
            "        <p style='color: #DC2626; font-weight: bold; margin: 0 0 10px 0; font-size: 13px;'>🔑 Tu Token JWT:</p>" +
            "        <div style='font-family: monospace; word-break: break-all; color: #1f2937; font-size: 14px; line-height: 1.4;'>" +
            "          %s" +
            "        </div>" +
            "      </div>" +

            "      " +
            "      <div style='background-color: #fff; border: 1px solid #e5e7eb; padding: 15px; border-radius: 6px;'>" +
            "        <h3 style='color: #DC2626; margin-top: 0; font-size: 16px;'>📋 Pasos a seguir:</h3>" +
            "        <ol style='padding-left: 20px; margin-bottom: 0; color: #4b5563;'>" +
            "          <li style='margin-bottom: 5px;'>Copia el token completo.</li>" +
            "          <li style='margin-bottom: 5px;'>Pégalo en el login del SuperAdmin.</li>" +
            "          <li>Haz clic en Ingresar.</li>" +
            "        </ol>" +
            "      </div>" +

            "      <p style='margin-top: 30px; font-size: 12px; color: #9ca3af; text-align: center;'>" +
            "        Este token expirará pronto por seguridad.<br>Sistema de Gestión Regional" +
            "      </p>" +
            "    </div>" +
            "  </div>" +
            "</div>",
            nombreUsuario, token);
    }

    @Override
    public void sendEmail(String destinatario, String asunto, String contenido) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(REMITENTE_OFICIAL);
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