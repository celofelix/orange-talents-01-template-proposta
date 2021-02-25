package br.com.zup.proposta.proposta;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.proposta.validacao.CPFOrCNPJ;

public class PropostaRequest {

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@NotBlank
	// @CPFOrCNPJ(message = "Deve ser informado um CPF ou CNPJ válido")
	private String documento;

	@NotBlank
	@Email
	@Column(nullable = false)
	private String email;

	@NotNull
	@Positive
	@Column(nullable = false)
	private BigDecimal salario;

	@NotNull
	@Column(nullable = false)
	private EnderecoRequest endereco;

	public String getNome() {
		return nome;
	}

	public String getDocumento() {
		return documento;
	}

	public String getEmail() {
		return email;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public EnderecoRequest getEndereco() {
		return endereco;
	}

	public Proposta toModel(@Valid PropostaRequest propostaRequest) {
		Endereco novoEndereco = endereco.toModel(propostaRequest.getEndereco());
		return new Proposta(nome, documento, salario, email, novoEndereco);
	}

	public boolean ifExist(PropostaRepository propostaRepositorie) {
		Optional<Proposta> proposta = propostaRepositorie.findByDocumento(documento);
		if (!proposta.isEmpty()) {
			return true;
		}
		return false;
	}
}
