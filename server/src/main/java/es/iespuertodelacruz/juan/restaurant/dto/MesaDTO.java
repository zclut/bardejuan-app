package es.iespuertodelacruz.juan.restaurant.dto;

import es.iespuertodelacruz.juan.restaurant.entity.Mesa;

public class MesaDTO {
	private Integer nummesa;
	private Integer ocupantesmax;
	
	public MesaDTO(Mesa m) {
		super();
		this.nummesa = m.getNummesa();
		this.ocupantesmax = m.getOcupantesmax();
	}
	
	public Integer getNummesa() {
		return nummesa;
	}
	
	public void setNummesa(Integer nummesa) {
		this.nummesa = nummesa;
	}
	
	public Integer getOcupantesmax() {
		return ocupantesmax;
	}
	
	public void setOcupantesmax(Integer ocupantesmax) {
		this.ocupantesmax = ocupantesmax;
	}

}
