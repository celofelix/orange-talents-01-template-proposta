package br.com.zup.proposta.cartao;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.proposta.cartao.CartaoClient.BloqueioRequest;
import feign.FeignException;

@RestController
@RequestMapping("/cartao/{id}")
public class BloqueioCartaoController {

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private BloqueioRepository bloqueioRepository;

	@Autowired
	private CartaoClient cartaoAnalise;

	@PostMapping("/bloqueio")
	public ResponseEntity<?> bloqueiaCartao(@PathVariable(required = true) Long id, HttpServletRequest request) {

		Optional<Cartao> cartao = cartaoRepository.findById(id);
		if (cartao.isPresent()) {
			Optional<Bloqueio> possivelBloqueio = bloqueioRepository.findById(cartao.get().getId());
			if (possivelBloqueio.isPresent()) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body("O Cartão informado já está bloqueado");
			}

			String ip = ipValido(request);
			String userAgente = request.getHeader("User-Agent");
			Bloqueio novoBloqueio = new Bloqueio(ip, userAgente, cartao.get());

			bloqueioRepository.save(novoBloqueio);

			try {
				String statusDeBloqueio = cartaoAnalise.bloqueiaCartao(cartao.get().getNumero(),
						new BloqueioRequest("Sistema"));

				cartao.get().adicionaBloqueio(novoBloqueio);
				cartao.get().atualizaStatus(statusDeBloqueio);
				cartaoRepository.save(cartao.get());
				return ResponseEntity.ok().body(cartao.get().getStatusCartao());
			} catch (FeignException.UnprocessableEntity e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Não foi possível bloquear o cartão" + cartao.get().getStatusCartao());
			}
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão informado não existe");
	}

	private String ipValido(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
