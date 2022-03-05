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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.juan.restaurant.dto.DetallefacturaDTO;
import es.iespuertodelacruz.juan.restaurant.dto.ServicioDTO;
import es.iespuertodelacruz.juan.restaurant.entity.Detallefactura;
import es.iespuertodelacruz.juan.restaurant.entity.Mesa;
import es.iespuertodelacruz.juan.restaurant.entity.Plato;
import es.iespuertodelacruz.juan.restaurant.entity.Servicio;
import es.iespuertodelacruz.juan.restaurant.service.DetallefacturaService;
import es.iespuertodelacruz.juan.restaurant.service.MesaService;
import es.iespuertodelacruz.juan.restaurant.service.PlatoService;
import es.iespuertodelacruz.juan.restaurant.service.ServicioService;
import es.iespuertodelacruz.juan.restaurant.utils.ApiError;
import es.iespuertodelacruz.juan.restaurant.utils.ApiMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v2/servicios")
@Api(value="API REST Servicios")
public class ServicioController {

	private Logger logger = LoggerFactory.getLogger(ServicioController.class);
	
	@Autowired
	ServicioService servicioService;

	@Autowired
	PlatoService platoService;

	@Autowired
	MesaService mesaService;
	
	@Autowired
	DetallefacturaService detallefacturaService;
	
	@GetMapping("")
	@ApiOperation(value="Retorna todas los servicios que esten o no pagado.")
	public ResponseEntity<?> getAll(@ApiParam(
			name =  "pagado",
			type = "Boolean",
			value = "Recibe un boolean para filtrar los servicios.",
			example = "true",
			required=false) @RequestParam(required=false, name="pagado") Boolean isPagado){
		ArrayList<ServicioDTO> prods = new ArrayList<>();
		
		List<Servicio> servicios = (isPagado == null)
				?(ArrayList<Servicio>) servicioService.findAll()
				:(ArrayList<Servicio>) servicioService.getAllPagado(isPagado);
		servicios
			.forEach(p -> prods.add(new ServicioDTO((Servicio) p)));
		
		return ResponseEntity.status(HttpStatus.OK).body(prods);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value="Retorna un servicio por id.")
	public ResponseEntity<?> getById(@ApiParam(
			name =  "id",
			type = "Integer",
			value = "ID del servicio que se esta buscando",
			example = "1",
			required = true) @PathVariable Integer id){
		Optional<Servicio> optS = servicioService.findById(id);
		
		// Comprobamos si el id existe
		if(!optS.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del servicio no existe."));
		
		return ResponseEntity.status(HttpStatus.OK).body(new ServicioDTO(optS.get()));
	}
	
	@PostMapping("")
	@ApiOperation(value="Guarda un servicio.")
	public ResponseEntity<?> save(@ApiParam(
			name =  "servicio",
			type = "Servicio",
			value = "Servicio con los datos que se quieren guardar",
			required = true) @RequestBody Servicio servicio){
		
		logger.info(servicio.getFechacomienzo() + "");
		
		// Comprobamos si introduce los campos
		if (servicio.getReservada() == null ||
				servicio.getFechacomienzo() == null )
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Debes introducir todos los campos para crear un plato."));
		
		// Comprobamos si envia la mesa.
		if (servicio.getMesa() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Selecciona una mesa."));
		
		Optional<Mesa> optM = mesaService.findById(servicio.getMesa().getNummesa());
		
		// Comprobamos si la mesa que envia el usuario existe.
		if (!optM.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "El numero de mesa no existe."));
		

		
		Servicio s = new Servicio();
		
		s.setMesa(optM.get());
		s.setFechacomienzo(servicio.getFechacomienzo());
		s.setFechafin( (servicio.getFechafin() == null ) ? servicio.getFechacomienzo() + 7200000: servicio.getFechafin());
		s.setPagada( (servicio.isPagada() == null)? false : servicio.isPagada() );
		s.setReservada( servicio.getReservada() );
		s.setDetallefacturas(new ArrayList<>());
		
		ServicioDTO savedServicio = new ServicioDTO(servicioService.save(s));
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedServicio);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value="Modifica un servicio por id.")
	public ResponseEntity<?> update(@ApiParam(
			name =  "id",
			type = "Integer",
			value = "ID del servicio que se esta buscando",
			example = "1",
			required = true) @PathVariable Integer id, 
			@ApiParam(
			name =  "servicio",
			type = "Servicio",
			value = "Servicio con los datos que se quieren guardar",
			required = true) @RequestBody Servicio servicio){
		Optional<Servicio> optS = servicioService.findById(id);
		
		// Comprobamos si el id existe
		if(!optS.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del servicio no existe."));

		Servicio s = optS.get();
		s.setMesa( s.getMesa() );
		s.setFechacomienzo( (servicio.getFechacomienzo() != null) ? servicio.getFechacomienzo() : s.getFechacomienzo() );
		s.setFechafin( (servicio.getFechacomienzo() != null) ? servicio.getFechacomienzo() + 7200000 : s.getFechafin() );
		s.setPagada( (servicio.isPagada() != null) ? servicio.isPagada() : s.isPagada() );
		s.setReservada( (servicio.getReservada() != null) ? servicio.getReservada() : s.getReservada() );

		ServicioDTO updatedServicio = new ServicioDTO(servicioService.save(s));
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(updatedServicio);
	}
	
	@DeleteMapping("{id}")
	@ApiOperation(value="Eliminar un servicio por id.")
	public ResponseEntity<?> delete(@ApiParam(
			name =  "id",
			type = "Integer",
			value = "ID del servicio que se esta buscando",
			example = "1",
			required = true) @PathVariable Integer id){
		
		Optional<Servicio> optS = servicioService.findById(id);
		// Comprobamos si el servicio existe
		if(!optS.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del servicio no existe."));
		
		Servicio s = optS.get();
		
		if (s.getDetallefacturas().size() > 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Este servicio tiene detalles asociados."));
		} else {
			servicioService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiMessage("Se ha eliminado correctamente."));
		}
	}

	
	/* (DETALLE FACTURA) */
	
	@PostMapping("{id}/plato")
	@ApiOperation(value="Guardar detalle en un servicio.")
	public ResponseEntity<?> save(@ApiParam(
			name =  "id",
			type = "Integer",
			value = "ID del servicio donde se guardaran los detalles",
			example = "1",
			required = true) @PathVariable Integer id,
			@ApiParam(
			name =  "detalleFactura",
			type = "Detallefactura",
			value = "Detallefactura con los datos que se quieren guardar dentro del servicio",
			required = true) @RequestBody Detallefactura detalleFactura){
		Optional<Servicio> optS = servicioService.findById(id);
		
		// Comprobamos si el id existe
		if(!optS.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del servicio no existe."));
		
		Optional<Plato> optP = platoService.findById(detalleFactura.getPlato().getIdplato());
		
		// Comprobamos si el plato existe.
		if (!optP.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del plato no existe."));
		
		if (!optP.get().isDisponible())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "El plato no esta disponible."));
		
		Detallefactura df = new Detallefactura();
		Servicio s = optS.get();
		df.setCantidad(detalleFactura.getCantidad());
		df.setPreciototal(Double.valueOf(optP.get().getPreciounidad() * detalleFactura.getCantidad()));
		df.setServicio(s);
		df.setPlato(optP.get());
		
		Detallefactura findDf = s.getDetallefacturas().stream()
									.filter(d -> d.getPlato().getNombre().equals(df.getPlato().getNombre()))
									.findFirst()
									.orElse(null);
			
		
		if (findDf == null) {
			detallefacturaService.save(df);
			s.getDetallefacturas().add(df);
		} else {
			findDf.setCantidad(findDf.getCantidad() + df.getCantidad());
			findDf.setPreciototal(findDf.getPreciototal() + df.getPreciototal());
			detallefacturaService.save(findDf);
		}

		ServicioDTO savedServicio = new ServicioDTO(servicioService.save(s));
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedServicio);
	}
	
	@PostMapping("{id}/platos")
	@ApiOperation(value="Guardar una lista de detalles en un servicio.")
	public ResponseEntity<?> saveList(@ApiParam(
			name =  "id",
			type = "Integer",
			value = "ID del servicio donde se guardaran los detalles",
			example = "1",
			required = true) @PathVariable Integer id, 
			@ApiParam(
			name =  "detalleFactura",
			type = "ArrayList<Detallefactura>",
			value = "Lista de Detallefactura con todos los detalles que se quieren guardar dentro del servicio",
			required = true) @RequestBody ArrayList<Detallefactura> detalleFactura){
		Optional<Servicio> optS = servicioService.findById(id);
		
		// Comprobamos si el id existe
		if(!optS.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del servicio no existe."));
		
		Servicio s = optS.get();
		
		for (Detallefactura d : detalleFactura) {
			Optional<Plato> optP = platoService.findById(d.getPlato().getIdplato());
			// Comprobamos si el plato existe.
			if (!optP.isPresent())
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiError(HttpStatus.NOT_FOUND, "El id del plato no existe."));
			
			if (!optP.get().isDisponible())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiError(HttpStatus.BAD_REQUEST, "El plato no esta disponible."));
			
			Detallefactura df = new Detallefactura();

			df.setCantidad(d.getCantidad());
			df.setPreciototal(Double.valueOf(optP.get().getPreciounidad() * d.getCantidad()));
			df.setServicio(s);
			df.setPlato(optP.get());
			
			Detallefactura findDf = s.getDetallefacturas().stream()
										.filter(d2 -> d2.getPlato().getNombre().equals(df.getPlato().getNombre()))
										.findFirst()
										.orElse(null);
				
			if (findDf == null) {
				detallefacturaService.save(df);
				s.getDetallefacturas().add(df);
			} else {
				findDf.setCantidad(findDf.getCantidad() + df.getCantidad());
				findDf.setPreciototal(findDf.getPreciototal() + df.getPreciototal());
				detallefacturaService.save(findDf);
			}
		}
		
		ServicioDTO savedServicio = new ServicioDTO(servicioService.save(s));
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedServicio);
	}
	
	@PutMapping("{idservicio}/platos/{iddetalle}")
	@ApiOperation(value="Modificar un detalle de un servicio.")
	public ResponseEntity<?> save(@ApiParam(
			name =  "idservicio",
			type = "Integer",
			value = "ID del servicio del que se modificará el detalle",
			example = "1",
			required = true) @PathVariable Integer idservicio,
			@ApiParam(
			name =  "iddetalle",
			type = "Integer",
			value = "ID del detalle que se va a modificar",
			example = "1",
			required = true) @PathVariable Integer iddetalle,
			@ApiParam(
			name =  "detalleFactura",
			type = "Detallefactura",
			value = "Detallefactura con los datos que se van a modificar",
			required = true) @RequestBody Detallefactura detalleFactura){
		
		Optional<Servicio> optS = servicioService.findById(idservicio);
		// Comprobamos si el id existe
		if(!optS.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del servicio no existe."));
		
		Optional<Detallefactura> optDf = detallefacturaService.findById(iddetalle);
		// Comprobamos si el id existe
		if(!optDf.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del detalle no existe."));
		Servicio s = optS.get();
		
		boolean isDetalleOfServicio = s.getDetallefacturas().stream()
							.anyMatch(df -> df.getIdfactura() == iddetalle);
		if (!isDetalleOfServicio) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "El detalle no pertenece a ese servicio."));
		
		Detallefactura df = optDf.get();

		
		df.setCantidad((detalleFactura.getCantidad() == null)? df.getCantidad() : detalleFactura.getCantidad());
		df.setPreciototal(Double.valueOf(df.getPlato().getPreciounidad() * ((detalleFactura.getCantidad() == null)? df.getCantidad() : detalleFactura.getCantidad())));
			
		detallefacturaService.save(df);
		
		ServicioDTO updatedServicio = new ServicioDTO(servicioService.save(s));
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(updatedServicio);
	}
	
	@DeleteMapping("{idservicio}/platos/{iddetalle}")
	@ApiOperation(value="Eliminar un detalle de un servicio.")
	public ResponseEntity<?> delete(@ApiParam(
			name =  "idservicio",
			type = "Integer",
			value = "ID del servicio del que se eliminará el detalle",
			example = "1",
			required = true) @PathVariable Integer idservicio, 
			@ApiParam(
			name =  "iddetalle",
			type = "Integer",
			value = "ID del detalle que se va a eliminar",
			example = "1",
			required = true)@PathVariable Integer iddetalle){
		
		Optional<Servicio> optS = servicioService.findById(idservicio);
		// Comprobamos si el servicio existe
		if(!optS.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del servicio no existe."));
		
		Optional<Detallefactura> optDf = detallefacturaService.findById(iddetalle);
		// Comprobamos si el detalle existe
		if(optDf.isPresent()) {
			detallefacturaService.deleteById(iddetalle);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiMessage("Se ha eliminado correctamente."));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(HttpStatus.NOT_FOUND, "El id del detalle no existe."));
		}
	}
	
	@GetMapping("{idservicio}/platos")
	@ApiOperation(value="Retorna todos los detalles de un servicio.")
	public ResponseEntity<?> getAllDetallesOfServicio(@ApiParam(
			name =  "idservicio",
			type = "Integer",
			value = "ID del servicio del que se buscarán los detalles",
			example = "1",
			required = true) @PathVariable Integer idservicio){
		Optional<Servicio> optS = servicioService.findById(idservicio);
		// Comprobamos si el servicio existe
		if(!optS.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del servicio no existe."));
		
		ArrayList<DetallefacturaDTO> prods = new ArrayList<>();
		Servicio s = optS.get();
		
		s.getDetallefacturas().forEach(d -> prods.add(new DetallefacturaDTO(d)));
		
		return ResponseEntity.status(HttpStatus.OK).body(prods);
	}
	
	@GetMapping("{idservicio}/platos/{idfactura}")
	@ApiOperation(value="Retorna un detalle por id.")
	public ResponseEntity<?> getAll(@ApiParam(
			name =  "idservicio",
			type = "Integer",
			value = "ID del servicio del que se va a buscar el detalle",
			example = "1",
			required = true) @PathVariable Integer idservicio,
			@ApiParam(
			name =  "idfactura",
			type = "Integer",
			value = "ID del detalle que se buscará",
			example = "1",
			required = true) @PathVariable Integer idfactura){
		Optional<Servicio> optS = servicioService.findById(idservicio);
		
		// Comprobamos si el idservicio existe
		if(!optS.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del servicio no existe."));
		
		Optional<Detallefactura> optDf = detallefacturaService.findById(idfactura);
		// Comprobamos si el idfactura existe
		if(!optDf.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El id del detalle no existe."));
		
		Servicio s = optS.get();
		
		Detallefactura findDf = s.getDetallefacturas().stream()
								.filter(d -> d.getIdfactura() == idfactura)
								.findFirst()
								.orElse(null);
		
		if(findDf == null) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiError(HttpStatus.NOT_FOUND, "El detalle no existe en el servicio."));
		
		
		
		return ResponseEntity.status(HttpStatus.OK).body(new DetallefacturaDTO(findDf));
	}
}
