package es.iespuertodelacruz.juan.restaurant.controller.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.juan.restaurant.dto.MesaDTO;
import es.iespuertodelacruz.juan.restaurant.dto.PlatoDTO;
import es.iespuertodelacruz.juan.restaurant.entity.Mesa;
import es.iespuertodelacruz.juan.restaurant.entity.Plato;
import es.iespuertodelacruz.juan.restaurant.service.PlatoService;
import es.iespuertodelacruz.juan.restaurant.utils.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v2/platos")
@Api(value="API REST Platos v2")
public class PlatoController {
	
	private Logger logger = LoggerFactory.getLogger(PlatoController.class);
	
	@Autowired
	PlatoService platoService;

	@GetMapping("")
	@ApiOperation(value="Retorna todos los platos que esten o no disponibles.")
	public ResponseEntity<?> getAll(@ApiParam(
			name =  "disponible",
			type = "Boolean",
			value = "Recibe un boolean para filtrar los platos.",
			example = "true",
			required=false) @RequestParam(required=false, name="disponible") Boolean isDisponible){
		ArrayList<PlatoDTO> prods = new ArrayList<>();
		
		List<Plato> platos = (isDisponible == null)
				?(ArrayList<Plato>) platoService.findAll()
				:(ArrayList<Plato>) platoService.getAllDisponible(isDisponible);
		platos
			.forEach(p -> prods.add(new PlatoDTO((Plato) p)));
		
		return ResponseEntity.status(HttpStatus.OK).body(prods);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value="Retorna un plato por id.")
	public ResponseEntity<?> getById(@ApiParam(
			name =  "id",
			type = "Integer",
			value = "ID del plato que se esta buscando",
			example = "1",
			required = true) @PathVariable Integer id){
		Optional<Plato> optP = platoService.findById(id);
		
		// Comprobamos si el id existe
		if(!optP.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del plato no existe."));
		
		return ResponseEntity.status(HttpStatus.OK).body(new PlatoDTO(optP.get()));
	}
	
	@PostMapping("")
	@ApiOperation(value="Guarda un plato.")
	public ResponseEntity<?> save(@ApiParam(
			name =  "plato",
			type = "Plato",
			value = "Plato con los datos que se quieren guardar",
			required = true) @RequestBody Plato plato){

		// Comprobamos si introduce los campos
		if (plato.getNombre() == null && plato.getDescripcion() == null &&
				plato.getPreciounidad() == null && plato.isDisponible() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Debes introducir todos los campos para crear un plato."));
		
		Plato p = new Plato();
		p.setNombre(plato.getNombre());
		p.setDescripcion(plato.getDescripcion());
		p.setPreciounidad(plato.getPreciounidad());
		p.setDisponible(plato.isDisponible());
		
		PlatoDTO savedPlato = new PlatoDTO(platoService.save(p));
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedPlato);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value="Modifica un plato por id.")
	public ResponseEntity<?> update(@ApiParam(
			name =  "id",
			type = "Integer",
			value = "ID del plato que se esta modificando",
			example = "1",
			required = true) @PathVariable Integer id, @ApiParam(
			name =  "plato",
			type = "Plato",
			value = "Plato con los datos que se quieren modificar",
			required = true) @RequestBody Plato plato){
		Optional<Plato> optP = platoService.findById(id);
		
		// Comprobamos si el id existe
		if(!optP.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del plato no existe."));

		Plato p = optP.get();
		p.setNombre( (plato.getNombre() != null) ? plato.getNombre() : p.getNombre() );
		p.setDescripcion( (plato.getDescripcion() != null) ? plato.getDescripcion() : p.getDescripcion() );
		p.setPreciounidad( (plato.getPreciounidad() != null) ? plato.getPreciounidad() : p.getPreciounidad() );
		p.setDisponible( (plato.isDisponible() != null) ? plato.isDisponible() : p.isDisponible() );

		PlatoDTO updatedPlato = new PlatoDTO(platoService.save(p));
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(updatedPlato);
	}
}
