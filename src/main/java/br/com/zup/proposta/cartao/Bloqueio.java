package br.com.zup.proposta.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Bloqueio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ip;

	private String userAgente;

	private LocalDateTime dataBloqueio;

	@OneToOne
	private Cartao cartao;

	public Bloqueio() {

	}

	public Bloqueio(String ip, String userAgente, Cartao cartao) {
		this.ip = ip;
		this.userAgente = userAgente;
		this.cartao = cartao;
		this.dataBloqueio = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getUserAgente() {
		return userAgente;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public LocalDateTime getDataBloqueio() {
		return dataBloqueio;
	}
}
