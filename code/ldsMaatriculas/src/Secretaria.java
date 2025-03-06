import java.io.FileWriter;
import java.io.IOException;

public class Secretaria extends Usuario implements ResponsavelMatricula {

    public Secretaria(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.SECRETARIA); // Define o tipo como SECRETARIA
    }

    public void cancelarMatricula(Disciplina disciplina, Aluno aluno) {
    }

    @Override
    public void cadastrar() {
        super.cadastrar(); // Chama o método cadastrar da classe Usuario para cadastrar no usuarios.csv
        try (FileWriter writer = new FileWriter("LDS-Sistema-de-matriculas\\code\\ldsMaatriculas\\src\\csv\\secretarias.csv",
                true)) {
            writer.append("\n")
                    .append(String.valueOf(id)).append(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gerarCurriculo() {
    }

    public void attInformacoesAluno() {
    }

    public void attInformacoesDisciplina() {
    }

    public void attInformacoesProfessor() {
    }
}