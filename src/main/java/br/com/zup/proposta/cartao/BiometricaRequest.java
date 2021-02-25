package br.com.zup.proposta.cartao;

import java.util.Base64;

import javax.validation.constraints.NotBlank;

public class BiometricaRequest {

	@NotBlank
	private String biometria;

	public String getBiometria() {
		return biometria;
	}

	public Biometria toModel(Cartao cartao, BiometricaRequest request) {
		byte[] digital = Base64.getDecoder().decode(request.getBiometria().getBytes());
		return new Biometria(digital, cartao);
	}
}
