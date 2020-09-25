package br.com.fns.centralatendimento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.fns.centralatendimento.model.cliente.Cliente;
import br.com.fns.centralatendimento.model.endereco.Endereco;
import br.com.fns.centralatendimento.repository.ClienteRepository;
import br.com.fns.centralatendimento.service.EnderecoService;

@RestController
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/listaclientes", method = RequestMethod.GET, produces = { "application/json" })
	public Iterable<Cliente> listaClientes(Model model) {

		Iterable<Cliente> clientes = repository.findAll();

		return clientes;
	}

	@RequestMapping(value = "/listaclientes/{cpf}", method = RequestMethod.GET, produces = { "application/json" })
	public Cliente cliente(@PathVariable("cpf") String cpf) {

		List<Cliente> cliente = repository.findByCpf(cpf);

		if (cliente != null && cliente.iterator().hasNext())
			return cliente.iterator().next();

		return new Cliente();
	}

	@RequestMapping(value = "/listaclientes/{cpf}/endereco", method = RequestMethod.GET, produces = {
			"application/json" })
	public Endereco getEnderecoClientePorCep(@PathVariable("cpf") String cpf) {
		try {

			List<Cliente> clientes = repository.findByCpf(cpf);

			if (clientes != null && clientes.iterator().hasNext())
				return EnderecoService.buscaEnderecoPeloCep(clientes.iterator().next().getCep());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return new Endereco();
	}

	@RequestMapping(value = "/alterar/{id}", method = RequestMethod.POST)
	public Cliente alterar(@PathVariable(value = "id") Long id, @RequestBody Cliente cliente) {

		if (repository.findById(id).isPresent()) {
			Cliente clienteExistente = repository.findById(id).get();
			clienteExistente.setCep(cliente.getCep());

			repository.save(clienteExistente);

			return clienteExistente;
		}

		return new Cliente();

	}

}
