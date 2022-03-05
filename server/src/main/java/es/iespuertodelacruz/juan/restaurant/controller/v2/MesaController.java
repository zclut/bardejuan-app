package es.iespuertodelacruz.juan.restaurant.controller.v2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.juan.restaurant.dto.MesaDTO;
import es.iespuertodelacruz.juan.restaurant.dto.ServicioDTO;
import es.iespuertodelacruz.juan.restaurant.entity.Mesa;
import es.iespuertodelacruz.juan.restaurant.entity.Servicio;
import es.iespuertodelacruz.juan.restaurant.service.MesaService;
import es.iespuertodelacruz.juan.restaurant.utils.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v2/mesas")
@Api(value="API REST Mesas")
public class MesaController {

	private Logger logger = LoggerFactory.getLogger(MesaController.class);
	
	@Autowired
	MesaService mesaService;
	
	
	@GetMapping("")
	@ApiOperation(value="Retorna todas las mesas o chequea una reserva.")
	public ResponseEntity<?> getAllOrCheckReserve(@ApiParam(
			name =  "fecha",
			type = "Long",
			value = "Recibe una fecha para buscar las mesas disponibles.",
			example = "true",
			required=false) @RequestParam(required=false, name="fecha") Long fecha,
			@ApiParam(
			name =  "ocupantes",
			type = "Integer",
			value = "Recibe un numero de ocupantes para filtrar las mesas disponibles.",
			example = "true",
			required=false) @RequestParam(required=false, name="ocupantes") Integer ocupantes
	) {
		ArrayList<MesaDTO> prods = new ArrayList<>();
		
		// Comprobamos si los parametros son null
		if (fecha == null && ocupantes == null) {
			mesaService.findAll()
				.forEach(m -> prods.add(new MesaDTO((Mesa) m)));
			return ResponseEntity.status(HttpStatus.OK).body(prods);
		}
		
		// Comprobamos si la fecha es null
		if (fecha == null || fecha.toString().trim().isEmpty()) 
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)	
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Introduce la fecha de reserva."));
		
		// Comprobamos si los ocupantes son null
		if (ocupantes == null || ocupantes.toString().trim().isEmpty())
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Introduce la cantidad de ocupantes."));
		
		// Comprobamos si la fecha que envia es menor que la actual
		Long now = new Date().getTime();
		if (fecha < now)
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Introduce una fecha mayor que la actual."));
		
		
		// Buscamos las mesas disponibles para hacer la reserva en una fecha y ocupantes
		List<Mesa> mesas = (ArrayList<Mesa>) mesaService.getMesasFechaOcupantes(fecha, ocupantes);
		mesas.forEach(m -> prods.add(new MesaDTO((Mesa) m)));
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(prods);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value="Retorna una mesa por id.")
	public ResponseEntity<?> getAll(@ApiParam(
			name =  "id",
			type = "Integer",
			value = "ID de la mesa que se esta buscando",
			example = "1",
			required = true) @PathVariable Integer id){
		Optional<Mesa> optM = mesaService.findById(id);
		
		// Comprobamos si el id existe
		if(!optM.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El numero de mesa no existe."));
		
		return ResponseEntity.status(HttpStatus.OK).body(new MesaDTO(optM.get()));
	}
	
	@PostMapping("")
	@ApiOperation(value="Guarda una mesa.")
	public ResponseEntity<?> save(@ApiParam(
			name =  "mesa",
			type = "Mesa",
			value = "Mesa con los datos que se quieren guardar",
			required = true) @RequestBody Mesa mesa){
		
		Optional<Mesa> optM = mesaService.findById(mesa.getNummesa());
		
		// Comprobamos si eiste una mesa con ese numero
		if (optM.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Ya existe una mesa con ese numero."));
		
		// Comprobamos si introduce los campos
		if (mesa.getNummesa() == null && mesa.getOcupantesmax() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Debes introducir todos los campos para crear una mesa."));
		
		Mesa m = new Mesa();
		m.setNummesa(mesa.getNummesa());
		m.setOcupantesmax(mesa.getOcupantesmax());
		
		MesaDTO savedMesa = new MesaDTO(mesaService.save(m));
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedMesa);
	}
	

	@PutMapping("/{id}")
	@ApiOperation(value="Modifica una mesa por id.")
	public ResponseEntity<?> update(@ApiParam(
			name =  "id",
			type = "Integer",
			value = "ID de la mesa que se quiere modificar",
			example = "1",
			required = true) @PathVariable Integer id,
			@ApiParam(
			name =  "mesa",
			type = "Mesa",
			value = "Mesa con los datos que se quieren modificar",
			required = true) @RequestBody Mesa mesa){
		Optional<Mesa> optM = mesaService.findById(id);
		
		// Comprobamos si el numero de mesa existe
		if(!optM.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El numero de mesa no existe."));
		
		// Comprobamos si quiere modificar otra mesa
		if(mesa.getNummesa() != id)
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "No puedes cambiar el numero de otra mesa."));

		Mesa m = optM.get();
		m.setNummesa( (mesa.getNummesa() != null) ? mesa.getNummesa() : m.getNummesa());
		m.setOcupantesmax( (mesa.getOcupantesmax() != null) ? mesa.getOcupantesmax() : m.getOcupantesmax() );

		MesaDTO updatedMesa = new MesaDTO(mesaService.save(m));
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(updatedMesa);
	}
}
