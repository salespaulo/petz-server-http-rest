package com.petz.api.cliente;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.petz.api.cliente.domain.Cliente;

public interface ClienteService {

	Page<Cliente> listar(Pageable pageable);

	Optional<Cliente> buscarPorCpf(String cpf);

	Optional<Cliente> buscarPorId(Integer id);

	Optional<Cliente> excluirPorCpf(String cpf);

	Optional<Cliente> excluirPorId(Integer id);

	Optional<Cliente> atualizar(Cliente cliente);

	Optional<Cliente> criar(Cliente cliente);


}
