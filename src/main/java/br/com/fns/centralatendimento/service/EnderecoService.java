package br.com.fns.centralatendimento.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.com.fns.centralatendimento.model.endereco.Endereco;
import br.com.fns.centralatendimento.model.endereco.Estado;
import br.com.fns.centralatendimento.model.endereco.Municipio;
import br.com.fns.centralatendimento.utils.Utils;

public class EnderecoService {

	static String webServiceCep = "http://viacep.com.br/ws/";
	static String webSericeEstado = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";

	static int codigoSucesso = 200;

	private static <T> T objetoJson(String urlService, Class<T> classe) throws Exception {
		try {

			Gson gson = new Gson();
			String result = jsonEmString(urlService);

			return gson.fromJson(result, classe);

		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}

	private static String jsonEmString(String urlService) throws Exception {
		try {
			URL url = new URL(urlService);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

			if (conexao.getResponseCode() != codigoSucesso)
				throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

			BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));

			return Utils.converteJsonEmString(resposta);

		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}

	public static Endereco buscaEnderecoPeloCep(String cep) throws Exception {
		return objetoJson(webServiceCep + cep + "/json", Endereco.class);
	}

	public static Estado buscaEstadoPeloId(String id) throws Exception {
		return objetoJson(webSericeEstado + "/" + id, Estado.class);
	}

	public static List<Municipio> buscaMunicipiosPeloIdEstado(String idEstado) throws Exception {
		String json = jsonEmString(webSericeEstado + idEstado + "/municipios");

		ObjectMapper mapper = new ObjectMapper();
		List<Municipio> municipios = Arrays.asList(mapper.readValue(json, Municipio[].class));

		return municipios;
	}

	public static List<Estado> buscaEstados() throws Exception {
		String json = jsonEmString(webSericeEstado);

		ObjectMapper mapper = new ObjectMapper();
		List<Estado> estados = Arrays.asList(mapper.readValue(json, Estado[].class));

		return estados;
	}

}
