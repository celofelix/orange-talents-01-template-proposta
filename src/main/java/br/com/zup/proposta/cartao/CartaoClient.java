package br.com.zup.proposta.cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;

@FeignClient(name = "analiseCartao", url = "${cartoes.host}")
public interface CartaoClient {

	@GetMapping
	CartaoResponse consultaCartao(@RequestParam("idProposta") Long id);

	@PostMapping(value = "/{id}/bloqueios")
	String bloqueiaCartao(@PathVariable String id, BloqueioRequest request);

	@PostMapping(value = "/{id}/avisos")
	String avisoViagem(@PathVariable String id, AvisoViagem viagemRequest);

	@PostMapping(value = "/{id}/carteiras")
	CarteiraResponse associaCarteira(@PathVariable String id, CarteiraRequest carteiraRequest);
	
	class CartaoResponse {

		private String id;
		private LocalDateTime emitidoEm;
		private String titular;
		private BigDecimal limite;
		private Long idProposta;

		public String getId() {
			return id;
		}

		public LocalDateTime getEmitidoEm() {
			return emitidoEm;
		}

		public String getTitular() {
			return titular;
		}

		public BigDecimal getLimite() {
			return limite;
		}

		public Long getIdProposta() {
			return idProposta;
		}

		public Cartao toModel(PropostaRepository propostaRepository) {
			Proposta proposta = propostaRepository.findById(idProposta).get();
			return new Cartao(id, titular, emitidoEm, limite, proposta);
		}
	}

	class BloqueioRequest {

		String sistemaResponsavel;

		public BloqueioRequest(String sistemaResponsavel) {
			this.sistemaResponsavel = sistemaResponsavel;
		}

		public String getSistemaResponsavel() {
			return sistemaResponsavel;
		}
	}

	class AvisoViagem {

		private String destino;

		@JsonFormat(pattern = "dd/MM/yyyy")
		@DateTimeFormat(pattern = "dd/MM/yyyy")
		private String validoAte;

		public AvisoViagem(@Valid ViagemRequest viagemRequest) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dataViagem = viagemRequest.getDataTerminoViagem().format(formatter);
			this.destino = viagemRequest.getDestinoViagem();
			this.validoAte = dataViagem;
		}

		public String getDestino() {
			return destino;
		}

		public String getValidoAte() {
			return validoAte;
		}
	}
}
