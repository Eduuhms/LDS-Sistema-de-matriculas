import java.util.List;

public class Disciplina {
    private String nome;
    private String codigo;
    private int creditos;
    private boolean ehObrigatoria;
    private List<Aluno> alunosMatriculados;
    private int maxAlunos = 60;
    private int minAlunos = 3;
    private String status;

    public String statusDisciplina() { return ""; }
    public void gerarCurriculo() {}
    public void fecharMatriculas() {}
    public void addAluno(Aluno aluno) {}
    public void removerAluno(Aluno aluno) {}
    public List<Aluno> alunosMatriculados() { return null; }
    public void cancelarDisciplina() {}
}