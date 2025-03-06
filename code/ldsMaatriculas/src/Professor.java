import java.util.List;

public class Professor extends Usuario {
    private List<Disciplina> disciplinas;


    public Professor(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.PROFESSOR); 
    }

    public List<Aluno> visualizarAlunos(Disciplina disciplina) { return null; }
}