package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.Regional_Api.entity.Acceso;
import web.Regional_Api.repository.AccesoRepository;
import web.Regional_Api.service.IAccesoService;

@Service
public class AccesoService implements IAccesoService {

    @Autowired
    private AccesoRepository accesoRepository;

    @Autowired
    private web.Regional_Api.repository.ModuloRepository moduloRepository;

    @Override
    public List<Acceso> getAllAccesos() {
        return accesoRepository.findAll();
    }

    @Override
    public Optional<Acceso> getAccesoById(Integer id) {
        return accesoRepository.findById(id);
    }

    @Override
    public Acceso saveAcceso(Acceso acceso) {
        return accesoRepository.save(acceso);
    }

    @Override
    public void deleteAcceso(Integer id) {
        accesoRepository.deleteById(id);
    }

    @Override
    public boolean existeAcceso(Integer idModulo, Integer idPerfil) {
        return accesoRepository.existsByIdModuloAndIdPerfilAndEstado(idModulo, idPerfil, 1);
    }

    @Override
    public void deleteAccesosByPerfil(Integer idPerfil) {
        accesoRepository.deleteByIdPerfil(idPerfil);
    }

    @Override
    public List<Acceso> getAccesosByPerfil(Integer idPerfil) {
        return accesoRepository.findByIdPerfilAndEstado(idPerfil, 1);
    }

    @Override
    public boolean tieneAcceso(Integer idPerfil, Integer idModulo) {
        return accesoRepository.existsByIdModuloAndIdPerfilAndEstado(idModulo, idPerfil, 1);
    }

    @Override
    public List<web.Regional_Api.dto.AccesoConModuloDTO> getAccesosConModuloByPerfil(Integer idPerfil) {
        List<Acceso> accesos = accesoRepository.findByIdPerfilAndEstado(idPerfil, 1);
        List<web.Regional_Api.dto.AccesoConModuloDTO> resultado = new java.util.ArrayList<>();

        for (Acceso acceso : accesos) {
            web.Regional_Api.entity.Modulo modulo = moduloRepository.findById(acceso.getIdModulo()).orElse(null);
            if (modulo != null) {
                web.Regional_Api.dto.AccesoConModuloDTO dto = new web.Regional_Api.dto.AccesoConModuloDTO(
                        acceso.getIdAcceso(),
                        modulo.getIdModulo(),
                        modulo.getNombreModulo(),
                        modulo.getOrden(),
                        acceso.getIdPerfil(),
                        acceso.getEstado());
                resultado.add(dto);
            }
        }

        // Ordenar por el orden del módulo
        resultado.sort((a, b) -> a.getOrden().compareTo(b.getOrden()));

        return resultado;
    }
}
