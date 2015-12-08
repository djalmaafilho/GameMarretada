package com.example.gamemarretada;

public class BotaoJogo {

	private int botaoId;
	private StatusBotao status; 
	
	public BotaoJogo(int botaoId, StatusBotao status) {
		super();
		this.botaoId = botaoId;
		this.status = status;
	}

	public StatusBotao getStatus() {
		return status;
	}
	
	public void setStatus(StatusBotao status) {
		this.status = status;
	}
	
	public void setBotaoId(int botaoId) {
		this.botaoId = botaoId;
	}
	
	public int getBotaoId() {
		return botaoId;
	}
	
}
