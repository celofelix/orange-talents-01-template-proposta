package br.com.zup.proposta.cartao;

public class CarteiraRequest {

	private String email;
	private String carteira;

	public String getEmail() {
		return email;
	}

	public String getCarteira() {
		return carteira;
	}

	public CarteiraDigital toModel(Cartao cartao, CarteiraRequest request, CarteiraResponse carteiraResponse) {
		return new CarteiraDigital(carteiraResponse.getIdCarteira(), request.getCarteira(), request.getEmail(), cartao);
	}

}
