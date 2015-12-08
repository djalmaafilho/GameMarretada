package com.example.gamemarretada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecordActivity extends Activity{
	
	private ArrayList<String> records;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_records);
        iniciarRecords();

        Intent it = getIntent();
        if(it != null && it.hasExtra("pontos")){
			String pontos = it.getStringExtra("pontos");
			salvarRecord(pontos);
		}

        exibirRecords();
	}
	
	private void exibirRecords() {
		int linha = 1;
		LinearLayout lil = (LinearLayout)findViewById(R.id.lilRecords);
		for(String ponto: records){
			View v = recuperarViewAPartirDeLayout(R.layout.linha_records);
			
			TextView aux = (TextView)v.findViewById(R.id.txtNumero);
			aux.setText(Integer.toString(linha++));
			
			aux = (TextView)v.findViewById(R.id.txtRecord);
			aux.setText(ponto);
			
			lil.addView(v);
		}
	}
	
	public View recuperarViewAPartirDeLayout(int resId) {
		LayoutInflater inflater = (LayoutInflater) 
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(resId, null);
	}

	private void iniciarRecords(){
		File pasta = getFilesDir();
		File recordsFile = new File(pasta, "records");
		FileInputStream fis;
		try {
			fis = new FileInputStream(recordsFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			records = (ArrayList<String>)ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(records == null){
			records = new ArrayList<String>();
		}
	}
	
	private void salvarRecord(String record){
		File pasta = getFilesDir();
		File recordsFile = new File(pasta, "records");
		try {
			FileOutputStream fos = new FileOutputStream(recordsFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			records.add(record);
			oos.writeObject(records);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}