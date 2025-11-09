package web.Regional_Api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Modulo;
import web.Regional_Api.entity.Usuarios;
import web.Regional_Api.repository.AccesoRepository;
import web.Regional_Api.repository.ModuloRepository;
import web.Regional_Api.service.jpa.UsuarioService;

@Service
public class AuthorizationService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ModuloRepository moduloRepository;

	@Autowired
	private AccesoRepository accesoRepository;

	public boolean userHasAccess(String nombreUsuarioLogin, String nombreModulo) {
		Optional<Usuarios> usuarioOpt = usuarioService.getUsuarioByLogin(nombreUsuarioLogin);
		if (usuarioOpt.isEmpty()) {
			return false;
		}

		Optional<Modulo> moduloOpt = moduloRepository.findByNombreModulo(nombreModulo);
		if (moduloOpt.isEmpty()) {
			return false;
		}

		Usuarios usuario = usuarioOpt.get();
		Modulo modulo = moduloOpt.get();

		return accesoRepository.existsByIdModuloAndIdPerfilAndEstado(modulo.getIdModulo(), usuario.getRolId(), "1");
	}
}
