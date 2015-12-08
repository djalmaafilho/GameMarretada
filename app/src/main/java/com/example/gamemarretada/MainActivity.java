package com.example.gamemarretada;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	private JogoControle controle;
	private Relogio relogio; 
	private Handler handler;
	private TextView txtTempo, txtPontos;
	private int [] vidas = new int[] {R.id.btVida1,R.id.btVida2, R.id.btVida3, R.id.btVida4};
	private int[] botoesArray = new int[]{R.id.button1, R.id.button2,
			R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtPontos = (TextView)findViewById(R.id.textViewPontos);
		txtTempo = (TextView)findViewById(R.id.textViewTempo);
		relogio = new Relogio();
		handler = new Handler();
		ArrayList<BotaoJogo> botoes = new ArrayList<BotaoJogo>();

		for(int i = 0; i < botoesArray.length; i++){
			View v = findViewById(botoesArray[i]);
			v.setOnClickListener(this);
			botoes.add(new BotaoJogo(v.getId(),StatusBotao.NAO_INICIADO));
		}
		controle = new JogoControle(botoes);
	}

	@Override
	public void onClick(View v) {
		controle.confirmarClickBotao(v.getId());
		v.setEnabled(false);
	}
	
	private void desativarBotoes(){
		for(int idView : botoesArray){
			findViewById(idView).setEnabled(false);
		}
	}
	
	private void ativarBotoes(){
		for(int idView : botoesArray){
			findViewById(idView).setEnabled(true);
		}
	}
	
	private void atualizarPontos(String pontos){
		txtPontos.setText(pontos);
	}
	
	private void atualizarVidas(int qtdVidas){
		for(int i = 0; i < vidas.length; i++){
			if(i < qtdVidas ){
				findViewById(vidas[i]).setBackgroundResource(R.color.verde);
			}else{
				findViewById(vidas[i]).setBackgroundResource(R.color.vermelho);
			}
		}
	}
	
	@Override
	protected void onStop() {
		pararJogo();
		super.onStop();
	}
	
	private void pararJogo(){
		handler.removeCallbacks(contadorTempo);
		handler.removeCallbacks(atualizarJogo);
	}
	
	private void concluirJogo(String pontos){
		Intent it = new Intent(this, RecordActivity.class);
		it.putExtra("pontos", pontos);
		startActivity(it);
        finish();
	}
	
	@Override
	protected void onStart() {
		handler.post(contadorTempo);
		handler.postDelayed(atualizarJogo, 1000);
		super.onStart();
	}
	
	private Runnable contadorTempo = new Runnable() {
		@Override
		public void run() {
			txtTempo.setText(relogio.toString());
			relogio.incrementarTempo();
			handler.postDelayed(this, 1000);
		}
	};
	
	private Runnable atualizarJogo = new Runnable() {
		boolean novaRodada = true;
		@Override
		public void run() {
			if(novaRodada){
				novaRodada = false;
				novaRodada();
			}else{
				concluirRodada();
				novaRodada = true;
			}
			if(controle.isJogoConcluido()){
				pararJogo();
				Toast.makeText(getBaseContext(), "Jogo Concluido", Toast.LENGTH_LONG).show();
				concluirJogo(controle.getPontos());
			}else{
				handler.postDelayed(this, JogoControle.TEMPO_RODADA);
			}
		}
		
		private void concluirRodada(){
			desativarBotoes();
			controle.concluirRodada();
			for(BotaoJogo bt : controle.getBotoes()){
				View v = findViewById(bt.getBotaoId());
				v.setBackgroundResource(bt.getStatus().getCor());
			}
			atualizarVidas(controle.getVidas());
			atualizarPontos(controle.getPontos());
			
		}
		
		private void novaRodada(){
			controle.gerarNovaRodada();
			for(BotaoJogo bt : controle.getBotoes()){
				View v = findViewById(bt.getBotaoId());
				v.setBackgroundResource(bt.getStatus().getCor());
			}
			ativarBotoes();
		}
	};
}