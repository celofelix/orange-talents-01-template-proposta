package br.com.zup.proposta.proposta;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.ColumnTransformer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.zup.proposta.cartao.Cartao;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nome;

	@NotBlank
	@Column(unique = true)
	@ColumnTransformer(read = "AES_DECRYPT(UNHEX(documento), 'my_secret_key')", write = "HEX(AES_ENCRYPT(?, 'my_secret_key'))")
	private String documento;

	@NotNull
	@Positive
	private BigDecimal salario;

	private String email;

	@Embedded
	private Endereco endereco;

	@Enumerated(EnumType.STRING)
	private StatusProposta status;

	@JsonIgnore
	@OneToOne(mappedBy = "proposta", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Cartao cartao;

	public Proposta() {

	}

	public Proposta(@NotBlank String nome, @NotBlank String documento, @NotNull @Positive BigDecimal salario,
			@NotBlank @Email String email, Endereco endereco) {
		this.nome = nome;
		this.documento = documento;
		this.salario = salario;
		this.endereco = endereco;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public String getDocumento() {
		return documento;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public StatusProposta getStatus() {
		return status;
	}

	public void adicionaCartao(Cartao novoCartao) {
		this.cartao = new Cartao(novoCartao.getNumero(), novoCartao.getTitular(), novoCartao.getDataEmicao(),
				novoCartao.getLimite(), this);
	}

	public void atualizarStatus(String resultadoSolicitacao) {
		this.status = StatusProposta.toStatus(resultadoSolicitacao);
	}
}
