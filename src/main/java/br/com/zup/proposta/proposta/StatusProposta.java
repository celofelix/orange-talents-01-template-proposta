package br.com.zup.proposta.proposta;

public enum StatusProposta {
	
	NAO_ELEGIVEL,
	ELEGIVEL;

	public static StatusProposta toStatus(String resultadoSolicatacao) {
		if(resultadoSolicatacao.equals("COM_RESTRICAO")) {
			return NAO_ELEGIVEL;
		}
		return ELEGIVEL;
	}
}
