package com.petz.api.cliente;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.petz.api.cliente.domain.Cliente;

@Service
@Transactional
class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Secured("CLIENTE_SAVE")
	@Override
	public Optional<Cliente> criar(final Cliente cliente) {
		return Optional.of(clienteRepository.save(cliente));
	}

	@Secured("CLIENTE_SAVE")
	@Override
	public Optional<Cliente> atualizar(final Cliente cliente) {
		return clienteRepository
				.findById(cliente.getId())
				.map(byId -> {
					byId.setLogradouro(cliente.getLogradouro());
					byId.setCep(cliente.getCep());
					return byId;
				})
				.map(clienteRepository::save);
	}

	@Secured("CLIENTE_DELETE")
	@Override
	public Optional<Cliente> excluirPorId(final Integer id) {
		return clienteRepository.findById(id)
					.map(cliente -> {
						clienteRepository.deleteById(id);
						return cliente;
					});
	}

	@Secured("CLIENTE_DELETE")
	@Override
	public Optional<Cliente> excluirPorCpf(final String cpf) {
		return clienteRepository.deleteOneByCpf(cpf);
	}

	@Secured("CLIENTE_GET")
	@Override
	public Optional<Cliente> buscarPorId(final Integer id) {
		return clienteRepository.findById(id);
	}

	@Secured("CLIENTE_GET")
	@Override
	public Optional<Cliente> buscarPorCpf(final String cpf) {
		return clienteRepository.findOneByCpf(cpf);
	}

	@Secured("CLIENTE_GET")
	@Override
	public Page<Cliente> listar(final Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

}
