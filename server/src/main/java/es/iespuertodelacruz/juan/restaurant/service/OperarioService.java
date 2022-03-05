package es.iespuertodelacruz.juan.restaurant.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.juan.restaurant.entity.Operario;
import es.iespuertodelacruz.juan.restaurant.repository.OperarioRepository;

@Service
public class OperarioService implements GenericService<Operario,Integer>{

	@Autowired
	private OperarioRepository operarioRepository;
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Operario> findAll() {
		return operarioRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Operario> findAll(Pageable pageable) {
		return operarioRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Operario> findById(Integer id) {
		return operarioRepository.findById(id);
	}

	@Override
	@Transactional(readOnly=false)
	public Operario save(Operario obj) {
		return operarioRepository.save(obj);
	}

	@Override
	@Transactional(readOnly=false)
	public void deleteById(Integer id) {
		operarioRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Operario entity) {
		operarioRepository.delete(entity);
	}
	
	@Transactional(readOnly=true)
	public Operario findByNombre(String nombre) {
		Operario u = null;
		List<Operario> lista = operarioRepository.findByNombre(nombre);
		if( lista != null && lista.size() ==1)
			u = lista.get(0);
		return u;
	}
	
	@Transactional(readOnly=true)
	public Optional<Operario> findByNombreOpt(String nombre) {
		return operarioRepository.findByNombreOpt(nombre);
	}

}
