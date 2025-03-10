import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
public class ResponsavelFinanceiro extends Usuario {
    public ResponsavelFinanceiro(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.RESPOSAVELFINANCEIRO);

    }

    private List<Disciplina> disciplinasCobradas;

    @Override
    public void cadastrar() {
        super.cadastrar(); 
        try (FileWriter writer = new FileWriter("code\\ldsMaatriculas\\src\\csv\\responsavelFinanceiro.csv",
                true)) {
            writer.append("\n")
                    .append(String.valueOf(id)).append(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gerarCobranca(String matricula) {
        Map<String, String> alunos = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\alunos.csv"))) {
            String linha;
            br.readLine(); 
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    alunos.put(dados[1], dados[0]); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String alunoId = alunos.get(matricula);
        if (alunoId == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        Cobranca cobranca = new Cobranca();
        float valor = cobranca.calcularValorTotal(matricula);

        int novoId = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\cobrancas.csv"))) {
            String linha;
            br.readLine(); 
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 1) {
                    int idAtual = Integer.parseInt(dados[0]);
                    if (idAtual >= novoId) {
                        novoId = idAtual + 1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("code\\ldsMaatriculas\\src\\csv\\cobrancas.csv", true))) {
            String novaCobranca = novoId + "," + alunoId + "," + valor;
            bw.write(novaCobranca);
            bw.newLine();
            System.out.println("Cobrança gerada com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
        }
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