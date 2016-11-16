package br.com.webkoder.miniprojeto3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
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
        lblCPF.setText( funcionario.getCpfFormatado() );
        lblSalario.setText( funcionario.getSalarioFormatado() );
        lblBonus.setText( funcionario.getBonusFormatado(this) );
    }

    //Listeners
    public void Editar(View view){
        Intent intent = new Intent(this, CadastroActivity.class);
        intent.putExtra( "FUNCIONARIOID", funcionario.getId() );
        startActivity(intent);
        finish();
    }

    public void Sair(View view){
        Intent intent = new Intent(this, ListaFuncionario.class);
        startActivity(intent);
        finish();
    }

    public void Remover(View view){
        new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Deseja remover " + funcionario.getNome() + " ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        funcionario.delete();
                        Sair(null);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
