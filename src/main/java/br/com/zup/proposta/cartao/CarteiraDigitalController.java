package br.com.zup.proposta.cartao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import feign.FeignException;

@RestController
@RequestMapping("/cartao/{id}")
public class CarteiraDigitalController {

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private CartaoClient cartaoClient;

	@Autowired
	private CarteiraRepository carteiraRepository;

	@PostMapping("/carteira")
	public ResponseEntity<?> adicionaCartaira(@PathVariable Long id,
			@RequestBody CarteiraRequest carteiraRequest) {

		Optional<Cartao> cartao = cartaoRepository.findById(id);
		if (cartao.isPresent()) {
			try {
				CarteiraResponse carteiraResponse = cartaoClient.associaCarteira(cartao.get().getNumero(), carteiraRequest);
				
				CarteiraDigital carteira = carteiraRequest.toModel(cartao.get(), carteiraRequest, carteiraResponse);
				carteiraRepository.save(carteira);
				cartao.get().adicionaCarteira(carteira);
				cartaoRepository.save(cartao.get());
				return ResponseEntity.ok().body(carteira.toString());
			} catch (FeignException.UnprocessableEntity ex) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body("Cartão informado já possui a carteira informada");
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O Cartão informado não foi encontrado");
	}

}
