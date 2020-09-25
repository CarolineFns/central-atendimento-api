package br.com.fns.centralatendimento.model.endereco;

public class Estado implements Comparable<Estado> {
	private Long id;
	private String sigla;
	private String nome;
	private Regiao regiao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int compareTo(Long anotherLong) {
		return id.compareTo(anotherLong);
	}

	public String toString() {
		return id.toString();
	}

	public int compareTo(String anotherString) {
		return nome.compareTo(anotherString);
	}

	@Override
	public int compareTo(Estado u) {
		if (getNome() == null || u.getNome() == null) {
			return 0;
		}
		return getNome().compareTo(u.getNome());
	}

}
