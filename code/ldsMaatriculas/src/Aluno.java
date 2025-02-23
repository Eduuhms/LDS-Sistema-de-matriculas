import java.util.List;

public class Aluno extends ResponsavelMatricula {
    private String matricula;
    private Curso curso;
    private List<Disciplina> disciplinasObrigatorias;
    private List<Disciplina> disciplinasOptativas;
    private int maxObrigatorias = 4;
    private int maxOptativas = 2;

    public void confirmarMatricula() {}
    public void cancelarMatricula(Disciplina disciplina) {}
    public void matricularDisciplina(Disciplina disciplina) {}
}