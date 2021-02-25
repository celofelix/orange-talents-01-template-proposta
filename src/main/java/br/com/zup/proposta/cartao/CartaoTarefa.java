package br.com.zup.proposta.cartao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zup.proposta.cartao.CartaoClient.CartaoResponse;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
import feign.FeignException;

@Component
public class CartaoTarefa {

	private static final Logger logger = LoggerFactory.getLogger(CartaoTarefa.class);

	@Autowired
	private PropostaRepository propostaRepository;

	@Autowired
	private CartaoClient cartaoCliente;

	@Autowired
	private CartaoRepository cartaoRepository;

	@Scheduled(fixedDelayString = "${periodicidade.minha-tarefa}")
	private void consultaCartoesElegiveis() {
		List<Proposta> propostas = propostaRepository.findPropostasElegiveisSemCartaoOrderByIdAsc();
		for (Proposta proposta : propostas) {
			try {
				CartaoResponse cartaoResponse = cartaoCliente.consultaCartao(proposta.getId());
				Cartao novoCartao = cartaoResponse.toModel(propostaRepository);
				proposta.adicionaCartao(novoCartao);
				cartaoRepository.save(novoCartao);
				
			} catch (FeignException exception) {
				logger.info("Não encontrou nenhum cartão para a proposta " + proposta.getId());
			}
		}

	}

}
