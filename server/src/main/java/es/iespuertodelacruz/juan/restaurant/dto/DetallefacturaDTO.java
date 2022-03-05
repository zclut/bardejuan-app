package es.iespuertodelacruz.juan.restaurant.dto;

import es.iespuertodelacruz.juan.restaurant.entity.Detallefactura;

public class DetallefacturaDTO {

	private Integer idfactura;
	private PlatoDTO plato;
	private Integer cantidad;
	private Double preciototal;
	
	public DetallefacturaDTO(Detallefactura df) {
		super();
		this.idfactura = df.getIdfactura();
		this.plato = new PlatoDTO(df.getPlato());
		this.cantidad = df.getCantidad();
		this.preciototal = df.getPreciototal();
	}
	
	public PlatoDTO getPlato() {
		return plato;
	}
	public void setPlato(PlatoDTO plato) {
		this.plato = plato;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getPreciototal() {
		return preciototal;
	}
	public void setPreciototal(Double preciototal) {
		this.preciototal = preciototal;
	}

	public Integer getIdfactura() {
		return idfactura;
	}

	public void setIdfactura(Integer idfactura) {
		this.idfactura = idfactura;
	}
}
