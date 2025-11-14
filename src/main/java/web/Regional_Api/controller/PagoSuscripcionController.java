package web.Regional_Api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.Regional_Api.entity.PagoSuscripcionDTO;
import web.Regional_Api.entity.PagoSuscripcion;
import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.service.IPagoSuscripcionService;
import web.Regional_Api.service.IRestauranteService;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoSuscripcionController {

    @Autowired
    private IPagoSuscripcionService pagoService;
    
    
    @Autowired
    private IRestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<List<PagoSuscripcionDTO>> obtenerTodos() {
        List<PagoSuscripcionDTO> pagos = pagoService.buscarTodos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagos);
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<PagoSuscripcionDTO> obtenerPorId(@PathVariable Integer id) {
        return pagoService.buscarId(id)
                .map(this::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    

    @GetMapping("/restaurante/{idRestaurante}")
    public ResponseEntity<List<PagoSuscripcionDTO>> obtenerPagosPorRestaurante(@PathVariable Integer idRestaurante) {
        // (Opcional) Verificar si el restaurante existe
        if (restauranteService.buscarId(idRestaurante).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PagoSuscripcionDTO> pagos = pagoService.buscarPorIdRestaurante(idRestaurante).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagos);
    }

    public ResponseEntity<?> crear(@RequestBody PagoSuscripcionDTO dto) {
        
        Optional<Restaurante> optRestaurante = restauranteService.buscarId(dto.getId_restaurante());
        if (optRestaurante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se puede crear el pago: El restaurante con ID " + dto.getId_restaurante() + " no existe.");
        }
  
        PagoSuscripcion pago = new PagoSuscripcion();
        pago.setRestaurante(optRestaurante.get()); // Asignamos el objeto
        
        pago.setFecha_pago(dto.getFecha_pago());
        pago.setMonto(dto.getMonto());
        pago.setPeriodoCubiertoInicio(dto.getPeriodo_cubierto_inicio());
        pago.setPeriodoCubiertoFin(dto.getPeriodo_cubierto_fin());
        

    PagoSuscripcion nuevoPago = pagoService.guardar(pago);
    return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(nuevoPago));
    }

 
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody PagoSuscripcionDTO dto) {
        
        Optional<PagoSuscripcion> optPago = pagoService.buscarId(id);
        if (optPago.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Optional<Restaurante> optRestaurante = restauranteService.buscarId(dto.getId_restaurante());
        if (optRestaurante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se puede actualizar: El restaurante con ID " + dto.getId_restaurante() + " no existe.");
        }
        
     
        PagoSuscripcion pago = optPago.get();
        pago.setRestaurante(optRestaurante.get());
        pago.setFecha_pago(dto.getFecha_pago());
        pago.setMonto(dto.getMonto());
        pago.setPeriodoCubiertoInicio(dto.getPeriodo_cubierto_inicio());
        pago.setPeriodoCubiertoFin(dto.getPeriodo_cubierto_fin());
        pago.setEstado_pago(dto.getEstado_pago());

    PagoSuscripcion actualizado = pagoService.guardar(pago);
    return ResponseEntity.ok(convertirADTO(actualizado));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (pagoService.buscarId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private PagoSuscripcionDTO convertirADTO(PagoSuscripcion entidad) {
        PagoSuscripcionDTO dto = new PagoSuscripcionDTO();
        dto.setId_pago(entidad.getId_pago());
        dto.setId_restaurante(entidad.getRestaurante() != null ? entidad.getRestaurante().getId_restaurante() : null);
        dto.setFecha_pago(entidad.getFecha_pago());
        dto.setMonto(entidad.getMonto());
        dto.setPeriodo_cubierto_inicio(entidad.getPeriodoCubiertoInicio());
        dto.setPeriodo_cubierto_fin(entidad.getPeriodoCubiertoFin());
        dto.setEstado_pago(entidad.getEstado_pago());
        return dto;
    }
}