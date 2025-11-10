package web.Regional_Api.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import web.Regional_Api.entity.Sucursales;
import web.Regional_Api.repository.SucursalesRepository;
import web.Regional_Api.service.ISucursalesService;

@Service
public class SucursalesService implements ISucursalesService {

    @Autowired
    private SucursalesRepository repoSucursales;

    @Override
    public List<Sucursales> buscarTodosPorRestaurante(Integer idRestaurante) {
        return repoSucursales.findByIdRestaurante(idRestaurante);
    }

    @Override
    public void guardar(Sucursales sucursal, Integer idRestaurante) {
        sucursal.setIdRestaurante(idRestaurante);
        repoSucursales.save(sucursal);
    }

    @Override
    public void modificar(Sucursales sucursalActualizada, Integer idRestaurante) {
        buscarIdYRestaurante(sucursalActualizada.getIdSucursal(), idRestaurante)
                .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada o acceso denegado para modificar."));

        repoSucursales.save(sucursalActualizada);
    }

    @Override
    public Optional<Sucursales> buscarIdYRestaurante(Integer idSucursal, Integer idRestaurante) {
        return repoSucursales.findByIdSucursalAndIdRestaurante(idSucursal, idRestaurante);
    }

    @Override
    public void eliminar(Integer idSucursal, Integer idRestaurante) {
        buscarIdYRestaurante(idSucursal, idRestaurante)
                .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada o acceso denegado para eliminar."));

        repoSucursales.deleteById(idSucursal);
    }
}