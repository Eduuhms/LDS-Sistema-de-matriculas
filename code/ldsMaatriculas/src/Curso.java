import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Curso {
    private static int proximoId = encontrarUltimoId() + 1; // Inicializa com o último ID + 1

    private int idCurso;
    private String nome;
    private int creditos;
    private List<Disciplina> disciplinas;

    public Curso(int idcurso) {
        this.idCurso = idcurso++;
        this.nome = null;
        this.creditos = 0;
        this.disciplinas = new ArrayList<>();
    }
    
    // Construtor sem idCurso (gerado automaticamente)
    public Curso(String nome, int creditos) {
        this.idCurso = proximoId++; // Gera o próximo ID
        this.nome = nome;
        this.creditos = creditos;
        this.disciplinas = new ArrayList<>();
    }

    // Método para encontrar o último ID no arquivo CSV
    private static int encontrarUltimoId() {
        int ultimoId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\cursos.csv"))) {
            String linha;
            boolean primeiraLinha = true; // Pula o cabeçalho
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String[] dados = linha.split(",");
                int idAtual = Integer.parseInt(dados[0]);
                if (idAtual > ultimoId) {
                    ultimoId = idAtual;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ultimoId;
    }

    // Método para salvar o curso no arquivo CSV
    public void salvarCurso() {
        try (FileWriter writer = new FileWriter("code\\ldsMaatriculas\\src\\csv\\cursos.csv", true)) {
            writer.append(String.valueOf(idCurso)).append(",")
                  .append(nome).append(",")
                  .append(String.valueOf(creditos)).append("\n");
            System.out.println("Curso salvo com sucesso no arquivo cursos.csv.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método estático para carregar cursos do arquivo CSV
    public static List<Curso> carregarCursos() {
        List<Curso> cursos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\cursos.csv"))) {
            String linha;
            boolean primeiraLinha = true; // Pula o cabeçalho
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String[] dados = linha.split(",");
                int idCurso = Integer.parseInt(dados[0]);
                String nome = dados[1];
                int creditos = Integer.parseInt(dados[2]);
                cursos.add(new Curso(nome, creditos));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    public void setDados(Boolean classes){
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\cursos.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                String[] dados = linha.split(",");
                String idCsv = dados[0];

                if (idCsv.equals(String.valueOf(this.idCurso))){
                    this.nome = dados[1];
                    this.creditos = Integer.parseInt(dados[2]);

                    if (classes){
                        String stringDisciplinas = dados[3];
                        String[] codigosDisciplinas = stringDisciplinas.split(";");
                        
                        for (String codigoDisciplina : codigosDisciplinas){
                            Disciplina disciplina = new Disciplina(codigoDisciplina);
                            try {
                                disciplina.preencherComDadosCsv(true);
                                disciplinas.add(disciplina);
                                
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }  
                    }
                    
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        if (disciplina != null) {
            disciplinas.add(disciplina);
            System.out.println("Disciplina " + disciplina.getNome() + " adicionada ao curso " + this.nome);
        } else {
            System.out.println("Erro: Disciplina inválida.");
        }
    }



    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "idCurso=" + idCurso +
                ", nome='" + nome + '\'' +
                ", creditos=" + creditos +
                ", disciplinas=" + disciplinas +
                '}';
    }
}