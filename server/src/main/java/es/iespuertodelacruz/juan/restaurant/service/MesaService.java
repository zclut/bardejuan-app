package es.iespuertodelacruz.juan.restaurant.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.juan.restaurant.entity.Mesa;
import es.iespuertodelacruz.juan.restaurant.repository.MesaRepository;

@Service
public class MesaService implements GenericService<Mesa,Integer>{
	
	@Autowired
	private MesaRepository mesaRepository;

	@Override
	@Transactional(readOnly=true)
	public Iterable<Mesa> findAll() {
		return mesaRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Mesa> findAll(Pageable pageable) {
		return mesaRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Mesa> findById(Integer id) {
		return mesaRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Mesa save(Mesa obj) {
		return mesaRepository.save(obj);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(Integer id) {
		mesaRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Mesa entity) {
		mesaRepository.delete(entity);
	}
	
	@Transactional(readOnly=true)
	public Iterable<Mesa> getMesasFechaOcupantes(Long fechaReserva, Integer ocupantes){
		return mesaRepository.getMesasFechaOcupantes(
				BigInteger.valueOf(fechaReserva),
				BigInteger.valueOf(new Date().getTime()),
				ocupantes);
	}

}
