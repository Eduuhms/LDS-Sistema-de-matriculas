public class Secretaria extends Usuario implements ResponsavelMatricula {

    public Secretaria(int id, String nome, String email, String senha) {
        super(id, nome, email, senha, TipoUsuario.SECRETARIA); // Define o tipo como SECRETARIA
    }

    public void cancelarMatricula(){}
    
    public void gerarCurriculo() {}
    public void attInformacoesAluno() {}
    public void attInformacoesDisciplina() {}
    public void attInformacoesProfessor() {}
}