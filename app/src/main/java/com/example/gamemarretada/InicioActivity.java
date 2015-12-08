package com.example.gamemarretada;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by djalma on 08/12/2015.
 */
public class InicioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_layout);
    }

    public void jogar(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void pontos(View v ){
        startActivity(new Intent(this, RecordActivity.class));
    }
}
