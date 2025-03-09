import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private static final int maxAlunos = 60;
    private static final int minAlunos = 3;

    private String nome;
    private String codigo;
    private int creditos;
    private boolean ehObrigatoria;
    private List<Aluno> alunosMatriculados;
    private String status;

    public Disciplina(String nome, String codigo, int creditos, boolean ehObrigatoria) {
        this.nome = nome;
        this.codigo = codigo;
        this.creditos = creditos;
        this.ehObrigatoria = ehObrigatoria;
        this.alunosMatriculados = new ArrayList<>();
        this.status = "ABERTA";
    }

    public Disciplina(String codigo) {
        this.codigo = codigo;
        this.creditos = 0;
        this.ehObrigatoria = false;
        this.alunosMatriculados = new ArrayList<>();
        this.status = "ABERTA";
    }

    public void salvarDisciplina() {
        try (FileWriter writer = new FileWriter("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv", true)) {
            if (new java.io.File("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv").length() == 0) {
                writer.append("nome,codigo,creditos,ehObrigatoria\n");
            }
            writer.append(nome).append(",")
                  .append(codigo).append(",")
                  .append(String.valueOf(creditos)).append(",")
                  .append(String.valueOf(ehObrigatoria)).append("\n");
            System.out.println("Disciplina salva com sucesso no arquivo Disciplinas.csv.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void preencherComDadosCsv() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv"))) {
            String linha;
            boolean primeiraLinha = true; 
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; 
                    continue;
                }
                if (linha.trim().isEmpty()) {
                    continue; 
                }

                String[] dados = linha.split(",");
                String codigoCsv = dados[1];

                if (codigoCsv.equals(this.codigo)) {
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

    public String statusDisciplina() {
        int totalAlunos = alunosMatriculados.size();

        if (totalAlunos < minAlunos) {
            status = "INATIVA"; 
        } else if (totalAlunos >= minAlunos && totalAlunos < maxAlunos) {
            status = "ABERTA"; 
        } else if (totalAlunos == maxAlunos) {
            status = "FECHADA";
        }

        return status;
    }

    // public void addAluno(Aluno aluno) {
    //     if (alunosMatriculados.size() < maxAlunos) {
    //         alunosMatriculados.add(aluno);
    //         System.out.println("Aluno " + aluno.getNome() + " matriculado na disciplina " + this.nome);
    //         statusDisciplina(); 
    //     } else {
    //         System.out.println("Não foi possível matricular o aluno. A disciplina está cheia.");
    //     }
    // }

    // public void removerAluno(Aluno aluno) {
    //     if (alunosMatriculados.remove(aluno)) {
    //         System.out.println("Aluno " + aluno.getNome() + " removido da disciplina " + this.nome);
    //         statusDisciplina(); 
    //     } else {
    //         System.out.println("Aluno não encontrado na disciplina.");
    //     }
    // }

    public List<Aluno> alunosMatriculados() {
        return alunosMatriculados;
    }

    public void cancelarDisciplina() {
        status = "CANCELADA";
        System.out.println("Disciplina " + this.nome + " cancelada.");
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCreditos() {
        return creditos;
    }

    public boolean isEhObrigatoria() {
        return ehObrigatoria;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", creditos=" + creditos +
                ", ehObrigatoria=" + ehObrigatoria +
                ", status=" + status +
                '}';
    }
}