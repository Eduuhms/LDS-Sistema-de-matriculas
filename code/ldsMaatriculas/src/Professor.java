import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Professor extends Usuario {
    private List<Disciplina> disciplinas;


    public Professor(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.PROFESSOR); 
    }

     @Override
    public void cadastrar() {
        super.cadastrar(); // Chama o método cadastrar da classe Usuario para cadastrar no usuarios.csv
        try (FileWriter writer = new FileWriter("LDS-Sistema-de-matriculas\\code\\ldsMaatriculas\\src\\csv\\professores.csv", true)) {
            writer.append("\n")
                  .append(String.valueOf(id)).append(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> visualizarAlunos(Disciplina disciplina) { return null; }
}