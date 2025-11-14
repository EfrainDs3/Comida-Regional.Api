package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Registros;
import web.Regional_Api.repository.RegistrosRepository;
import web.Regional_Api.service.IRegistrosService;
import web.Regional_Api.security.JwtUtil;

@Service
public class RegistrosService implements  IRegistrosService {

    @Autowired
    private RegistrosRepository repoRegistros; 
    @Autowired
    private JwtUtil jwtUtil;

    public List<Registros> buscarTodos () {
        return repoRegistros.findAll();
    }
    public void guardar(Registros registro){
        repoRegistros.save(registro);
    }
     public void modificar(Registros registro){
        repoRegistros.save(registro);
    }

    public Optional<Registros> buscarId (Integer id){
        return repoRegistros.findById(id);
    }

    public void eliminar(Integer id){
        repoRegistros.deleteById(id);
    }
    @Override
    public Optional<Registros> buscarPorIdUsuario(String idUsuario) {
        return repoRegistros.findByIdUsuario(idUsuario);
    }

    @Override
    public Optional<Registros> buscarPorAccessToken(String accessToken) {
        return repoRegistros.findByAccessToken(accessToken);
    }

    @Override
    public String generarToken(String idUsuario) {
        return jwtUtil.generateDeveloperToken(idUsuario);
    }

    public boolean validarToken(String token) {
        return jwtUtil.validateToken(token);
    }
}
