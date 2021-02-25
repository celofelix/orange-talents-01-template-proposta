package br.com.zup.proposta.cartao;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Viagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String destinoViagem;

	@Column(nullable = false)
	private LocalDate dataTerminoViagem;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime dataAvisoViagem;

	private String ip;

	private String userAgente;

	@Enumerated(EnumType.STRING)
	private StatusViagem statusViagem;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn
	private Cartao cartao;
	
	public Viagem() {
		
	}

	public Viagem(Cartao cartao, ViagemRequest viagemRequest, String ip, String userAgente) {
		this.cartao = cartao;
		this.destinoViagem = viagemRequest.getDestinoViagem();
		this.dataTerminoViagem = viagemRequest.getDataTerminoViagem();
		this.dataAvisoViagem = LocalDateTime.now();
		this.ip = ip;
		this.userAgente = userAgente;
		this.statusViagem = StatusViagem.VIAJANDO;
	}

	@Override
	public String toString() {
		return "Viagem [id=" + id + ", destinoViagem=" + destinoViagem + ", dataTerminoViagem=" + dataTerminoViagem
				+ ", dataAvisoViagem=" + dataAvisoViagem + ", ip=" + ip + ", userAgente=" + userAgente
				+ ", statusViagem=" + statusViagem + ", cartao=" + cartao + "]";
	}
}
