package br.com.fns.centralatendimento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.util.StringUtils;

import br.com.fns.centralatendimento.model.endereco.Endereco;
import br.com.fns.centralatendimento.model.endereco.Estado;
import br.com.fns.centralatendimento.model.endereco.Municipio;
import br.com.fns.centralatendimento.service.EnderecoService;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {

	@RequestMapping(value = "{cep}", method = RequestMethod.GET, produces = { "application/json" })
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
}
