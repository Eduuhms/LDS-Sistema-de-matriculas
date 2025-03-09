import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResponsavelFinanceiro extends Usuario {
    public ResponsavelFinanceiro(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.RESPOSAVELFINANCEIRO);

    }

    private List<Disciplina> disciplinasCobradas;

    @Override
    public void cadastrar() {
        super.cadastrar(); // Chama o método cadastrar da classe Usuario para cadastrar no usuarios.csv
        try (FileWriter writer = new FileWriter("LDS-Sistema-de-matriculas\\code\\ldsMaatriculas\\src\\csv\\responsavelFinanceiro.csv",
                true)) {
            writer.append("\n")
                    .append(String.valueOf(id)).append(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean gerarCobranca() {
        return false;
    }

    public void notificarCobranca() {
    }

    @Override
    public void setDados(){
        return;
    }

    @Override
    public void setDados(Boolean classes){
        return;
    }
}