package com.petz.api.pet;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.petz.api.pet.domain.Pet;
import com.petz.api.pet.domain.PetEspecie;
import com.petz.api.pet.domain.PetGenero;

@Service
@Transactional
class PetServiceImpl implements PetService {

	@Autowired
	private PetRepository clienteRepository;

	@Secured("PET_SAVE")
	@Override
	public Optional<Pet> criar(final Pet cliente) {
		return Optional.of(clienteRepository.save(cliente));
	}

	@Secured("PET_SAVE")
	@Override
	public Optional<Pet> atualizar(final Pet cliente) {
		return Optional.of(clienteRepository.save(cliente));
	}

	@Secured("PET_DELETE")
	@Override
	public Optional<Pet> excluirPorId(final Integer id) {
		return clienteRepository.findById(id)
					.map(cliente -> {
						clienteRepository.deleteById(id);
						return cliente;
					});
	}

	@Secured("PET_GET")
	@Override
	public Optional<Pet> buscarPorId(final Integer id) {
		return clienteRepository.findById(id);
	}

	@Secured("PET_GET")
	@Override
	public Page<Pet> listar(final Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	@Override
	public Page<Pet> listarPorNome(String nome, Pageable pageable) {
		return clienteRepository.findAllByNome(nome, pageable);
	}

	@Override
	public Page<Pet> listarPorEspecie(PetEspecie especie, Pageable pageable) {
		return clienteRepository.findAllByEspecie(especie, pageable);
	}

	@Override
	public Page<Pet> listarPorGenero(PetGenero genero, Pageable pageable) {
		return clienteRepository.findAllByGenero(genero, pageable);
	}

}
