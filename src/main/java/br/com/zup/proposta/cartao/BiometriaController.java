package br.com.zup.proposta.cartao;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cartao/{id}")
public class BiometriaController {

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private BiometriaRepository biometriaReposity;

	@PostMapping("/biometria")
	public ResponseEntity<?> cadastrar(@PathVariable Long id, @RequestBody BiometricaRequest request,
			UriComponentsBuilder uriBuilder) {

		Optional<Cartao> cartao = cartaoRepository.findById(id);
		if (cartao.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Biometria biometria = request.toModel(cartao.get(), request);
		biometriaReposity.save(biometria);
		cartao.get().adcionaBiometria(biometria);
		cartaoRepository.save(cartao.get());

		URI uri = uriBuilder.path("cartao/{id}/biometria").buildAndExpand(biometria.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}
}
