package web.Regional_Api.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "mesas")
@SQLDelete(sql = "UPDATE mesas SET estado = 0 WHERE id_mesa = ?")
@Where(clause = "estado = 1")
public class Mesas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_mesa;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Sucursal id_sucursal;
    
    private String numero_mesa;
    private Integer capacidad;
    
    @Enumerated(EnumType.STRING)
    private EstadoMesa estado_mesa;
    
    private Integer estado = 1;

    // Enum para el estado de la mesa
    public enum EstadoMesa {
        Disponible, Ocupada, Reservada
    }

    // Constructores
    public Mesas() {}

    public Mesas(Sucursal id_sucursal, String numero_mesa, Integer capacidad, EstadoMesa estado_mesa) {
        this.id_sucursal = id_sucursal;
        this.numero_mesa = numero_mesa;
        this.capacidad = capacidad;
        this.estado_mesa = estado_mesa;
        this.estado = 1;
    }

    // Getters y Setters
    public Integer getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(Integer id_mesa) {
        this.id_mesa = id_mesa;
    }

    public Sucursal getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Sucursal id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public String getNumero_mesa() {
        return numero_mesa;
    }

    public void setNumero_mesa(String numero_mesa) {
        this.numero_mesa = numero_mesa;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoMesa getEstado_mesa() {
        return estado_mesa;
    }

    public void setEstado_mesa(EstadoMesa estado_mesa) {
        this.estado_mesa = estado_mesa;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Mesas [id_mesa=" + id_mesa + ", id_sucursal=" + id_sucursal + ", numero_mesa=" + numero_mesa + ", capacidad="
                + capacidad + ", estado_mesa=" + estado_mesa + ", estado=" + estado + "]";
    }
}