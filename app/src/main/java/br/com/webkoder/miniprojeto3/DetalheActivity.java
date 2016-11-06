package br.com.webkoder.miniprojeto3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.webkoder.miniprojeto3.model.Funcionario;

public class DetalheActivity extends AppCompatActivity {

    Funcionario funcionario;
    TextView lblNome, lblCargo, lblCPF, lblSalario, lblBonus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Intent intent = this.getIntent();
        Long id = intent.getLongExtra("FUNCIONARIOID", 0);

        funcionario = Funcionario.findById(Funcionario.class, id);

        //Elementos
        lblNome = (TextView) findViewById(R.id.lblNome);
        lblCargo = (TextView) findViewById(R.id.lblCargo);
        lblCPF = (TextView) findViewById(R.id.lblCPF);
        lblSalario = (TextView) findViewById(R.id.lblSalario);
        lblBonus = (TextView) findViewById(R.id.lblBonus);

        lblNome.setText( funcionario.getNome() );
        lblCargo.setText( funcionario.getCargo() );
        lblCPF.setText( funcionario.getCpf() );
        lblSalario.setText( funcionario.getSalario().toString() );
        lblBonus.setText( Double.toString( funcionario.getBonus(this) ) );
    }

    //Listeners
    public void Editar(View view){
        Intent intent = new Intent(this, CadastroActivity.class);
        intent.putExtra( "FUNCIONARIOID", funcionario.getId() );
        startActivity(intent);
        finish();
    }

    public void Remover(View view){
        // TODO adicionar um dialogBox aqui
        // TODO deixar o botão vermelho
        funcionario.delete();
        Intent intent = new Intent(this, ListaFuncionario.class);
        startActivity(intent);
        finish();
    }
}
