package com.petz.api.cliente;

import static com.petz.api.core.exception.Exceptions.supplierResourceNotFound;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.petz.api.cliente.domain.Cliente;
import com.petz.api.cliente.resource.ClienteResource;

@RestController
@RequestMapping("/api")
public class ClienteRestController {

	public final ClienteService clienteService;
	
	@Autowired
	public ClienteRestController(final ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@RequestMapping(value = "/clientes", method = RequestMethod.POST)
	public @ResponseBody ClienteResource criar(@Valid @RequestBody Cliente cliente) {
		return clienteService
				.criar(cliente)
				.map(ClienteResource.map())
				.get();
	}

	@RequestMapping(value = "/clientes/{id}", method = RequestMethod.PUT)
	public @ResponseBody ClienteResource atualizar(
			@NotNull
			@Size(min = 0)
			@PathVariable Integer id, @Valid @RequestBody Cliente cliente) {
		cliente.setId(id);
		return clienteService
				.atualizar(cliente)
				.map(ClienteResource.map())
				.get();
	}

	@RequestMapping(value = "/clientes/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ClienteResource excluir(@NotNull @Size(min = 0) @PathVariable Integer id) {
		return clienteService
				.excluirPorId(id)
				.map(ClienteResource.map())
				.orElseThrow(supplierResourceNotFound("Cliente", id));
	}
	
	@RequestMapping(value = "/clientes/cpf/{cpf}", method = RequestMethod.DELETE)
	public @ResponseBody ClienteResource excluir(@NotNull @Size(min = 10) @PathVariable String cpf) {
		return clienteService
				.excluirPorCpf(cpf)
				.map(ClienteResource.map())
				.orElseThrow(supplierResourceNotFound("Cliente", cpf));
	}
	
	@RequestMapping(value = "/clientes/{id}", method = RequestMethod.GET)
	public @ResponseBody ClienteResource buscarPorId(@NotNull @Size(min = 0) @PathVariable Integer id) {
		return clienteService
				.buscarPorId(id)
				.map(ClienteResource.map())
				.orElseThrow(supplierResourceNotFound("Cliente", id));
	}

	@RequestMapping(value = "/clientes/cpf/{cpf}", method = RequestMethod.GET)
	public @ResponseBody ClienteResource buscarPorCpf(@NotNull @Size(min = 10) @PathVariable String cpf) {
		return clienteService
				.buscarPorCpf(cpf)
				.map(ClienteResource.map())
				.orElseThrow(supplierResourceNotFound("Cliente", cpf));
	}

	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public @ResponseBody Page<ClienteResource> listar(Pageable pageable) {
		return clienteService
				.listar(pageable)
				.map(ClienteResource.map());
	}

}
