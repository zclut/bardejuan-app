package es.iespuertodelacruz.juan.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.iespuertodelacruz.juan.restaurant.entity.Servicio;


public interface ServicioRepository extends JpaRepository<Servicio, Integer>{

	@Query("SELECT s FROM Servicio s WHERE s.pagada = :isPagada")
	Iterable<Servicio> getAllPagado(@Param("isPagada") boolean isPagada);
}
