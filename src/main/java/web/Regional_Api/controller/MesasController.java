package web.Regional_Api.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import web.Regional_Api.entity.MesasDTO;
import web.Regional_Api.entity.Sucursales;
import web.Regional_Api.repository.SucursalesRepository;
import web.Regional_Api.service.IMesasService;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin(origins = "*")
public class MesasController {

    @Autowired
    private IMesasService mesasService;

    @Autowired
    private SucursalesRepository sucursalesRepository;

    // GET - Obtener todas las mesas
    @GetMapping
    public ResponseEntity<List<MesasDTO>> listarMesas() {
        List<MesasDTO> mesas = mesasService.buscarTodos().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }

    // GET - Obtener mesa por ID
    @GetMapping("/{id}")
    public ResponseEntity<MesasDTO> obtenerMesaPorId(@PathVariable Integer id) {
        Optional<Mesas> mesa = mesasService.buscarId(id);
        if (mesa.isPresent()) {
            return new ResponseEntity<>(convertToDto(mesa.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - Crear nueva mesa
    @PostMapping
    public ResponseEntity<?> crearMesa(@RequestBody MesasDTO mesaDTO) {
        Map<String, Object> response = new HashMap<>();

        if (mesaDTO == null || mesaDTO.getId_sucursal() == null || mesaDTO.getNumero_mesa() == null
                || mesaDTO.getCapacidad() == null || mesaDTO.getEstado_mesa() == null) {
            response.put("message", "id_sucursal, numero_mesa, capacidad y estado_mesa son obligatorios");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<Sucursales> sucursalOpt = sucursalesRepository.findById(mesaDTO.getId_sucursal());
        if (sucursalOpt.isEmpty()) {
            response.put("message", "La sucursal indicada no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<Mesas.EstadoMesa> estadoOpt = parseEstadoMesa(mesaDTO.getEstado_mesa());
        if (estadoOpt.isEmpty()) {
            response.put("message", "Estado de mesa inválido. Valores permitidos: "
                    + Arrays.toString(Mesas.EstadoMesa.values()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Mesas mesa = new Mesas();
        mesa.setId_sucursal(sucursalOpt.get());
        mesa.setNumero_mesa(mesaDTO.getNumero_mesa());
        mesa.setCapacidad(mesaDTO.getCapacidad());
        mesa.setEstado_mesa(estadoOpt.get());

    Mesas nuevaMesa = mesasService.guardar(mesa);
    return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(nuevaMesa));
    }

    // PUT - Actualizar mesa existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMesa(@PathVariable Integer id, @RequestBody MesasDTO mesaDTO) {
        Optional<Mesas> mesaExistente = mesasService.buscarId(id);
        if (mesaExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Mesas mesa = mesaExistente.get();
        Map<String, Object> response = new HashMap<>();

        if (mesaDTO.getId_sucursal() != null) {
            Optional<Sucursales> sucursalOpt = sucursalesRepository.findById(mesaDTO.getId_sucursal());
            if (sucursalOpt.isEmpty()) {
                response.put("message", "La sucursal indicada no existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            mesa.setId_sucursal(sucursalOpt.get());
        }

        if (mesaDTO.getNumero_mesa() != null) {
            mesa.setNumero_mesa(mesaDTO.getNumero_mesa());
        }

        if (mesaDTO.getCapacidad() != null) {
            mesa.setCapacidad(mesaDTO.getCapacidad());
        }

        if (mesaDTO.getEstado_mesa() != null) {
            Optional<Mesas.EstadoMesa> estadoOpt = parseEstadoMesa(mesaDTO.getEstado_mesa());
            if (estadoOpt.isEmpty()) {
                response.put("message", "Estado de mesa inválido. Valores permitidos: "
                        + Arrays.toString(Mesas.EstadoMesa.values()));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            mesa.setEstado_mesa(estadoOpt.get());
        }

    Mesas mesaActualizada = mesasService.modificar(mesa);
    return ResponseEntity.ok(convertToDto(mesaActualizada));
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
    public ResponseEntity<List<MesasDTO>> listarMesasPorSucursal(@PathVariable Integer idSucursal) {
        List<MesasDTO> mesas = mesasService.buscarTodos().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }

    // GET - Buscar mesas por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MesasDTO>> listarMesasPorEstado(@PathVariable String estado) {
        List<MesasDTO> mesas = mesasService.buscarTodos().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }

    private Optional<Mesas.EstadoMesa> parseEstadoMesa(String estado) {
        if (estado == null) {
            return Optional.empty();
        }
        return Arrays.stream(Mesas.EstadoMesa.values())
                .filter(e -> e.name().equalsIgnoreCase(estado))
                .findFirst();
    }

    private MesasDTO convertToDto(Mesas mesa) {
        MesasDTO dto = new MesasDTO();
        dto.setId_mesa(mesa.getId_mesa());
        Sucursales sucursal = mesa.getId_sucursal();
        if (sucursal != null) {
            dto.setId_sucursal(sucursal.getIdSucursal());
        }
        dto.setNumero_mesa(mesa.getNumero_mesa());
        dto.setCapacidad(mesa.getCapacidad());
        dto.setEstado_mesa(mesa.getEstado_mesa() != null ? mesa.getEstado_mesa().name() : null);
        return dto;
    }
}