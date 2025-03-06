import java.util.List;

public class ResponsavelFinanceiro extends Usuario {
    public ResponsavelFinanceiro(String nome, String email, String senha) {
            super(nome, email, senha, TipoUsuario.RESPOSAVELFINANCEIRO);

        }
        private List<Disciplina> disciplinasCobradas;

    public boolean gerarCobranca() { return false; }
    public void notificarCobranca() {}
}