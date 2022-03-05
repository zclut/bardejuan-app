package es.iespuertodelacruz.juan.restaurant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import es.iespuertodelacruz.juan.restaurant.entity.Servicio;
import es.iespuertodelacruz.juan.restaurant.repository.ServicioRepository;

@Service
public class ServicioService implements GenericService<Servicio,Integer>{

	@Autowired
	private ServicioRepository servicioRepository;
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Servicio> findAll() {
		return servicioRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Servicio> findAll(Pageable pageable) {
		return servicioRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Servicio> findById(Integer id) {
		return servicioRepository.findById(id);
	}

	@Override
	@Transactional(readOnly=false)
	public Servicio save(Servicio obj) {
		return servicioRepository.save(obj);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Integer id) {
		servicioRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Servicio entity) {
		servicioRepository.delete(entity);
	}
	
	@Transactional(readOnly=true)
	public Iterable<Servicio> getAllPagado(Boolean isPagado) {
		return servicioRepository.getAllPagado(isPagado);
	}
}
