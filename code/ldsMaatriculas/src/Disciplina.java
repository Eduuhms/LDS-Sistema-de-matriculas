import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Disciplina {
    private static final int maxAlunos = 60;
    private static final int minAlunos = 3;
    
    private String nome;
    private String codigo;
    private int creditos;
    private boolean ehObrigatoria;
    private String status;

    public Disciplina(String nome,
        String codigo,
        int creditos,
        boolean ehObrigatoria){
            this.codigo = codigo;
            this.creditos = creditos;
            this.ehObrigatoria = ehObrigatoria;
            this.status = "ABERTA";
        }

    public Disciplina(String codigo){
        this.codigo = codigo;
        this.creditos = 0;
        this.ehObrigatoria = false;
        this.status = "ABERTA";
    }

    public String statusDisciplina() { return ""; }
    public void gerarCurriculo() {}
    public void fecharMatriculas() {}
    public void addAluno(Aluno aluno) {}
    public void removerAluno(Aluno aluno) {}
    public List<Aluno> alunosMatriculados() { return null; }
    public void cancelarDisciplina() {}

    public void preencherComDadosCsv() throws Exception{
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv"))) {
            String linha;
            boolean primeiraLinha = true; // Flag para identificar a primeira linha
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // Pula a primeira linha
                    continue;
                }
                if (linha.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }

                String[] dados = linha.split(",");
                String codigoCsv = dados[1];

                if (codigoCsv.equals(this.codigo)){
                    this.nome = dados[0];
                    this.creditos = Integer.parseInt(dados[2]);
                    this.ehObrigatoria = Boolean.parseBoolean(dados[3]);
                    return;
                }
                
            }
            throw new Exception("Disciplina não encontrada no csv!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo(){
        return codigo;
    }
}
