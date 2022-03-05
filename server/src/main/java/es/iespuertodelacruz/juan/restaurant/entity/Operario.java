package es.iespuertodelacruz.juan.restaurant.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the operario database table.
 * 
 */
@Entity
@Table(name="operario")
@NamedQuery(name="Operario.findAll", query="SELECT o FROM Operario o")
public class Operario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idoperario;

	private String nombre;

	private String password;

	private String rol;

	public Operario() {
	}

	public Integer getIdoperario() {
		return this.idoperario;
	}

	public void setIdoperario(Integer idoperario) {
		this.idoperario = idoperario;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}