package br.com.fns.centralatendimento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.util.StringUtils;

import br.com.fns.centralatendimento.model.Cliente;
import br.com.fns.centralatendimento.model.Endereco;
import br.com.fns.centralatendimento.model.Estado;
import br.com.fns.centralatendimento.model.Municipio;
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
	public Cliente cliente(@PathVariable("cpf") String cpf, Model model) {

		List<Cliente> cliente = repository.findByCpf(cpf);

		if (cliente != null && cliente.iterator().hasNext())
			return cliente.iterator().next();

		return new Cliente();
	}

	@RequestMapping(value = "/listaclientes/{cpf}/endereco", method = RequestMethod.GET, produces = {
			"application/json" })
	public Endereco getEnderecoClientePorCep(@PathVariable("cpf") String cpf, Model model) {
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

	@RequestMapping(value = "endereco/{cep}", method = RequestMethod.GET, produces = { "application/json" })
	public Endereco getEnderecoPorCep(@PathVariable("cep") String cep, Model model) {
		try {

			if (!StringUtils.isNullOrEmpty(cep))
				return EnderecoService.buscaEnderecoPeloCep(cep);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return new Endereco();
	}

	@RequestMapping(value = "estados", method = RequestMethod.GET, produces = { "application/json" })
	public List<Estado> getEstados() {
		try {
			List<Estado> estados = EnderecoService.buscaEstados();
			Collections.sort(estados);

			List<Estado> listaFiltrada = new ArrayList<>();
			estados.forEach(e -> {
				if (e.getSigla().equals("RJ") || e.getSigla().equals("SP"))
					listaFiltrada.add(e);
			});

			Collections.reverse(listaFiltrada);

			listaFiltrada.addAll(estados);

			Set<Estado> listaSemDuplicadas = new LinkedHashSet<>(listaFiltrada);
			List<Estado> listaFinal = new ArrayList<>(listaSemDuplicadas);

			return listaFinal;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return new ArrayList<Estado>();
	}

	@RequestMapping(value = "estados/{idEstado}", method = RequestMethod.GET, produces = { "application/json" })
	public Estado getEstadoPeloId(@PathVariable("idEstado") String idEstado, Model model) {
		try {

			if (!StringUtils.isNullOrEmpty(idEstado))
				return EnderecoService.buscaEstadoPeloId(idEstado);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return new Estado();
	}

	@RequestMapping(value = "estados/{idEstado}/municipios", method = RequestMethod.GET, produces = {
			"application/json" })
	public List<Municipio> getMunicipiosPeloIdEstado(@PathVariable("idEstado") String idEstado, Model model) {
		try {

			if (!StringUtils.isNullOrEmpty(idEstado))
				return EnderecoService.buscaMunicipiosPeloIdEstado(idEstado);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return new ArrayList<Municipio>();
	}

	@RequestMapping(value = "alterar", method = RequestMethod.POST)
	public String alterar(@RequestParam("nome") String nome, @RequestParam("email") String email,
			@RequestParam("telefone") String telefone, @RequestParam("cpf") String cpf, Model model) {

		Cliente novoCliente = new Cliente(nome, email, telefone, cpf);

		repository.save(novoCliente);

		Iterable<Cliente> clientes = repository.findAll();

		model.addAttribute("clientes", clientes);

		return "listaclientes";

	}

}
