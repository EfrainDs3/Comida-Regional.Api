package web.Regional_Api.entity;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "acceso")
@Where(clause = "estado = 1")
public class Acceso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_acceso")
	private Integer idAcceso;

	@Column(name = "id_modulo")
	private Integer idModulo;

	@Column(name = "id_perfil")
	private Integer idPerfil;

	@Column(name = "estado")
	private Integer estado = 1;

	public Acceso() {
	}

	public Integer getIdAcceso() {
		return idAcceso;
	}

	public void setIdAcceso(Integer idAcceso) {
		this.idAcceso = idAcceso;
	}

	public Integer getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Integer idModulo) {
		this.idModulo = idModulo;
	}

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

    @Override
    public String toString() {
        return "Acceso [idAcceso=" + idAcceso + ", idModulo=" + idModulo + ", idPerfil=" + idPerfil + ", estado="
                + estado + "]";
    }

}

