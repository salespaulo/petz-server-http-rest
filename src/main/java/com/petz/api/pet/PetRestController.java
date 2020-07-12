package com.petz.api.pet;

import static com.petz.api.core.exception.Exceptions.supplierResourceNotFound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.petz.api.cliente.ClienteService;
import com.petz.api.pet.domain.Pet;
import com.petz.api.pet.resource.PetResource;

@RestController
@RequestMapping("/api")
public class PetRestController {

	public final PetService petService;
	public final ClienteService clienteService;
	
	@Autowired
	public PetRestController(final PetService petService, ClienteService clienteService) {
		this.petService = petService;
		this.clienteService = clienteService;
	}

	@RequestMapping(value = "/pets/{clienteId}", method = RequestMethod.POST)
	public @ResponseBody PetResource criar(@PathVariable Integer clienteId, @RequestBody Pet pet) {
		return clienteService.buscarPorId(clienteId)
				.map(cliente -> {
					cliente.getPets().add(pet);
					clienteService.atualizar(cliente);
					return pet;
				})
				.map(PetResource.map())
				.orElseThrow(supplierResourceNotFound("Cliente", clienteId));
	}

	@RequestMapping(value = "/pets", method = RequestMethod.PUT)
	public @ResponseBody PetResource atualizar(@RequestBody Pet pet) {
		return petService
				.atualizar(pet)
				.map(PetResource.map())
				.get();
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
