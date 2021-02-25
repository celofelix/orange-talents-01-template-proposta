package br.com.zup.proposta.cartao;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

public class ViagemRequest {
	
	@NotBlank
	private String destinoViagem;
	
	@Future
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull
	private LocalDate validoAte;

	private String ip;

	private String userAgente;

	public String getDestinoViagem() {
		return destinoViagem;
	}

	public LocalDate getDataTerminoViagem() {
		return validoAte;
	}

	public String getIp() {
		return ip;
	}

	public String getUserAgente() {
		return userAgente;
	}

	public Viagem toModel(Cartao cartao, ViagemRequest viagemRequest, String ip, String userAgente) {		
		return new Viagem(cartao,viagemRequest, ip, userAgente);
	}

}
