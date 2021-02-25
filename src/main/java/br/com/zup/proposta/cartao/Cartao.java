package br.com.zup.proposta.cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.zup.proposta.proposta.Proposta;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String numero;

	@Column(nullable = false)
	private String titular;

	@Column(nullable = false)
	private LocalDateTime dataEmicao;

	@Column(nullable = false)
	private BigDecimal limite;

	@OneToOne
	private Proposta proposta;

	@JsonIgnore
	@OneToMany(mappedBy = "cartao", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Biometria> biometria = new ArrayList<>();

	@OneToOne(mappedBy = "cartao", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Bloqueio bloqueio;

	@Enumerated(EnumType.STRING)
	private StatusCartao statusCartao;

	@JsonIgnore
	@OneToMany(mappedBy = "cartao", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Viagem> viagens = new ArrayList<>();

	@OneToMany(mappedBy = "cartao", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<CarteiraDigital> carteirasDigitais = new ArrayList<>();

	public Cartao() {

	}

	public Cartao(String numero, String titular, LocalDateTime dataEmicao, BigDecimal limite, Proposta proposta) {
		this.numero = numero;
		this.titular = titular;
		this.dataEmicao = dataEmicao;
		this.limite = limite;
		this.proposta = proposta;
	}

	public Long getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public String getTitular() {
		return titular;
	}

	public LocalDateTime getDataEmicao() {
		return dataEmicao;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public Proposta getProposta() {
		return proposta;
	}

	public void adcionaBiometria(Biometria biometria) {
		this.biometria.add(biometria);
	}

	public void adicionaBloqueio(Bloqueio novoBloqueio) {
		this.bloqueio = novoBloqueio;
	}

	public List<Biometria> getBiometria() {
		return biometria;
	}

	public Bloqueio getBloqueio() {
		return bloqueio;
	}

	public StatusCartao getStatusCartao() {
		return statusCartao;
	}

	public void atualizaStatus(String statusDeBloqueio) {
		this.statusCartao = StatusCartao.toStatus(statusDeBloqueio);
	}

	public void adicionaViagem(Viagem viagem) {
		this.viagens.addAll(viagens);
	}

	public void adicionaCarteira(CarteiraDigital carteira) {
		this.carteirasDigitais.addAll(carteirasDigitais);
	}

}
