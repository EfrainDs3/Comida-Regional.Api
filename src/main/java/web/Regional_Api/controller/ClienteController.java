package web.Regional_Api.controller;

import java.util.List;
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

import web.Regional_Api.entity.Cliente;
import web.Regional_Api.entity.ClienteDTO;
import web.Regional_Api.service.IClienteService;

@RestController
@RequestMapping("/restful/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        List<ClienteDTO> clientes = clienteService.buscarTodos().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerPorId(@PathVariable Integer id) {
        Optional<Cliente> opt = clienteService.buscarId(id);
        return opt.map(this::convertirADTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> crear(@RequestBody ClienteDTO dto) {
        Cliente c = new Cliente();
        c.setIdRestaurante(dto.getIdRestaurante());
        c.setTipoCliente(dto.getTipoCliente());
        c.setNombreRazonSocial(dto.getNombreRazonSocial());
        c.setDocumento(dto.getDocumento());
        c.setEmail(dto.getEmail());
        c.setTelefono(dto.getTelefono());
        c.setDireccion(dto.getDireccion());
        if (dto.getEstado() != null)
            c.setEstado(dto.getEstado());

        Cliente guardado = clienteService.guardar(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Integer id, @RequestBody ClienteDTO dto) {
        Optional<Cliente> opt = clienteService.buscarId(id);
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();

        Cliente c = opt.get();
        if (dto.getIdRestaurante() != null)
            c.setIdRestaurante(dto.getIdRestaurante());
        if (dto.getTipoCliente() != null)
            c.setTipoCliente(dto.getTipoCliente());
        if (dto.getNombreRazonSocial() != null)
            c.setNombreRazonSocial(dto.getNombreRazonSocial());
        if (dto.getDocumento() != null)
            c.setDocumento(dto.getDocumento());
        if (dto.getEmail() != null)
            c.setEmail(dto.getEmail());
        if (dto.getTelefono() != null)
            c.setTelefono(dto.getTelefono());
        if (dto.getDireccion() != null)
            c.setDireccion(dto.getDireccion());
        if (dto.getEstado() != null)
            c.setEstado(dto.getEstado());

        Cliente actualizado = clienteService.modificar(c);
        return ResponseEntity.ok(convertirADTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Cliente> opt = clienteService.buscarId(id);
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();

        Cliente c = opt.get();
        c.setEstado(0);
        clienteService.modificar(c);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restaurante/{idRestaurante}")
    public ResponseEntity<List<ClienteDTO>> porRestaurante(@PathVariable Integer idRestaurante) {
        List<ClienteDTO> clientes = clienteService.buscarPorRestaurante(idRestaurante).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ClienteDTO>> porEstado(@PathVariable Integer estado) {
        List<ClienteDTO> clientes = clienteService.buscarPorEstado(estado).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    private ClienteDTO convertirADTO(Cliente entidad) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(entidad.getIdCliente());
        dto.setIdRestaurante(entidad.getIdRestaurante());
        dto.setTipoCliente(entidad.getTipoCliente());
        dto.setNombreRazonSocial(entidad.getNombreRazonSocial());
        dto.setDocumento(entidad.getDocumento());
        dto.setEmail(entidad.getEmail());
        dto.setTelefono(entidad.getTelefono());
        dto.setDireccion(entidad.getDireccion());
        dto.setEstado(entidad.getEstado());
        dto.setFechaRegistro(entidad.getFechaRegistro());
        return dto;
    }
}
