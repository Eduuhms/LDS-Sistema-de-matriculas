import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario implements ResponsavelMatricula {
    private static final int MAX_OBRIGATORIAS = 4;
    private static final int MAX_OPTATIVAS = 2;
    
    private String matricula;
    private String nome;
    private Curso curso;
    private List<Disciplina> disciplinasObrigatorias;
    private List<Disciplina> disciplinasOptativas;
    

    public Aluno(String nome, String email, String senha, String matricula, Curso curso) {
        super(nome, email, senha, TipoUsuario.ALUNO); 
        this.matricula = randomMatricula();
        this.nome = nome;
        this.curso = curso;
    }

    public Aluno(int id, String nome, String email, String senha, String matricula, Curso curso) {
        super(nome, email, senha, TipoUsuario.ALUNO, id); 
        this.nome = nome;
        this.matricula = randomMatricula();
        this.curso = curso;
    }
    
    public Aluno(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.ALUNO); 
        this.nome = nome;
        this.matricula = randomMatricula();
    }

    public Aluno(String matricula, String nome){
        super();
        this.nome = nome;
        this.matricula = matricula;
    }

    public Aluno(String nome, String email, String senha, int id){
        super(nome, email, senha, TipoUsuario.ALUNO, id);
        this.nome = null;
        this.matricula = null;
        this.curso = null;
        this.disciplinasObrigatorias = new ArrayList<>();
        this.disciplinasOptativas = new ArrayList<>();
    }

    public String getMatricula(){
        return this.matricula;
    }

    public Aluno(String matricula, String nome, Curso curso, List<Disciplina> disciplinasObrigatorias,
     List<Disciplina> disciplinasOptativas){
        super();
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.disciplinasObrigatorias = disciplinasObrigatorias;
        this.disciplinasOptativas = disciplinasOptativas;
    }

    private String randomMatricula() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    @Override
    public void cadastrar() {
        super.cadastrar(); // Chama o método cadastrar da classe Usuario para cadastrar no usuarios.csv
        try (FileWriter writer = new FileWriter("code\\ldsMaatriculas\\src\\csv\\alunos.csv", true)) {
            writer.append("\n")
                  .append(String.valueOf(id)).append(",")
                  .append(matricula).append(",")
                  .append(nome).append(",")
                  .append(null).append(",")
                  .append(null).append(",")
                  .append(null).append(",");
                  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void confirmarMatricula() {}
    public void cancelarMatricula(Disciplina disciplina) {}

    @Override
    public void setDados(){
        setDados(false);
    }

    @Override
    public void setDados(Boolean classes){
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\alunos.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                String[] dados = linha.split(",");
                String idCsv = dados[0];
                String matriculaCsv = dados[1];

                if (idCsv.equals(String.valueOf(this.getId())) || matriculaCsv.equals(String.valueOf(this.getMatricula()))){
                    this.id = Integer.parseInt(dados[0]);
                    this.matricula = dados[1];
                    this.nome = dados[2];

                    if (classes){
                        String idCursoCsv = dados[3];
                        if (idCursoCsv != null){
                            this.curso = new Curso(Integer.parseInt(idCursoCsv));
                            curso.setDados(true);
                            
                            List<Disciplina> disciplinasCurso = curso.getDisciplinas();
                            String idsDisciplinas = "";
                            idsDisciplinas = !"null".equals(dados[4]) ? idsDisciplinas + dados[4] : "";
                            idsDisciplinas = !"null".equals(dados[5]) ? idsDisciplinas + dados[5] : idsDisciplinas;
                            
                                for (String idDisciplina : idsDisciplinas.split(";")){
                                    for (Disciplina disciplinaCurso : disciplinasCurso){
                                        if (idDisciplina.equalsIgnoreCase(String.valueOf(disciplinaCurso.getCodigo()))){
                                            if (disciplinaCurso.getEhObrigatoria()){
                                                this.disciplinasObrigatorias.add(disciplinaCurso);
                                            } else {
                                                this.disciplinasOptativas.add(disciplinaCurso);
                                            }
                                            break;
                                        }
                                }
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

    @Override
    public String toString(){

        return "Nome: " + this.nome + "- Matrícula: " + this.matricula;
    }
    
    @Override
    public void cancelarMatricula(String matriculaAluno, String codigoDisciplina) {
        String arquivoAlunos = "code\\ldsMaatriculas\\src\\csv\\alunos.csv";
        String arquivoMatriculas = "code\\ldsMaatriculas\\src\\csv\\matriculas.csv";
        List<String> linhasMatriculas = new ArrayList<>();
        String alunoId = null;
    
        //Encontrando o ID do aluno com base na matrícula
        try (BufferedReader brAlunos = new BufferedReader(new FileReader(arquivoAlunos))) {
            String linha;
            brAlunos.readLine();
            while ((linha = brAlunos.readLine()) != null) {
                String[] dados = linha.split(",");
                String id = dados[0].trim();
                String matricula = dados[1].trim();
    
                if (matricula.equals(matriculaAluno)) {
                    alunoId = id;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (alunoId == null) {
            System.out.println("Aluno com matrícula " + matriculaAluno + " não encontrado.");
            return;
        }
    
        //Removendo a matrícula do aluno no arquivo matriculas.csv
        try (BufferedReader brMatriculas = new BufferedReader(new FileReader(arquivoMatriculas))) {
            String linha;
            brMatriculas.readLine();
            while ((linha = brMatriculas.readLine()) != null) {
                String[] dados = linha.split(",");
                String idAlunoMatricula = dados[0].trim();
                String codigoDisciplinaMatricula = dados[1].trim();
    
                // Verifica se a linha não corresponde à matrícula que deve ser cancelada
                if (!idAlunoMatricula.equals(alunoId) || !codigoDisciplinaMatricula.equals(codigoDisciplina)) {
                    linhasMatriculas.add(linha); // Mantém a linha se não for a matrícula a ser cancelada
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        //Reescrevendo o arquivo matriculas.csv
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoMatriculas))) {
            for (String linha : linhasMatriculas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        System.out.println("Matrícula cancelada: Aluno " + matriculaAluno + ", Disciplina " + codigoDisciplina);
    }
    
    
    public void matricularDisciplina(Disciplina disciplina) {}

    
}