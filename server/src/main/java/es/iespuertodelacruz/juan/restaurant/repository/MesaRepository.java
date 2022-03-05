package es.iespuertodelacruz.juan.restaurant.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.iespuertodelacruz.juan.restaurant.entity.Mesa;

public interface MesaRepository extends JpaRepository<Mesa, Integer>{

	//@Query("SELECT m FROM Mesa m, Servicio s WHERE :fechaReserva >= :fechaActual AND ((:fechaReserva + 720000 < s.fechacomienzo OR :fechaReserva >= s.fechafin) OR (s.pagada = true)) AND :ocupantes <= m.ocupantesmax")
	
	@Query(nativeQuery = true,
			value="SELECT DISTINCT mesas.nummesa, mesas.ocupantesmax FROM mesas "
					+ "LEFT JOIN servicio ON mesas.nummesa = servicio.fkmesa "
					+ "WHERE :fechaReserva >= :fechaActual AND \n"
					+ "((:fechaReserva + 7200000 < servicio.fechacomienzo OR :fechaReserva >= servicio.fechafin) OR "
					+ "(servicio.pagada = true) Or mesas.nummesa NOT IN (SELECT servicio.fkmesa FROM servicio)) AND "
					+ ":ocupantes <= mesas.ocupantesmax;")
	Iterable<Mesa> getMesasFechaOcupantes(
			@Param("fechaReserva") BigInteger fechaReserva,
			@Param("fechaActual") BigInteger fechaActual,
			@Param("ocupantes") Integer ocupantes);
}
