package br.com.zup.proposta.proposta;

import com.sun.istack.NotNull;

import br.com.zup.proposta.validacao.ExistID;

public class PropostaResponse {

	@NotNull 
	@ExistID(domainClass = Proposta.class, fieldName = "id", 
	message = "O ID da proposta informado não existe, favor informar outro")
	private Long id;

	private String titular;

	private StatusProposta status;

	public PropostaResponse(Proposta proposta) {
		this.id = proposta.getId();
		this.titular = proposta.getNome();
		this.status = proposta.getStatus();
	}

	public Long getId() {
		return id;
	}

	public String getTitular() {
		return titular;
	}

	public StatusProposta getStatus() {
		return status;
	}
}
