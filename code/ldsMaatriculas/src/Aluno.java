import java.util.List;

public class Aluno extends ResponsavelMatricula {
    private static final int MAX_OBRIGATORIAS = 4;
    private static final int MAX_OPTATIVAS = 2;
    
    private String matricula;
    private Curso curso;
    private List<Disciplina> disciplinasObrigatorias;
    private List<Disciplina> disciplinasOptativas;
    

    public void confirmarMatricula() {}
    public void cancelarMatricula(Disciplina disciplina) {}
    public void matricularDisciplina(Disciplina disciplina) {}
}