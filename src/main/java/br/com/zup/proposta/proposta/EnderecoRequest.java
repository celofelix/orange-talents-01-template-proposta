package br.com.zup.proposta.proposta;

import javax.validation.constraints.NotBlank;

public class EnderecoRequest {

	@NotBlank
	private String logradouro;

	@NotBlank
	private String numero;

	private String complemento;

	@NotBlank
	private String cep;

	@NotBlank
	private String municipio;

	@NotBlank
	private String estado;

	public String getLogradouro() {
		return logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public String getCep() {
		return cep;
	}

	public String getMunicipio() {
		return municipio;
	}

	public String getEstado() {
		return estado;
	}

	public String getComplemento() {
		return complemento;
	}

	public Endereco toModel(EnderecoRequest enderecoRequest) {
		return new Endereco(logradouro, numero, complemento, cep, municipio, estado);
	}
}
