package br.com.zup.proposta.cartao;

public enum CarteirasEnum {

	PAYPAL, SAMSUNGPAY;

	static CarteirasEnum toStatus(String carteira) {
		if (carteira.equalsIgnoreCase("paypal")) {
			return PAYPAL;
		}
		return SAMSUNGPAY;
	}

}
