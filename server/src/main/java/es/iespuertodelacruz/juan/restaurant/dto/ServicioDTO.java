package es.iespuertodelacruz.juan.restaurant.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import es.iespuertodelacruz.juan.restaurant.entity.Servicio;

public class ServicioDTO {
	private Integer idservicio;
	private Long fechacomienzo;
	private Long fechafin;
	private String reservada;
	private boolean pagada;
	private MesaDTO mesa;
	private ArrayList<DetallefacturaDTO> detalleFactura;
	
	
	public ServicioDTO(Servicio s) {
		super();
		this.idservicio = s.getIdservicio();
		this.fechacomienzo = s.getFechacomienzo();
		this.fechafin = s.getFechafin();
		this.reservada = s.getReservada();
		this.pagada = s.isPagada();
		this.mesa = new MesaDTO(s.getMesa());
		detalleFactura = new ArrayList<>();
		if (s.getDetallefacturas().size() > 0) {
			s.getDetallefacturas().forEach(df -> detalleFactura.add(new DetallefacturaDTO(df)));
		}
		
	}
	
	
	public ArrayList<DetallefacturaDTO> getDetalleFactura() {
		return detalleFactura;
	}


	public void setDetalleFactura(ArrayList<DetallefacturaDTO> detalleFactura) {
		this.detalleFactura = detalleFactura;
	}


	public Integer getIdservicio() {
		return idservicio;
	}
	public void setIdservicio(Integer idservicio) {
		this.idservicio = idservicio;
	}
	public Long getFechacomienzo() {
		return fechacomienzo;
	}
	public void setFechacomienzo(Long fechacomienzo) {
		this.fechacomienzo = fechacomienzo;
	}
	public Long getFechafin() {
		return fechafin;
	}
	public void setFechafin(Long fechafin) {
		this.fechafin = fechafin;
	}
	public String getReservada() {
		return reservada;
	}
	public void setReservada(String reservada) {
		this.reservada = reservada;
	}
	public boolean isPagada() {
		return pagada;
	}
	public void setPagada(boolean pagada) {
		this.pagada = pagada;
	}

	public MesaDTO getMesa() {
		return mesa;
	}

	public void setMesa(MesaDTO mesa) {
		this.mesa = mesa;
	}
}
