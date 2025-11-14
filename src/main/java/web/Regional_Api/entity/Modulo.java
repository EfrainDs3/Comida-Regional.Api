package web.Regional_Api.entity;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "modulo")
@Where(clause = "estado = 1")
public class Modulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_modulo")
	private Integer idModulo;

	@Column(name = "nombre_modulo")
	private String nombreModulo;

	@Column(name = "estado")
	private Integer estado = 1;

	@Column(name = "orden")
	private Integer orden;

	public Modulo() {
	}

	public Integer getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Integer idModulo) {
		this.idModulo = idModulo;
	}

	public String getNombreModulo() {
		return nombreModulo;
	}

	public void setNombreModulo(String nombreModulo) {
		this.nombreModulo = nombreModulo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

    @Override
    public String toString() {
        return "Modulo [idModulo=" + idModulo + ", nombreModulo=" + nombreModulo + ", estado=" + estado + ", orden="
                + orden + "]";
    } 
}
