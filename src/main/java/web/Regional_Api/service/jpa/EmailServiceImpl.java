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

    @Override
    @org.springframework.scheduling.annotation.Async
    public void enviarTokenSuperAdmin(String destinatario, String token, String nombreUsuario) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("🔐 Token de Acceso SuperAdmin - Sistema Regional");
            helper.setText(construirEmailHTML(token, nombreUsuario), true);

            mailSender.send(message);
            System.out.println("✅ Email enviado exitosamente a: " + destinatario);
        } catch (MessagingException e) {
            System.err.println("❌ Error al enviar email: " + e.getMessage());
            throw new RuntimeException("Error al enviar el correo electrónico", e);
        }
    }

    private String construirEmailHTML(String token, String nombreUsuario) {
        return String.format(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <style>" +
                        "        body {" +
                        "            font-family: Arial, sans-serif;" +
                        "            margin: 0;" +
                        "            padding: 0;" +
                        "            background-color: #f3f4f6;" +
                        "        }" +
                        "        .container {" +
                        "            max-width: 600px;" +
                        "            margin: 0 auto;" +
                        "            background-color: white;" +
                        "        }" +
                        "        .header {" +
                        "            background: linear-gradient(135deg, #DC2626, #EA580C);" +
                        "            color: white;" +
                        "            padding: 40px 20px;" +
                        "            text-align: center;" +
                        "        }" +
                        "        .header h1 {" +
                        "            margin: 0;" +
                        "            font-size: 28px;" +
                        "        }" +
                        "        .content {" +
                        "            padding: 30px 20px;" +
                        "            background-color: #f9fafb;" +
                        "        }" +
                        "        .greeting {" +
                        "            font-size: 16px;" +
                        "            color: #374151;" +
                        "            margin-bottom: 20px;" +
                        "        }" +
                        "        .token-box {" +
                        "            background: white;" +
                        "            border: 2px solid #DC2626;" +
                        "            padding: 20px;" +
                        "            margin: 20px 0;" +
                        "            border-radius: 8px;" +
                        "            box-shadow: 0 2px 4px rgba(0,0,0,0.1);" +
                        "        }" +
                        "        .token-label {" +
                        "            margin: 0 0 10px 0;" +
                        "            color: #DC2626;" +
                        "            font-weight: bold;" +
                        "            font-size: 14px;" +
                        "        }" +
                        "        .token {" +
                        "            font-family: 'Courier New', monospace;" +
                        "            word-break: break-all;" +
                        "            font-size: 12px;" +
                        "            background-color: #f3f4f6;" +
                        "            padding: 15px;" +
                        "            border-radius: 4px;" +
                        "            color: #1f2937;" +
                        "            line-height: 1.6;" +
                        "        }" +
                        "        .instructions {" +
                        "            background: white;" +
                        "            padding: 20px;" +
                        "            border-radius: 8px;" +
                        "            margin: 20px 0;" +
                        "        }" +
                        "        .instructions h3 {" +
                        "            color: #DC2626;" +
                        "            margin-top: 0;" +
                        "        }" +
                        "        .instructions ol {" +
                        "            color: #374151;" +
                        "            line-height: 1.8;" +
                        "        }" +
                        "        .warning {" +
                        "            background-color: #FEF3C7;" +
                        "            border-left: 4px solid #F59E0B;" +
                        "            padding: 15px;" +
                        "            margin: 20px 0;" +
                        "            border-radius: 4px;" +
                        "        }" +
                        "        .warning p {" +
                        "            margin: 0;" +
                        "            color: #92400E;" +
                        "            font-size: 14px;" +
                        "        }" +
                        "        .footer {" +
                        "            text-align: center;" +
                        "            color: #6b7280;" +
                        "            font-size: 12px;" +
                        "            padding: 20px;" +
                        "            background-color: #f9fafb;" +
                        "            border-top: 1px solid #e5e7eb;" +
                        "        }" +
                        "        .footer p {" +
                        "            margin: 5px 0;" +
                        "        }" +
                        "    </style>" +
                        "</head>" +
                        "<body>" +
                        "    <div class=\"container\">" +
                        "        <div class=\"header\">" +
                        "            <h1>🔐 Token de Acceso SuperAdmin</h1>" +
                        "        </div>" +
                        "        <div class=\"content\">" +
                        "            <p class=\"greeting\">Hola <strong>%s</strong>,</p>" +
                        "            <p class=\"greeting\">Has solicitado acceso al Panel SuperAdmin del Sistema de Gestión Regional. Aquí está tu token de autenticación:</p>"
                        +
                        "            <div class=\"token-box\">" +
                        "                <p class=\"token-label\">🔑 Tu Token JWT:</p>" +
                        "                <div class=\"token\">%s</div>" +
                        "            </div>" +
                        "            <div class=\"instructions\">" +
                        "                <h3>📋 Instrucciones de Uso:</h3>" +
                        "                <ol>" +
                        "                    <li>Copia el token completo (todo el texto de arriba)</li>" +
                        "                    <li>Regresa a la página de login del SuperAdmin</li>" +
                        "                    <li>Pega el token en el campo \"Token JWT\"</li>" +
                        "                    <li>Haz clic en \"✓ Acceder al Panel\"</li>" +
                        "                </ol>" +
                        "            </div>" +
                        "            <div class=\"warning\">" +
                        "                <p><strong>⚠️ Importante:</strong> Este token es personal y confidencial. No lo compartas con nadie. El token tiene una validez limitada por seguridad.</p>"
                        +
                        "            </div>" +
                        "        </div>" +
                        "        <div class=\"footer\">" +
                        "            <p><strong>Sistema de Gestión Regional</strong></p>" +
                        "            <p>Este es un correo automático generado por el sistema</p>" +
                        "            <p>Si no solicitaste este acceso, ignora este mensaje y contacta al administrador</p>"
                        +
                        "        </div>" +
                        "    </div>" +
                        "</body>" +
                        "</html>",
                nombreUsuario, token);
    }
}
