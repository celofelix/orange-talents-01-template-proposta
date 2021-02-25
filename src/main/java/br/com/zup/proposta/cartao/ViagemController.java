package br.com.zup.proposta.cartao;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.proposta.cartao.CartaoClient.AvisoViagem;
import feign.FeignException;

@RestController
@RequestMapping("/cartao/{id}")
public class ViagemController {

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private ViagemRepository viagemRepository;

	@Autowired
	private CartaoClient cartaoClient;

	@PostMapping("/viagem")
	public ResponseEntity<?> avisarViagem(@PathVariable Long id, HttpServletRequest http,
			@Valid @RequestBody ViagemRequest viagemRequest) {

		Optional<Cartao> cartao = cartaoRepository.findById(id);
		if (cartao.isPresent()) {
			String ip = ipValido(http);
			String userAgente = http.getHeader("User-Agent");
			try {
				String aviso = cartaoClient.avisoViagem(cartao.get().getNumero(), new AvisoViagem(viagemRequest));
				System.out.println(aviso);
				Viagem viagem = viagemRequest.toModel(cartao.get(), viagemRequest, ip, userAgente);
				viagemRepository.save(viagem);
				cartao.get().adicionaViagem(viagem);
				cartaoRepository.save(cartao.get());
				return ResponseEntity.ok().body(viagem.toString());
			} catch (FeignException.UnprocessableEntity ex) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body("Cartão informado já possui aviso de Viagem");
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O Cartão informado não foi encontrado");
	}

	private String ipValido(HttpServletRequest http) {
		String ip = http.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = http.getRemoteAddr();
		}
		return ip;
	}

}
