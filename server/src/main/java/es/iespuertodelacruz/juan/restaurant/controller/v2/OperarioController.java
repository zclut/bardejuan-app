package es.iespuertodelacruz.juan.restaurant.controller.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.juan.restaurant.entity.Operario;
import es.iespuertodelacruz.juan.restaurant.security.GestorDeJWT;
import es.iespuertodelacruz.juan.restaurant.service.OperarioService;
import es.iespuertodelacruz.juan.restaurant.utils.ApiError;
import es.iespuertodelacruz.juan.restaurant.utils.ApiMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value="API REST Operarios")
public class OperarioController {

	@Autowired
	OperarioService operarioService;

	@PostMapping(path = "/api/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Inicia sesión y devuelve un token.")
	public ResponseEntity<?> login(@ApiParam(
			name =  "usuarioJson",
			type = "UsuarioJsonLogin",
			value = "Usuario con las credenciales para iniciar sesión",
			required = true) @RequestBody UsuarioJsonLogin usuarioJson) {
		String token = getJWTToken(usuarioJson.nombre, usuarioJson.password);
		// token nulo si usuario/pass no es válido
		if (token != null) {
			return ResponseEntity.ok(token);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new ApiError(HttpStatus.FORBIDDEN, "Credenciales incorrectas."));
	}

	@PostMapping(path = "/api/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Guardar un operario.")
	public ResponseEntity<?> register(@ApiParam(
			name =  "usuarioJson",
			type = "UsuarioJsonLogin",
			value = "Usuario con las credenciales para registrarse",
			required = true) @RequestBody UsuarioJsonLogin usuarioJson) {
		Optional<Operario> optO = operarioService.findByNombreOpt(usuarioJson.getNombre());

		// Comprueba si hay algun operario con el nombre enviado
		if (optO.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Ya existe un operario con ese nombre."));

		// Comprobamos si envia el nombre y la contraseña y son nulos
		if (usuarioJson.getNombre() == null && usuarioJson.getPassword() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Debes introducir todos los campos."));

		// Comprueba si envia el nombre vacio
		if (usuarioJson.getNombre().trim().isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Debes introducir un nombre valido."));

		// Comprueba si envia la contraseña vacia
		if (usuarioJson.getPassword().trim().isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiError(HttpStatus.BAD_REQUEST, "Debes introducir una contraseña valida."));

		// Hasheamos la contraseña y creamos el operario
		String password = BCrypt.hashpw(usuarioJson.getPassword(), BCrypt.gensalt(10));
		Operario o = new Operario();
		o.setNombre(usuarioJson.getNombre());
		o.setPassword(password);
		o.setRol("ROLE_USER");
		operarioService.save(o);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiMessage("Creado correctamente."));
	}

	private String getJWTToken(String username, String passTextoPlanoRecibida) {
		String respuesta = null;
		GestorDeJWT gestorDeJWT = GestorDeJWT.getInstance();
		Operario operario = operarioService.findByNombre(username);

		String passwordUsuarioEnHash = "";
		boolean autenticado = false;

		if (operario != null) {
			passwordUsuarioEnHash = operario.getPassword();
			autenticado = BCrypt.checkpw(passTextoPlanoRecibida, passwordUsuarioEnHash);
		}

		if (autenticado) {
			String rol = operario.getRol();
			List<String> roles = new ArrayList<String>();
			roles.add(rol);

			int duracionMinutos = 600;
			String token = gestorDeJWT.generarToken(username, roles, duracionMinutos);
			respuesta = gestorDeJWT.BEARERPREFIX + token;
		}

		return respuesta;
	}

	static class UsuarioJsonLogin {
		String nombre;
		String password;

		public String getNombre() {
			return nombre;
		};

		public String getPassword() {
			return password;
		};

		public void setNombre(String nombre) {
			this.nombre = nombre;
		};

		public void setPassword(String password) {
			this.password = password;
		};
	}
}
