package br.com.zup.proposta.cartao;

public enum StatusCartao {

	BLOQUEADO, DESBLOQUEADO;

	public static StatusCartao toStatus(String resultado) {
		if (resultado.equals("FALHA")) {
			return DESBLOQUEADO;
		}

		return BLOQUEADO;
	}

}
