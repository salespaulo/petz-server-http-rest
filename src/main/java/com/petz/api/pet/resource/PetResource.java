package com.petz.api.pet.resource;

import java.util.function.Function;

import com.petz.api.pet.domain.Pet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class PetResource {

	private Integer id;

	private String nome;

	private String especie;

	private String genero;

	private String descricao;

	public static final Function<Pet, PetResource> map() {
		return pet -> new PetResource(pet.getId(), 
				pet.getNome(), 
				pet.getEspecie().toString(), 
				pet.getGenero().toString(),
				pet.getDescricao());
	}

}
