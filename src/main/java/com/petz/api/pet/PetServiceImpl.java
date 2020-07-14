package com.petz.api.pet;

import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.petz.api.cliente.ClienteService;
import com.petz.api.cliente.domain.Cliente;
import com.petz.api.pet.domain.Pet;
import com.petz.api.pet.domain.PetEspecie;
import com.petz.api.pet.domain.PetGenero;

@Service
@Transactional
class PetServiceImpl implements PetService {

	private final ClienteService clienteService;
	private final PetRepository petRepository;
	
	@Autowired
	public PetServiceImpl(final ClienteService clienteService, final PetRepository petRepository) {
		this.clienteService = clienteService;
		this.petRepository = petRepository;
	}

	@Secured("PET_SAVE")
	@Override
	public Optional<Pet> criar(final Integer clienteId, final Pet pet) {
		return clienteService
				.buscarPorId(clienteId)
				.map(adicionarCliente(pet))
				.map(petRepository::save);
	}

	@Secured("PET_SAVE")
	@Override
	public Optional<Pet> atualizar(final Pet pet) {
		return petRepository
				.findById(pet.getId())
				.map(byId -> {
					byId.setNome(pet.getNome());
					byId.setEspecie(pet.getEspecie());
					byId.setGenero(pet.getGenero());
					byId.setDescricao(pet.getDescricao());
					return byId;
				})
				.map(petRepository::save);
	}

	@Secured("PET_DELETE")
	@Override
	public Optional<Pet> excluirPorId(final Integer id) {
		return petRepository.findById(id)
					.map(pet -> {
						petRepository.deleteById(id);
						return pet;
					});
	}

	@Secured("PET_GET")
	@Override
	public Optional<Pet> buscarPorId(final Integer id) {
		return petRepository.findById(id);
	}

	@Secured("PET_GET")
	@Override
	public Page<Pet> listar(final Pageable pageable) {
		return petRepository.findAll(pageable);
	}

	@Override
	public Page<Pet> listarPorNome(String nome, Pageable pageable) {
		return petRepository.findAllByNome(nome, pageable);
	}

	@Override
	public Page<Pet> listarPorEspecie(PetEspecie especie, Pageable pageable) {
		return petRepository.findAllByEspecie(especie, pageable);
	}

	@Override
	public Page<Pet> listarPorGenero(PetGenero genero, Pageable pageable) {
		return petRepository.findAllByGenero(genero, pageable);
	}

	private static final Function<Cliente, Pet> adicionarCliente(final Pet pet) {
		return cliente -> {
			pet.setCliente(cliente);
			return pet;
		};
	}

}
