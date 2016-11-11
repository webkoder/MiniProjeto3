package br.com.webkoder.miniprojeto3.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.text.DecimalFormat;

import br.com.webkoder.miniprojeto3.R;

/**
 * Created by Ricardo on 06/11/2016.
 */
public class Funcionario extends SugarRecord {
    private Long id;
    private String nome;
    private String cargo;
    private String cpf;
    private double salario;

    static double BONUS_PROGRAMADOR = 1.5;
    static double BONUS_DESIGNER = 1.5;
    static double BONUS_GERENTE = 3;
    static double BONUS_ATENDIMENTO = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCpfFormatado() {
        if(cpf == null){
            return "CPF não cadastrado";
        }
        if(cpf.length() != 11){
            return "CPF inválido";
        }
        return cpf.replaceAll("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1.$2.$3-$4");
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public double getBonus(Context context){
        long ncargo = Select.from(Funcionario.class)
                .where( Condition.prop("cargo").eq(this.cargo) )
                .count();

        SharedPreferences config = context.getSharedPreferences("LUCRO", context.MODE_PRIVATE);
        double lucro = (double) config.getInt("VALOR", 0) / 100;
        double porcentagem = 0;
        if(this.cargo.equals("Programador")) porcentagem = BONUS_PROGRAMADOR;
        else if(this.cargo.equals("Designer")) porcentagem = BONUS_DESIGNER;
        else if(this.cargo.equals("Gerente")) porcentagem = BONUS_GERENTE;
        else if(this.cargo.equals("Atendimento")) porcentagem = BONUS_ATENDIMENTO;

        double bonus = lucro * (porcentagem / 100) / ncargo;
        return bonus;
    }
}
