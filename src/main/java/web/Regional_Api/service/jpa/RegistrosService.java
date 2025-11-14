package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Registros;
import web.Regional_Api.repository.RegistrosRepository;
import web.Regional_Api.service.IRegistrosService;


@Service
public class RegistrosService implements  IRegistrosService {

    @Autowired
    private RegistrosRepository repoRegistros; 
    public List<Registros> buscarTodos () {
        return repoRegistros.findAll();
    }
    public void guardar(Registros registro){
        // Asegurar que access_token no sea nulo o vacío antes de guardar
        String token = registro.getAccess_token();
        if (token == null || token.trim().isEmpty()) {
            // Asignar valor predeterminado
            registro.setAccess_token(Registros.DEFAULT_ACCESS_TOKEN);
        }
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Registros> buscarPorAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String generarToken(String idUsuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean validarToken(String token) {
        throw new UnsupportedOperationException("Not supported yet.");
    }





}
