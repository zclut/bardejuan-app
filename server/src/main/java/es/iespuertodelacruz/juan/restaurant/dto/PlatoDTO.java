package es.iespuertodelacruz.juan.restaurant.dto;

import es.iespuertodelacruz.juan.restaurant.entity.Plato;

public class PlatoDTO {
	private Integer idplato;
	private String nombre;
	private String descripcion;
	private Integer preciounidad;
	private boolean disponible;
	
	public PlatoDTO(Plato p) {
		super();
		this.idplato = p.getIdplato();
		this.nombre = p.getNombre();
		this.descripcion = p.getDescripcion();
		this.preciounidad = p.getPreciounidad();
		this.disponible = p.isDisponible();
	}

	public Integer getIdplato() {
		return idplato;
	}

	public void setIdplato(Integer idplato) {
		this.idplato = idplato;
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

	public Integer getPreciounidad() {
		return preciounidad;
	}

	public void setPreciounidad(Integer preciounidad) {
		this.preciounidad = preciounidad;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
}
