package com.petz.api.cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petz.api.cliente.domain.Cliente;

@Repository
interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	void deleteById(Integer id);

	Optional<Cliente> deleteOneByCpf(String cpf);

	Optional<Cliente> findById(Integer id);

	Optional<Cliente> findOneByCpf(String cpf);

}
