package com.petz.api.pet;

import static com.petz.api.core.exception.Exceptions.supplierResourceNotFound;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.petz.api.pet.domain.Pet;
import com.petz.api.pet.resource.PetResource;

@RestController
@RequestMapping("/api")
public class PetRestController {

	public final PetService petService;
	
	@Autowired
	public PetRestController(final PetService petService) {
		this.petService = petService;
	}

	@RequestMapping(value = "/pets/{clienteId}", method = RequestMethod.POST)
	public @ResponseBody PetResource criar(@PathVariable Integer clienteId, @Valid @RequestBody Pet pet) {
		return petService.criar(clienteId, pet)
				.map(PetResource.map())
				.orElseThrow(supplierResourceNotFound("Cliente", clienteId));
	}

	@RequestMapping(value = "/pets/{id}", method = RequestMethod.PUT)
	public @ResponseBody PetResource atualizar(@PathVariable Integer id, @Valid @RequestBody Pet pet) {
		pet.setId(id);
		return petService
				.atualizar(pet)
				.map(PetResource.map())
				.orElseThrow(supplierResourceNotFound("Pet", id));
	}

	@RequestMapping(value = "/pets/{id}", method = RequestMethod.DELETE)
	public @ResponseBody PetResource excluir(@PathVariable Integer id) {
		return petService
				.excluirPorId(id)
				.map(PetResource.map())
				.orElseThrow(supplierResourceNotFound("Pet", id));
	}
	
	@RequestMapping(value = "/pets/{id}", method = RequestMethod.GET)
	public @ResponseBody PetResource buscarPorId(@PathVariable Integer id) {
		return petService
				.buscarPorId(id)
				.map(PetResource.map())
				.orElseThrow(supplierResourceNotFound("Pet", id));
	}

	@RequestMapping(value = "/pets", method = RequestMethod.GET)
	public @ResponseBody Page<PetResource> listar(Pageable pageable) {
		return petService
				.listar(pageable)
				.map(PetResource.map());
	}

}
