package web.Regional_Api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.entity.RestauranteDTO;
import web.Regional_Api.repository.RestauranteRepository;

@Service
@Transactional
public class RestauranteService {
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    /**
     * Obtener todos los restaurantes
     */
    public List<RestauranteDTO> obtenerTodos() {
        return restauranteRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Contar todos los restaurantes
     */
    public long contarTodos() {
        return restauranteRepository.count();
    }
    
    /**
     * Buscar todos los restaurantes (entidades)
     */
    public List<Restaurante> buscarTodos() {
        return restauranteRepository.findAll();
    }
    
    /**
     * Buscar restaurante por ID (entidad)
     */
    public Optional<Restaurante> buscarId(Integer id) {
        return restauranteRepository.findById(id);
    }
    
    /**
     * Verificar si existe restaurante por RUC
     */
    public boolean existsByRuc(String ruc) {
        return restauranteRepository.findByRuc(ruc).isPresent();
    }
    
    /**
     * Guardar restaurante
     */
    public Restaurante guardar(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }
    
    /**
     * Eliminar restaurante por ID
     */
    public void eliminar(Integer id) {
        restauranteRepository.deleteById(id);
    }
    
    /**
     * Obtener restaurante por ID
     */
    public RestauranteDTO obtenerPorId(Integer idRestaurante) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(idRestaurante);
        return restaurante.map(this::convertirADTO).orElse(null);
    }
    
    /**
     * Obtener restaurante por RUC
     */
    public RestauranteDTO obtenerPorRuc(String ruc) {
        Optional<Restaurante> restaurante = restauranteRepository.findByRuc(ruc);
        return restaurante.map(this::convertirADTO).orElse(null);
    }
    
    /**
     * Crear nuevo restaurante
     */
    public RestauranteDTO crearRestaurante(RestauranteDTO dto) {
        // Validar que el RUC no exista
        if (restauranteRepository.findByRuc(dto.getRuc()).isPresent()) {
            throw new RuntimeException("El RUC " + dto.getRuc() + " ya existe");
        }
        
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre(dto.getNombre());
        restaurante.setRuc(dto.getRuc());
        restaurante.setDireccion(dto.getDireccion());
        restaurante.setLogoUrl(dto.getLogoUrl());
        restaurante.setSimboloMoneda(dto.getSimboloMoneda());
        restaurante.setTasaIgv(dto.getTasaIgv());
        restaurante.setEmailContacto(dto.getEmailContacto());
        restaurante.setEstado(1); // Activo por defecto
        restaurante.setFechaCreacion(LocalDateTime.now());
        restaurante.setFechaVencimiento(LocalDateTime.now().plusMonths(1)); // 1 mes de prueba por defecto
        
        Restaurante saved = restauranteRepository.save(restaurante);
        return convertirADTO(saved);
    }
    
    /**
     * Actualizar restaurante
     */
    public RestauranteDTO actualizarRestaurante(Integer idRestaurante, RestauranteDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
            .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
        
        restaurante.setNombre(dto.getNombre());
        restaurante.setDireccion(dto.getDireccion());
        restaurante.setLogoUrl(dto.getLogoUrl());
        restaurante.setSimboloMoneda(dto.getSimboloMoneda());
        restaurante.setTasaIgv(dto.getTasaIgv());
        restaurante.setEmailContacto(dto.getEmailContacto());
        restaurante.setEstado(dto.getEstado());
        
        Restaurante updated = restauranteRepository.save(restaurante);
        return convertirADTO(updated);
    }
    
    /**
     * Cambiar estado del restaurante
     */
    public RestauranteDTO cambiarEstado(Integer idRestaurante, Integer nuevoEstado) {
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
            .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
        
        restaurante.setEstado(nuevoEstado);
        Restaurante updated = restauranteRepository.save(restaurante);
        return convertirADTO(updated);
    }
    
    /**
     * Renovar suscripción del restaurante
     */
    public RestauranteDTO renovarSuscripcion(Integer idRestaurante, Integer diasVigencia) {
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
            .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
        
        LocalDateTime nuevaFechaVencimiento = LocalDateTime.now().plusDays(diasVigencia);
        restaurante.setFechaVencimiento(nuevaFechaVencimiento);
        restaurante.setEstado(1); // Reactivar si estaba vencido
        
        Restaurante updated = restauranteRepository.save(restaurante);
        return convertirADTO(updated);
    }
    
    /**
     * Obtener restaurantes activos
     */
    public List<RestauranteDTO> obtenerActivos() {
        return restauranteRepository.findByEstado(1).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtener restaurantes con suscripción vencida
     */
    public List<RestauranteDTO> obtenerConSuscripcionVencida() {
        return restauranteRepository.findRestaurantesConSuscripcionVencida(LocalDateTime.now())
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtener restaurantes con suscripción próxima a vencer (próximos N días)
     */
    public List<RestauranteDTO> obtenerProximoVencimiento(Integer dias) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limite = ahora.plusDays(dias);
        
        return restauranteRepository.findRestaurantesProximoVencimiento(ahora, limite)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Buscar restaurantes por nombre
     */
    public List<RestauranteDTO> buscarPorNombre(String nombre) {
        return restauranteRepository.findByNombreContainingIgnoreCase(nombre)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Eliminar restaurante
     */
    public void eliminarRestaurante(Integer idRestaurante) {
        restauranteRepository.deleteById(idRestaurante);
    }
    
    /**
     * Convertir entity a DTO
     */
    private RestauranteDTO convertirADTO(Restaurante restaurante) {
        RestauranteDTO dto = new RestauranteDTO();
        dto.setIdRestaurante(restaurante.getIdRestaurante());
        dto.setNombre(restaurante.getNombre());
        dto.setRuc(restaurante.getRuc());
        dto.setDireccion(restaurante.getDireccion());
        dto.setLogoUrl(restaurante.getLogoUrl());
        dto.setSimboloMoneda(restaurante.getSimboloMoneda());
        dto.setTasaIgv(restaurante.getTasaIgv());
        dto.setEmailContacto(restaurante.getEmailContacto());
        dto.setEstado(restaurante.getEstado());
        dto.setFechaCreacion(restaurante.getFechaCreacion() != null ? restaurante.getFechaCreacion().toString() : null);
        dto.setFechaVencimiento(restaurante.getFechaVencimiento() != null ? restaurante.getFechaVencimiento().toString() : null);
        return dto;
    }
}

