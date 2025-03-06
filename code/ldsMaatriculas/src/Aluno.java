import java.util.List;

public class Aluno extends Usuario implements ResponsavelMatricula {
    private static final int MAX_OBRIGATORIAS = 4;
    private static final int MAX_OPTATIVAS = 2;
    
    private String matricula;
    private Curso curso;
    private List<Disciplina> disciplinasObrigatorias;
    private List<Disciplina> disciplinasOptativas;
    

    public Aluno(int id, String nome, String email, String senha, String matricula, Curso curso) {
        super(id, nome, email, senha, TipoUsuario.ALUNO); 
        this.matricula = matricula;
        this.curso = curso;
    }
    
    public void confirmarMatricula() {}
    public void cancelarMatricula(Disciplina disciplina) {}
    
    @Override
    public void cancelarMatricula(Disciplina disciplina, Aluno aluno){}
    public void matricularDisciplina(Disciplina disciplina) {}

    
}