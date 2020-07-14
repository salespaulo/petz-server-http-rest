package com.petz.api.pet;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petz.api.pet.domain.Pet;
import com.petz.api.pet.domain.PetEspecie;
import com.petz.api.pet.domain.PetGenero;

@Repository
interface PetRepository extends JpaRepository<Pet, Integer> {

	void deleteById(Integer id);

	Optional<Pet> findById(Integer id);

	Page<Pet> findAllByNome(String nome, Pageable pageable);

	Page<Pet> findAllByEspecie(PetEspecie especie, Pageable pageable);

	Page<Pet> findAllByGenero(PetGenero genero, Pageable pageable);

}
