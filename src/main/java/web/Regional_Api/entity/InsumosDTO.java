package web.Regional_Api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InsumosDTO {
    
    private Integer id_insumo;
    private String nombre;
    private String descripcion;
    private BigDecimal stock_actual;
    private BigDecimal stock_minimo;
    private String unidad_medida;
    private LocalDate fecha_vencimiento;
    private Integer estado;

    // Para la relación, solo pasamos el ID
    private Integer idSucursal;

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

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }
}
