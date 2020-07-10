package com.petz.api.pet;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.petz.api.pet.domain.Pet;
import com.petz.api.pet.domain.PetEspecie;
import com.petz.api.pet.domain.PetGenero;

public interface PetService {

	Page<Pet> listarPorNome(String nome, Pageable pageable);

	Page<Pet> listarPorEspecie(PetEspecie especie, Pageable pageable);

	Page<Pet> listarPorGenero(PetGenero genero, Pageable pageable);

	Page<Pet> listar(Pageable pageable);

	Optional<Pet> buscarPorId(Integer id);

	Optional<Pet> excluirPorId(Integer id);

	Optional<Pet> atualizar(Pet cliente);

	Optional<Pet> criar(Pet cliente);


}
