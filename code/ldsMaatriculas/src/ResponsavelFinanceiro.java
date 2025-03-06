import java.util.List;

public class ResponsavelFinanceiro extends Usuario {
    public ResponsavelFinanceiro(int id, String nome, String email, String senha, TipoUsuario tipo) {
            super(id, nome, email, senha, tipo);
            //TODO Auto-generated constructor stub

        }
        private List<Disciplina> disciplinasCobradas;

    public boolean gerarCobranca() { return false; }
    public void notificarCobranca() {}
}