public class Secretaria extends Usuario implements ResponsavelMatricula {

    public Secretaria(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.SECRETARIA); // Define o tipo como SECRETARIA
    }

    public void cancelarMatricula(Disciplina disciplina, Aluno aluno) {}
    
    public void gerarCurriculo() {}
    public void attInformacoesAluno() {}
    public void attInformacoesDisciplina() {}
    public void attInformacoesProfessor() {}
}