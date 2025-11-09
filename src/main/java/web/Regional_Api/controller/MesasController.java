package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.Regional_Api.entity.Mesas;
import web.Regional_Api.service.IMesasService;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin(origins = "*")
public class MesasController {

    @Autowired
    private IMesasService mesasService;

    // GET - Obtener todas las mesas
    @GetMapping
    public ResponseEntity<List<Mesas>> listarMesas() {
        List<Mesas> mesas = mesasService.buscarTodos();
        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }

    // GET - Obtener mesa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mesas> obtenerMesaPorId(@PathVariable Integer id) {
        Optional<Mesas> mesa = mesasService.buscarId(id);
        if (mesa.isPresent()) {
            return new ResponseEntity<>(mesa.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - Crear nueva mesa
    @PostMapping
    public ResponseEntity<Mesas> crearMesa(@RequestBody Mesas mesa) {
        try {
            Mesas nuevaMesa = mesasService.guardar(mesa);
            return new ResponseEntity<>(nuevaMesa, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT - Actualizar mesa existente
    @PutMapping("/{id}")
    public ResponseEntity<Mesas> actualizarMesa(@PathVariable Integer id, @RequestBody Mesas mesa) {
        Optional<Mesas> mesaExistente = mesasService.buscarId(id);
        if (mesaExistente.isPresent()) {
            mesa.setId_mesa(id); // Asegurar que se actualice el registro correcto
            Mesas mesaActualizada = mesasService.modificar(mesa);
            return new ResponseEntity<>(mesaActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - Eliminar mesa (soft delete por el @SQLDelete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable Integer id) {
        Optional<Mesas> mesa = mesasService.buscarId(id);
        if (mesa.isPresent()) {
            mesasService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET - Buscar mesas por sucursal
    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Mesas>> listarMesasPorSucursal(@PathVariable Integer idSucursal) {
        // Este método requeriría un servicio adicional en IMesasService
        // Por ahora retornamos todas las mesas
        List<Mesas> mesas = mesasService.buscarTodos();
        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }

    // GET - Buscar mesas por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Mesas>> listarMesasPorEstado(@PathVariable String estado) {
        // Este método requeriría un servicio adicional en IMesasService
        // Por ahora retornamos todas las mesas
        List<Mesas> mesas = mesasService.buscarTodos();
        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }
}