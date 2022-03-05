package es.iespuertodelacruz.juan.restaurant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.juan.restaurant.entity.Detallefactura;
import es.iespuertodelacruz.juan.restaurant.entity.Servicio;
import es.iespuertodelacruz.juan.restaurant.repository.DetallefacturaRepository;

@Service
public class DetallefacturaService implements GenericService<Detallefactura,Integer>{

	@Autowired
	private DetallefacturaRepository detalleFacturaRepository;
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Detallefactura> findAll() {
		return detalleFacturaRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Detallefactura> findAll(Pageable pageable) {
		return detalleFacturaRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Detallefactura> findById(Integer id) {
		return detalleFacturaRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Detallefactura save(Detallefactura obj) {
		return detalleFacturaRepository.save(obj);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Integer id) {
		detalleFacturaRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Detallefactura entity) {
		detalleFacturaRepository.delete(entity);
	}
}
