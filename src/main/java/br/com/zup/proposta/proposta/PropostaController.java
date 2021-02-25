package br.com.zup.proposta.proposta;

import java.net.URI;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

	@Autowired
	private PropostaRepository propostaRepositorie;

	@Autowired
	private AnalisePropostaClient analiseProposta;
	
	@Autowired
	private Tracer tracer;

	@PostMapping
	public ResponseEntity<?> criar(@Valid @RequestBody PropostaRequest propostaRequest,
			UriComponentsBuilder uriBuilder) {
		
		Span activeSpan = tracer.activeSpan();
		activeSpan.log("Log criação de propostas");
		activeSpan.setTag("user.email", propostaRequest.getEmail());
		activeSpan.setBaggageItem("user.email", propostaRequest.getEmail());

		if (propostaRequest.ifExist(propostaRepositorie)) {
			HashMap<String, Object> mensagem = new HashMap<>();
			mensagem.put("mensagem", "O Documento informado já possui uma proposta, favor informar outro");
			mensagem.put("campo", "documento");

			return ResponseEntity.unprocessableEntity().body(mensagem);
		}

		Proposta proposta = propostaRequest.toModel(propostaRequest);
		propostaRepositorie.save(proposta);

		try {
			AnalisePropostaClient.ConsultaRequest requisisaoAnalise = new AnalisePropostaClient.ConsultaRequest(
					proposta);
			AnalisePropostaClient.RespostaAnalise respostaAnalise = analiseProposta.consultaProposta(requisisaoAnalise);

			proposta.atualizarStatus(respostaAnalise.getResultadoSolicitacao());
			propostaRepositorie.save(proposta);

		} catch (FeignException.UnprocessableEntity exception) {
			proposta.atualizarStatus("COM_RESTRICAO");
		}

		URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
}
