package web.Regional_Api.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void enviarTokenSuperAdmin(String destinatario, String token, String nombreUsuario);
}
