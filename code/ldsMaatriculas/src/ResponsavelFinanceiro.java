import java.util.List;

public class ResponsavelFinanceiro extends Usuario {
    public ResponsavelFinanceiro(String nome, String email, String senha, TipoUsuario tipo) {
            super(nome, email, senha, tipo);

        }
        private List<Disciplina> disciplinasCobradas;

    public boolean gerarCobranca() { return false; }
    public void notificarCobranca() {}
}