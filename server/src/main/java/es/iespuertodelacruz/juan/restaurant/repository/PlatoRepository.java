package es.iespuertodelacruz.juan.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.iespuertodelacruz.juan.restaurant.entity.Plato;

public interface PlatoRepository extends JpaRepository<Plato, Integer>{

	@Query("SELECT p FROM Plato p WHERE p.disponible = :isDisponible")
	Iterable<Plato> getAllDisponible(@Param("isDisponible") boolean isDisponible);
}
