import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    private String status;
    private List<Aluno> alunosMatriculados;

    public Disciplina(String nome,
        String codigo,
        int creditos,
        boolean ehObrigatoria,
        String status){
            this.codigo = codigo;
            this.creditos = creditos;
            this.ehObrigatoria = ehObrigatoria;
            this.status = status;
            this.alunosMatriculados = new ArrayList<>();
            this.nome = nome;
        }

    public Disciplina(String nome,
        String codigo,
        int creditos,
        boolean ehObrigatoria){
            this.codigo = codigo;
            this.creditos = creditos;
            this.ehObrigatoria = ehObrigatoria;
            this.status = "ABERTA";
            this.alunosMatriculados = new ArrayList<>();
            this.nome = nome;
        }

    public Disciplina(String codigo) {
        this.codigo = codigo;
        this.nome = null;
        this.creditos = 0;
        this.ehObrigatoria = false;
        this.status = "ABERTA";
        this.alunosMatriculados = new ArrayList<>();
    }

    public Disciplina(String codigo, String nome){
        this.codigo = codigo;
        this.nome = nome;
        this.alunosMatriculados = new ArrayList<>();
    }

    public void salvarDisciplina() {
        String codigosAlunos = "";
        for (Aluno alunoMatriculado : this.alunosMatriculados){
            codigosAlunos += alunoMatriculado.getMatricula();
            codigosAlunos += ";";
        }
        codigosAlunos = codigosAlunos.equals("") ? null : codigosAlunos;

        try (FileWriter writer = new FileWriter("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv", true)) {
            if (new java.io.File("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv").length() == 0) {
                writer.append("nome,codigo,creditos,ehObrigatoria\n");
            }
            writer.append(nome).append(",")
                  .append(codigo).append(",")
                  .append(String.valueOf(creditos)).append(",")
                  .append(String.valueOf(ehObrigatoria)).append(",")
                  .append(String.valueOf(status)).append(",")
                  .append(String.valueOf(codigosAlunos)).append("\n");
            System.out.println("Disciplina salva com sucesso no arquivo Disciplinas.csv.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> alunosMatriculados() {
        return alunosMatriculados;
    }

    public void atualizarRegistroCsv(){
        String arquivoCSV = "code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv";
        List<String> linhas = new ArrayList<>();
        boolean encontrada = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoCSV))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length > 1 && dados[1].equals(this.codigo)) {
                    String codigosAlunos = "";
                    for (Aluno alunoMatriculado : this.alunosMatriculados){
                        codigosAlunos += alunoMatriculado.getMatricula();
                        codigosAlunos += ";";
                    }

                    codigosAlunos = codigosAlunos.equals("") ? null : codigosAlunos;

                    StringBuilder novaLinha = new StringBuilder();
                    novaLinha.append(this.nome).append(",")
                             .append(this.codigo).append(",")
                             .append(this.creditos).append(",")
                             .append(this.ehObrigatoria).append(",")
                             .append(this.status).append(",")
                             .append(codigosAlunos).append("");

                    linhas.add(novaLinha.toString());
                    encontrada = true;
                } else {
                    linhas.add(linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (!encontrada) {
            System.out.println("Erro: Disciplina com código " + this.codigo + " não encontrada!");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoCSV))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void preencherComDadosCsv() throws Exception{
        preencherComDadosCsv(false);
    }
    
    public void preencherComDadosCsv(Boolean classes) throws Exception{
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
                    this.status = dados[4];

                    if (classes){
                        String stringalunosMatriculados = dados[5];
                        String[] codigosalunosMatriculados = stringalunosMatriculados.split(";");
                        for (String codigoAluno : codigosalunosMatriculados){
                            if (!codigoAluno.equals("null")){
                                Aluno aluno = new Aluno(codigoAluno, "");
                                aluno.setDados();
                                alunosMatriculados.add(aluno);
                            }
                        }
                    }
                    return;
                }
            }
            throw new Exception("Disciplina não encontrada no csv!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String statusDisciplina() {
        int totalalunosMatriculados = alunosMatriculados.size();

        if (totalalunosMatriculados < minAlunos) {
            status = "INATIVA"; 
        } else if (totalalunosMatriculados >= minAlunos && totalalunosMatriculados < maxAlunos) {
            status = "ABERTA"; 
        } else if (totalalunosMatriculados == maxAlunos) {
            status = "FECHADA";
        }

        return status;
    }

    public void addAluno(Aluno aluno) throws Exception{
        if (alunosMatriculados.size() < maxAlunos) {
            alunosMatriculados.add(aluno);
            System.out.println("Aluno " + aluno.getNome() + " matriculado na disciplina " + this.nome);
            statusDisciplina();
            atualizarRegistroCsv();
            return;
        } 

        throw new Exception ("Não foi possível matricular o aluno. A disciplina está cheia.");
    }

    public void removerAluno(Aluno aluno) throws Exception{
        if (alunosMatriculados.remove(aluno)) {
            System.out.println("Aluno " + aluno.getNome() + " removido da disciplina " + this.nome);
            statusDisciplina(); 
            atualizarRegistroCsv();
            return;
        } 

        throw new Exception ("Aluno não encontrado na disciplina.");
    }

    public static List<Disciplina> carregarDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv"))) {
            String linha;
            boolean primeiraLinha = true; // Pula o cabeçalho
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String[] dados = linha.split(",");
                String nome = dados[0];
                String codigoDisciplina = dados[1];
                int creditos = Integer.parseInt(dados[2]);
                boolean ehObrigatoria = Boolean.parseBoolean(dados[3]);
                String status = dados[4];

                disciplinas.add(new Disciplina(nome, codigoDisciplina, creditos, ehObrigatoria, status));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return disciplinas;
    }

    public void cancelarDisciplina() {
        status = "CANCELADA";
        System.out.println("Disciplina " + this.nome + " cancelada.");
    }

    public String getNome() {
        return nome;
    }

    public Boolean getEhObrigatoria() {
        return ehObrigatoria;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
	public boolean equals(Object outroObjeto) {
		Disciplina outraDisciplina = (Disciplina) outroObjeto;
        Boolean resultado = this.codigo.equals(outraDisciplina.getCodigo());
        if (resultado == true){
            return resultado;
        } else {
            return (this.nome.equalsIgnoreCase(outraDisciplina.getNome()));
        }
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