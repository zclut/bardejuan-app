package es.iespuertodelacruz.juan.restaurant.controller.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.juan.restaurant.controller.v2.PlatoController;
import es.iespuertodelacruz.juan.restaurant.dto.PlatoDTO;
import es.iespuertodelacruz.juan.restaurant.entity.Plato;
import es.iespuertodelacruz.juan.restaurant.service.PlatoService;
import es.iespuertodelacruz.juan.restaurant.utils.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/platos")
@Api(value="API REST Platos v1")
public class PlatoControllerV1 {
	
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
		
		List<Plato> platos = (ArrayList<Plato>) platoService.getAllDisponible(true);
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
}
