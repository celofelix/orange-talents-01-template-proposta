package br.com.zup.proposta.proposta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analiseCliente", url = "${analise.host}")
public interface AnalisePropostaClient {

	@PostMapping
	RespostaAnalise consultaProposta(@RequestBody ConsultaRequest consultaRequest);

	class ConsultaRequest {
		private String documento;
		private String nome;
		private Long idProposta;

		public ConsultaRequest(Proposta proposta) {
			this.documento = proposta.getDocumento();
			this.nome = proposta.getNome();
			this.idProposta = proposta.getId();
		}

		public String getDocumento() {
			return documento;
		}

		public String getNome() {
			return nome;
		}

		public Long getIdProposta() {
			return idProposta;
		}
	}

	class RespostaAnalise {
		private String resultadoSolicitacao;
		private String documento;
		private String nome;
		private Long idProposta;

		public String getResultadoSolicitacao() {
			return resultadoSolicitacao;
		}

		public String getDocumento() {
			return documento;
		}

		public String getNome() {
			return nome;
		}

		public Long getIdProposta() {
			return idProposta;
		}
	}
}