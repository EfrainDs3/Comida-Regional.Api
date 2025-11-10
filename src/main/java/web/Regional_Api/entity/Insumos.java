package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "insumos")
// Para el borrado lógico (usa tu columna 'estado')
@SQLDelete(sql = "UPDATE insumos SET estado = 0 WHERE id_insumo = ?")
@Where(clause = "estado = 1")
public class Insumos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_insumo;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "text")
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal stock_actual;

    @Column(precision = 10, scale = 2)
    private BigDecimal stock_minimo;

    private String unidad_medida;

    private LocalDate fecha_vencimiento; // Usa LocalDate para 'date'

    private Integer estado = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Sucursales sucursales;

    // --- Getters y Setters ---
    
    public Integer getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(Integer id_insumo) {
        this.id_insumo = id_insumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(BigDecimal stock_actual) {
        this.stock_actual = stock_actual;
    }

    public BigDecimal getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(BigDecimal stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public LocalDate getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(LocalDate fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Sucursales getSucursales() {
        return sucursales;
    }

    public void setSucursales(Sucursales sucursales) {
        this.sucursales = sucursales;
    }
}