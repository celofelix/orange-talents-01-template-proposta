package br.com.zup.proposta.proposta;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/propostas")
public class ConsultaPropostaController {

	@Autowired
	private PropostaRepository propostaRepository;

	@GetMapping("/{id}")
	public ResponseEntity<PropostaResponse> detalhar(@PathVariable Long id) {
		Optional<Proposta> proposta = propostaRepository.findById(id);
		if (proposta.isPresent()) {
			return ResponseEntity.ok().body(new PropostaResponse(proposta.get()));
		}
		return ResponseEntity.notFound().build();
	}
}
