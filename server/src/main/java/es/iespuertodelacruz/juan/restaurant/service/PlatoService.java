package es.iespuertodelacruz.juan.restaurant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.juan.restaurant.entity.Plato;
import es.iespuertodelacruz.juan.restaurant.repository.PlatoRepository;

@Service
public class PlatoService implements GenericService<Plato,Integer>{

	@Autowired
	private PlatoRepository platoRepository;
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Plato> findAll() {
		return platoRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Plato> findAll(Pageable pageable) {
		return platoRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Plato> findById(Integer id) {
		return platoRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Plato save(Plato obj) {
		return platoRepository.save(obj);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Integer id) {
		platoRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Plato entity) {
		platoRepository.delete(entity);
	}

	@Transactional(readOnly=true)
	public Iterable<Plato> getAllDisponible(boolean isDisponible){
		return platoRepository.getAllDisponible(isDisponible);
	}
}
