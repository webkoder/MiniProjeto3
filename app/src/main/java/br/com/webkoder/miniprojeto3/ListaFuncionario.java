package br.com.webkoder.miniprojeto3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.orm.SugarContext;

import java.util.List;

import br.com.webkoder.miniprojeto3.adapter.FuncionarioAdapter;
import br.com.webkoder.miniprojeto3.helper.Mask;
import br.com.webkoder.miniprojeto3.model.Funcionario;

public class ListaFuncionario extends AppCompatActivity {

//    https://github.com/BugginhoDeveloper/mini-projeto-3-java-android
//    x Cadastrar funcionários (apenas o nome, cpf, cargo e salário);
//    x Editar funcionários;
//    x Remover funcionários;
//    x Ver um funcionário (Todos os dados, inclusive o Bônus);
//    x Consultar um funcionário pelo cpf;
//    x Listar todos os funcionários (nome e cpf);
//    x Cadastrar / Editar o lucro anual da empresa
//    OBS:
//    Todos os dados deverão ser armazenados em um banco de dados SQLite.
//    Apenas serão permitidos cadastros de funcionários: "Programador", "Designer", "Gerente" e "Atendimento"
//    Crie uma tela separada onde seja possível cadastrar e editar o lucro anual da empresa
//    Bônus: Programador (1.5%), Designer (1.5%), Gerente (3%) e Atendimento (1%)
//    Para calcular o Bônus, divida o percentual acima pelo total de funcionários por cargo. Ex.: Se existirem no banco de dados 3 programadores, cada um ganhará um Bônus de 0.5% (1.5 / 3) do lucro anual da empresa

    List<Funcionario> funcionarios;
    ListView lstFuncionarios;
    EditText edtPesquisa;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SugarContext.terminate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_funcionario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SugarContext.init(this);

        funcionarios = Funcionario.listAll(Funcionario.class);
        lstFuncionarios = (ListView) findViewById(R.id.lstFuncionario);
        lstFuncionarios.setAdapter(new FuncionarioAdapter(this, 0, funcionarios));
        lstFuncionarios.setOnItemClickListener(new lstFuncionariosOnItemListener());
        edtPesquisa = (EditText) findViewById(R.id.edtPesquisar);
        TextWatcher maskPesquisa = Mask.insert("###.###.###-##", edtPesquisa);
        edtPesquisa.addTextChangedListener(maskPesquisa);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_funcionario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_lucro) {
            Intent intent = new Intent(this, LucroActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Listeners
    private class lstFuncionariosOnItemListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), DetalheActivity.class);
            Funcionario f = (Funcionario) lstFuncionarios.getItemAtPosition(position);
            intent.putExtra("FUNCIONARIOID", f.getId() );
            startActivity(intent);
        }
    }

    public void Pesquisar(View view){
        String key = edtPesquisa.getText().toString();
        if(key.length() != 11){
            Toast.makeText(this, "O CPF digitado é inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        for(Funcionario f : funcionarios){
            if(f.getCpf().equals(key)){
                Intent intent = new Intent(this, DetalheActivity.class);
                intent.putExtra("FUNCIONARIOID", f.getId());
                startActivity(intent);
                return;
            }
        }
        Toast.makeText(this, "Não foi encontrado nenhum funcionário com esse CPF", Toast.LENGTH_SHORT).show();
    }
}
