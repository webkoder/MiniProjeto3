package br.com.webkoder.miniprojeto3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import br.com.webkoder.miniprojeto3.helper.Mask;

public class LucroActivity extends AppCompatActivity {
    EditText edtLucro;
    SharedPreferences config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucro);

        edtLucro = (EditText) findViewById(R.id.edtLucro);
        config = getApplicationContext().getSharedPreferences("LUCRO", getApplicationContext().MODE_PRIVATE);
        double lucro = (double) config.getInt("VALOR", 0) / 100;
        edtLucro.setText( Double.toString(lucro) );
        TextWatcher lucroMask = Mask.monetario(edtLucro);
        edtLucro.addTextChangedListener(lucroMask);
    }

    public void Salvar(View view){
        int lucro = Integer.valueOf( edtLucro.getText().toString().replace(".", "") );
        SharedPreferences.Editor editor = config.edit();
        editor.putInt( "VALOR",  lucro  );
        editor.commit();
        Sair(null);
    }

    public void Sair(View view){
        Intent intent = new Intent(this, ListaFuncionario.class);
        startActivity(intent);
        finish();
    }
}
