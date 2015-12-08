package com.example.gamemarretada;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class JogoControle {

	Set<Integer> idsAleatorios = new HashSet<Integer>();
	private int contadorRodadas;
	private long pontos;
	private int vidas = 4;
	
	public static final int TEMPO_RODADA = 2000;
	public static final int MAX_RODADAS = 30;
	
	private List<BotaoJogo> botoes;
	
	public JogoControle(ArrayList<BotaoJogo> botoes) {
		this.botoes = botoes;
	}
	
	public void confirmarClickBotao(int botaoId){
		for(BotaoJogo bt: botoes){
			if(bt.getBotaoId() == botaoId ){
				bt.setStatus(StatusBotao.SUCESSO);
				pontos += 100;
				break;
			}
		}
	}
	
	public void concluirRodada(){
		boolean teveFalha = false;
		for(BotaoJogo bt: botoes){
			Iterator<Integer> it = idsAleatorios.iterator();
			while(it.hasNext()){
				Integer id = it.next();
				if(id.equals(botoes.indexOf(bt)) && !bt.getStatus().equals(StatusBotao.SUCESSO)){
					bt.setStatus(StatusBotao.FALHA);
					teveFalha = true;
				}
			}
		}
		
		if(teveFalha && vidas > 0){
			vidas --;
		}
		
		idsAleatorios.clear();
	}
	
	public int getVidas(){
		return vidas;
	}
	
	public void gerarNovaRodada(){
		contadorRodadas++;
		while(idsAleatorios.size() < getQtdBotoesRodada()){
			Random random = new Random();  
			Integer idAleatorio = random.nextInt(botoes.size());
			idsAleatorios.add(idAleatorio);
		}
		
		for(BotaoJogo bt: botoes){
			boolean aguardando = false;
			for(Integer i : idsAleatorios){
				if(i.equals(botoes.indexOf(bt))){
					bt.setStatus(StatusBotao.AGUARDANDO);
					aguardando = true;
					break;
				}
			}
			if(!aguardando){
				bt.setStatus(StatusBotao.NAO_INICIADO);
			}
		}
	}
	
	public int getQtdBotoesRodada(){
		
		int qtd = 0;
		
		if(contadorRodadas <= 10){
			qtd = 3;
		}else if(contadorRodadas <= 20){
			qtd = 4;
		}else{
			qtd = 5;
		}
		
		return qtd;
	}
	
	public List<BotaoJogo> getBotoes() {
		return botoes;
	}
	
	public String getPontos(){
		 
		return Long.toString(pontos);
	}
	
	public boolean isJogoConcluido(){
		boolean concluido = false;
		if(vidas == 0 || contadorRodadas > MAX_RODADAS){
			concluido = true;
		}
		
		return concluido;
	}
	
}