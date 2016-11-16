package br.com.webkoder.miniprojeto3;

import org.junit.Test;

import br.com.webkoder.miniprojeto3.model.Funcionario;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void cpfFormatTeste() throws Exception {
        Funcionario funcionario = new Funcionario();
        // CPF OK
        String cpf = "33802255844";
        funcionario.setCpf(cpf);
        String formatado = funcionario.getCpfFormatado();

        assertEquals("338.022.558-44", formatado);

        // CPF INVALIDO
        cpf = "3380225584";
        funcionario.setCpf(cpf);
        formatado = funcionario.getCpfFormatado();

        assertEquals("CPF inválido", formatado);

        // CPF vazio
        funcionario.setCpf(null);
        formatado = funcionario.getCpfFormatado();

        assertEquals("CPF não cadastrado", formatado);
    }

    @Test
    public void SalarioFormatTeste() throws Exception {
        Funcionario funcionario = new Funcionario();
        // valor nulo
        String formatado = funcionario.getSalarioFormatado();

        assertEquals("R$ 0", formatado);

        // número grande
        double salario = 54321849.08;
        funcionario.setSalario(salario);
        formatado = funcionario.getSalarioFormatado();

        assertEquals("R$ 54.321.849,08", formatado);

        // número pequeno
        salario = 849.08;
        funcionario.setSalario(salario);
        formatado = funcionario.getSalarioFormatado();

        assertEquals("R$ 849,08", formatado);

        // mais casas decimais
        salario = 849.0891;
        funcionario.setSalario(salario);
        formatado = funcionario.getSalarioFormatado();

        assertEquals("R$ 849,09", formatado);
    }
}