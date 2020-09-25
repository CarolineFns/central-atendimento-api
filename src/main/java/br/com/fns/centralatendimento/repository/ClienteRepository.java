package br.com.fns.centralatendimento.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.fns.centralatendimento.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

	List<Cliente> findByCpf(String cpf);
}
