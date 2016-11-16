package br.com.webkoder.miniprojeto3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import br.com.webkoder.miniprojeto3.helper.Mask;
import br.com.webkoder.miniprojeto3.model.Funcionario;

public class CadastroActivity extends AppCompatActivity {
    private Funcionario funcionario;
    private EditText edtNome, edtCpf, edtSalario;
    private Spinner spnCargo;
    private TextWatcher cpfMask, salarioMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Elementos
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtCpf = (EditText) findViewById(R.id.edtCpf);
        edtSalario = (EditText) findViewById(R.id.edtSalario);
        spnCargo = (Spinner) findViewById(R.id.spnCargo);
        cpfMask = Mask.insert("###.###.###-##", edtCpf);
        salarioMask = Mask.monetario(edtSalario);
        edtCpf.addTextChangedListener(cpfMask);
        edtSalario.addTextChangedListener(salarioMask);

        Intent intent = this.getIntent();
        if(intent.hasExtra("FUNCIONARIOID")){
            funcionario = Funcionario.findById(Funcionario.class, intent.getLongExtra("FUNCIONARIOID", 0));
            Popular();
        }else{
            funcionario = new Funcionario();
        }
    }

    private void Popular(){
        edtNome.setText( funcionario.getNome() );
        edtCpf.setText( funcionario.getCpf() );
        edtSalario.setText( funcionario.getSalario().toString() );

        String[] cargos = getResources().getStringArray(R.array.cargos);
        int index = 0;
        for(String cargo : cargos){
            if(cargo.equals(funcionario.getCargo())) break;
            index++;
        }
        spnCargo.setSelection(index);
    }

    // Listeners
    public void Salvar(View view){
        String msgerro = "";
        String nome = edtNome.getText().toString();
        String cpf = edtCpf.getText().toString().replaceAll("[.-]", "");
        String salario = edtSalario.getText().toString().replaceAll("[.,-]", "");

        if(nome.equals(""))
            msgerro += "O nome do funcionário está vazio\r\n";

        if(cpf.equals(""))
            msgerro += "O número do CPF está vazio\r\n";
        else if(cpf.length() != 11)
            msgerro += "O número do CPF está incompleto\r\n";

        if(salario.equals(""))
            msgerro += "O valor do salário está vazio\r\n";


        if(msgerro.equals("")) {
            funcionario.setNome(nome);
            funcionario.setCpf(cpf);
            funcionario.setSalario(Double.valueOf(salario));
            funcionario.setCargo( spnCargo.getSelectedItem().toString() );
            funcionario.save();
            Sair(null);
        }else{
            Toast toast = Toast.makeText(this, "Os seguintes erros foram encontrados:\r\n" + msgerro, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void Sair(View view){
        Intent intent = new Intent(this, ListaFuncionario.class);
        startActivity(intent);
    }
}
