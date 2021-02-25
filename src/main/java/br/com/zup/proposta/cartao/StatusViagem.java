package br.com.zup.proposta.cartao;

public enum StatusViagem {

	VIAJANDO;

	public static StatusViagem toStatus(String aviso) {
		if (aviso.equals("CRIADO")) {
			return VIAJANDO;
		}
		return null;
	}
}
