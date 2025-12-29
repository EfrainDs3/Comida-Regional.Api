package web.Regional_Api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.Regional_Api.entity.PagoSuscripcion;
import web.Regional_Api.entity.PagoSuscripcionDTO;
import web.Regional_Api.entity.Restaurante;
import web.Regional_Api.repository.PagoSuscripcionRepository;
import web.Regional_Api.repository.RestauranteRepository;

@Service
@Transactional
public class PagoSuscripcionService {
    
    @Autowired
    private PagoSuscripcionRepository pagoRepository;
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    /**
     * Obtener todos los pagos
     */
    public List<PagoSuscripcionDTO> obtenerTodos() {
        return pagoRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtener pago por ID
     */
    public PagoSuscripcionDTO obtenerPorId(Long idPago) {
        Optional<PagoSuscripcion> pago = pagoRepository.findById(idPago);
        return pago.map(this::convertirADTO).orElse(null);
    }
    
    /**
     * Obtener todos los pagos de un restaurante
     */
    public List<PagoSuscripcionDTO> obtenerPagosPorRestaurante(Integer idRestaurante) {
        return pagoRepository.findByRestauranteIdRestaurante(idRestaurante)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtener último pago de un restaurante
     */
    public PagoSuscripcionDTO obtenerUltimoPago(Integer idRestaurante) {
        Optional<PagoSuscripcion> pago = pagoRepository.findUltimoPagoRestaurante(idRestaurante);
        return pago.map(this::convertirADTO).orElse(null);
    }
    
    /**
     * Crear nuevo pago de suscripción
     */
    public PagoSuscripcionDTO crearPago(Integer idRestaurante, PagoSuscripcionDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
            .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
        
        // Validar que no exista pago para el mismo período
        Optional<PagoSuscripcion> pagoExistente = pagoRepository
            .findPagoByRestauranteAndPeriodo(idRestaurante, dto.getPeriodoCubierto());
        
        if (pagoExistente.isPresent()) {
            throw new RuntimeException("Ya existe un pago registrado para este período");
        }
        
        PagoSuscripcion pago = new PagoSuscripcion();
        pago.setRestaurante(restaurante);
        pago.setMonto(new BigDecimal(dto.getMonto().toString()));
        pago.setFechaPago(LocalDateTime.now());
        pago.setPeriodoCubierto(dto.getPeriodoCubierto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setComprobanteUrl(dto.getComprobanteUrl());
        pago.setEstado("PENDIENTE");
        pago.setObservaciones(dto.getObservaciones());
        
        PagoSuscripcion saved = pagoRepository.save(pago);
        return convertirADTO(saved);
    }
    
    /**
     * Aprobar pago de suscripción
     */
    public PagoSuscripcionDTO aprobarPago(Long idPago) {
        PagoSuscripcion pago = pagoRepository.findById(idPago)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        
        pago.setEstado("APROBADO");
        pago.setFechaAprobacion(LocalDateTime.now());
        
        // Renovar la suscripción del restaurante por 30 días adicionales
        Restaurante restaurante = pago.getRestaurante();
        restaurante.setFechaVencimiento(restaurante.getFechaVencimiento().plusDays(30));
        
        PagoSuscripcion updated = pagoRepository.save(pago);
        return convertirADTO(updated);
    }
    
    /**
     * Rechazar pago de suscripción
     */
    public PagoSuscripcionDTO rechazarPago(Long idPago, String motivo) {
        PagoSuscripcion pago = pagoRepository.findById(idPago)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        
        pago.setEstado("RECHAZADO");
        pago.setFechaAprobacion(LocalDateTime.now());
        pago.setObservaciones(motivo);
        
        PagoSuscripcion updated = pagoRepository.save(pago);
        return convertirADTO(updated);
    }
    
    /**
     * Obtener pagos pendientes de aprobación
     */
    public List<PagoSuscripcionDTO> obtenerPagosPendientes() {
        return pagoRepository.findByEstadoOrderByFechaPagoAsc("PENDIENTE")
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtener pagos por estado
     */
    public List<PagoSuscripcionDTO> obtenerPagosPorEstado(String estado) {
        return pagoRepository.findByEstado(estado)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtener pagos de un restaurante por estado
     */
    public List<PagoSuscripcionDTO> obtenerPagosRestaurantePorEstado(Integer idRestaurante, String estado) {
        return pagoRepository.findByRestauranteIdRestauranteAndEstado(idRestaurante, estado)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtener pagos en rango de fechas
     */
    public List<PagoSuscripcionDTO> obtenerPagosPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        return pagoRepository.findPagosPorFecha(desde, hasta)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Contar pagos pendientes
     */
    public long contarPagosPendientes() {
        return pagoRepository.countByEstado("PENDIENTE");
    }
    
    /**
     * Actualizar pago
     */
    public PagoSuscripcionDTO actualizarPago(Long idPago, PagoSuscripcionDTO dto) {
        PagoSuscripcion pago = pagoRepository.findById(idPago)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        
        // Solo permitir actualizar si está pendiente
        if (!"PENDIENTE".equals(pago.getEstado())) {
            throw new RuntimeException("No se puede actualizar un pago que no está en estado PENDIENTE");
        }
        
        pago.setMonto(new BigDecimal(dto.getMonto().toString()));
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setComprobanteUrl(dto.getComprobanteUrl());
        pago.setObservaciones(dto.getObservaciones());
        
        PagoSuscripcion updated = pagoRepository.save(pago);
        return convertirADTO(updated);
    }
    
    /**
     * Eliminar pago
     */
    public void eliminarPago(Long idPago) {
        pagoRepository.deleteById(idPago);
    }
    
    /**
     * Convertir entity a DTO
     */
    private PagoSuscripcionDTO convertirADTO(PagoSuscripcion pago) {
        PagoSuscripcionDTO dto = new PagoSuscripcionDTO();
        dto.setIdPago(pago.getIdPago());
        dto.setIdRestaurante(pago.getRestaurante().getIdRestaurante());
        dto.setMonto(pago.getMonto().doubleValue());
        dto.setFechaPago(java.sql.Timestamp.valueOf(pago.getFechaPago()));
        dto.setPeriodoCubierto(pago.getPeriodoCubierto());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setComprobanteUrl(pago.getComprobanteUrl());
        dto.setEstado(pago.getEstado());
        if (pago.getFechaAprobacion() != null) {
            dto.setFechaAprobacion(java.sql.Timestamp.valueOf(pago.getFechaAprobacion()));
        }
        dto.setObservaciones(pago.getObservaciones());
        return dto;
    }
}
