package br.com.webkoder.miniprojeto3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.webkoder.miniprojeto3.R;
import br.com.webkoder.miniprojeto3.model.Funcionario;

/**
 * Created by Ricardo on 06/11/2016.
 */
public class FuncionarioAdapter extends ArrayAdapter<Funcionario> {

    List<Funcionario> funcionarios;
    Context context;
    Holder holder;

    public FuncionarioAdapter(Context context, int resource, List<Funcionario> objects) {
        super(context, resource, objects);

        this.context = context;
        this.funcionarios = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.funcionario_item, null);
            holder = new Holder();
            holder.nome = (TextView) convertView.findViewById(R.id.txtItemNome);
            holder.cpf = (TextView) convertView.findViewById(R.id.txtItemCpf);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        Funcionario f = funcionarios.get(position);
        if(f != null){
            holder.nome.setText( f.getNome() );
            holder.cpf.setText( f.getCpfFormatado() );
        }

        return convertView;
    }

    private class Holder{
        TextView nome;
        TextView cpf;
    }
}
