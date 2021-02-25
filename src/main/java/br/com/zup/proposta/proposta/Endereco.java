package br.com.zup.proposta.proposta;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Endereco {

	private String logradouro;

	private String numero;

	private String complemento;

	private String cep;

	private String municipio;

	private String estado;
	
	public Endereco() {
		
	}
	
	public Endereco(@NotBlank String logradouro, @NotBlank String numero, String complemento, @NotBlank String cep, @NotBlank String municipio, @NotBlank String estado) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
		this.municipio = municipio;
		this.estado = estado;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
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
}
