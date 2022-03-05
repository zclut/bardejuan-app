package es.iespuertodelacruz.juan.restaurant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.iespuertodelacruz.juan.restaurant.entity.Operario;

public interface OperarioRepository extends JpaRepository<Operario, Integer>{
	
    @Query("SELECT o FROM Operario o where o.nombre = :name") 
    List<Operario> findByNombre(@Param("name") String strNombre);
    
    @Query("SELECT o FROM Operario o where o.nombre = :name") 
    Optional<Operario> findByNombreOpt(@Param("name") String strNombre);
}
