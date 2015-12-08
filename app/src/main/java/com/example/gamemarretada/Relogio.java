package com.example.gamemarretada;

public class Relogio {

	private int minuto, segundo;
	
	public int getSegundo() {
		return segundo;
	}
	
	public void setSegundo(int segundo) {
		this.segundo = segundo;
	}
	
	public int getMinuto() {
		return minuto;
	}
	
	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}
	

	public void incrementarTempo(){
		segundo ++;
		
		if(segundo >= 60){
			minuto++;
			segundo = 0;
		}
		
		if(minuto >= 60 ){
			minuto = 0;
			segundo = 0;
		}
	}

	@Override
	public String toString() {
		String strMinuto = minuto < 10? "0"+minuto : ""+minuto;
		String strSegundo = segundo < 10? "0"+segundo : ""+segundo;
		String strTempo = strMinuto+":"+strSegundo;
		
		return strTempo;
	}	
}
