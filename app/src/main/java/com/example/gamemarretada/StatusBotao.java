package com.example.gamemarretada;

public enum StatusBotao {
	
	SUCESSO(R.color.verde), NAO_INICIADO(R.color.cinza), FALHA(R.color.vermelho), AGUARDANDO(R.color.amarelo);
	
	private int cor;
	
	private StatusBotao(int cor) {
		this.cor = cor;
	}
	
	public int getCor() {
		return cor;
	}
}
