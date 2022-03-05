package es.iespuertodelacruz.juan.restaurant.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the platos database table.
 * 
 */
@Entity
@Table(name="platos")
@NamedQuery(name="Plato.findAll", query="SELECT p FROM Plato p")
public class Plato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idplato;

	private String descripcion;

	private Boolean disponible;

	private String nombre;

	private Integer preciounidad;

	//bi-directional many-to-one association to Detallefactura
	@OneToMany(mappedBy="plato")
	private List<Detallefactura> detallefacturas;

	public Plato() {
	}

	public Integer getIdplato() {
		return this.idplato;
	}

	public void setIdplato(int idplato) {
		this.idplato = idplato;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean isDisponible() {
		return this.disponible;
	}

	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPreciounidad() {
		return this.preciounidad;
	}

	public void setPreciounidad(Integer preciounidad) {
		this.preciounidad = preciounidad;
	}

	public List<Detallefactura> getDetallefacturas() {
		return this.detallefacturas;
	}

	public void setDetallefacturas(List<Detallefactura> detallefacturas) {
		this.detallefacturas = detallefacturas;
	}

	public Detallefactura addDetallefactura(Detallefactura detallefactura) {
		getDetallefacturas().add(detallefactura);
		detallefactura.setPlato(this);

		return detallefactura;
	}

	public Detallefactura removeDetallefactura(Detallefactura detallefactura) {
		getDetallefacturas().remove(detallefactura);
		detallefactura.setPlato(null);

		return detallefactura;
	}

}