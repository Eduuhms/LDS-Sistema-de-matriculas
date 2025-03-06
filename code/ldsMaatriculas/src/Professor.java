import java.util.List;

public class Professor extends Usuario {
    private List<Disciplina> disciplinas;


    public Professor(int id, String nome, String email, String senha) {
        super(id, nome, email, senha, TipoUsuario.PROFESSOR); 
    }

    public List<Aluno> visualizarAlunos(Disciplina disciplina) { return null; }
}