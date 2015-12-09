package com.example.gamemarretada;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RecordListViewActivity extends Activity{
	
	private ArrayList<String> records;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_records_listview);
        iniciarRecords();

        Intent it = getIntent();
        if(it != null && it.hasExtra("pontos")){
			String pontos = it.getStringExtra("pontos");
			salvarRecord(pontos);
		}

        ListView liv = (ListView)findViewById(R.id.livRecords);
        liv.setAdapter(new MeuAdapter());
	}
    class MeuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return records.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            if(v == null){
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.linha_records, null);
            }

            TextView aux = (TextView)v.findViewById(R.id.txtNumero);
            aux.setText(Integer.toString(i));

            aux = (TextView)v.findViewById(R.id.txtRecord);
            aux.setText(records.get(i));

            return v;
        }
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