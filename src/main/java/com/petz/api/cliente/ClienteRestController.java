package com.petz.api.cliente;

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

import com.petz.api.cliente.domain.Cliente;
import com.petz.api.cliente.resource.ClienteResource;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

	public final ClienteService clienteService;
	
	@Autowired
	public ClienteRestController(final ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ClienteResource criar(@RequestBody Cliente cliente) {
		return clienteService
				.criar(cliente)
				.map(ClienteResource.map())
				.get();
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public @ResponseBody ClienteResource atualizar(@RequestBody Cliente cliente) {
		return clienteService
				.atualizar(cliente)
				.map(ClienteResource.map())
				.get();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ClienteResource excluir(@PathVariable Integer id) {
		return clienteService
				.excluirPorId(id)
				.map(ClienteResource.map())
				.orElseThrow(supplierResourceNotFound("Cliente", id));
	}
	
	@RequestMapping(value = "/cpf/{cpf}", method = RequestMethod.DELETE)
	public @ResponseBody ClienteResource excluir(@PathVariable String cpf) {
		return clienteService
				.excluirPorCpf(cpf)
				.map(ClienteResource.map())
				.orElseThrow(supplierResourceNotFound("Cliente", cpf));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody ClienteResource buscarPorId(@PathVariable Integer id) {
		return clienteService
				.buscarPorId(id)
				.map(ClienteResource.map())
				.orElseThrow(supplierResourceNotFound("Cliente", id));
	}

	@RequestMapping(value = "/cpf/{cpf}", method = RequestMethod.GET)
	public @ResponseBody ClienteResource buscarPorCpf(@PathVariable String cpf) {
		return clienteService
				.buscarPorCpf(cpf)
				.map(ClienteResource.map())
				.orElseThrow(supplierResourceNotFound("Cliente", cpf));
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody Page<ClienteResource> listar(Pageable pageable) {
		return clienteService
				.listar(pageable)
				.map(ClienteResource.map());
	}

}
