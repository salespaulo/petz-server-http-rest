package com.petz.api.cliente.resource;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.petz.api.cliente.domain.Cliente;
import com.petz.api.pet.resource.PetResource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ClienteResource {

	private String cpf;

	private String logradouro;

	private String cep;

	private Set<PetResource> pets;

	public static final Function<Cliente, ClienteResource> map() {
		return cliente -> new ClienteResource(cliente.getCpf(), cliente.getLogradouro(), cliente.getCep(),
				cliente.getPets().stream().map(PetResource.map()).collect(Collectors.toSet()));
	}

}
