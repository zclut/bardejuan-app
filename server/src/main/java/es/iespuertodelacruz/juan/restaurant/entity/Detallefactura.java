package es.iespuertodelacruz.juan.restaurant.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the detallefactura database table.
 * 
 */
@Entity
@Table(name="detallefactura")
@NamedQuery(name="Detallefactura.findAll", query="SELECT d FROM Detallefactura d")
public class Detallefactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idfactura;

	private Integer cantidad;

	private Double preciototal;

	//bi-directional many-to-one association to Plato
	@ManyToOne
	@JoinColumn(name="fkidplato")
	private Plato plato;

	//bi-directional many-to-one association to Servicio
	@ManyToOne
	@JoinColumn(name="fkidservicio")
	private Servicio servicio;

	public Detallefactura() {
	}

	public Integer getIdfactura() {
		return this.idfactura;
	}

	public void setIdfactura(Integer idfactura) {
		this.idfactura = idfactura;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPreciototal() {
		return this.preciototal;
	}

	public void setPreciototal(Double preciototal) {
		this.preciototal = preciototal;
	}

	public Plato getPlato() {
		return this.plato;
	}

	public void setPlato(Plato plato) {
		this.plato = plato;
	}

	public Servicio getServicio() {
		return this.servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

}