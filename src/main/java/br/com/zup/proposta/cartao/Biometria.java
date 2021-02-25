package br.com.zup.proposta.cartao;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private byte[] digital;

	@NotNull
	private LocalDateTime dataCriacao;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn
	private Cartao cartao;

	public Biometria() {

	}

	public Biometria(byte[] digital, Cartao cartao) {
		super();
		this.digital = digital;
		this.dataCriacao = LocalDateTime.now();
		this.cartao = cartao;
	}

	public Long getId() {
		return id;
	}

	public byte[] getDigital() {
		return digital;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public Cartao getCartao() {
		return cartao;
	}

}
