import java.util.List;

public class ResponsavelFinanceiro extends Usuario {
    private List<Disciplina> disciplinasCobradas;

    public boolean gerarCobranca() { return false; }
    public void notificarCobranca() {}
}