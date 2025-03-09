import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
                  .append(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void confirmarMatricula() {}
    public void cancelarMatricula(Disciplina disciplina) {}

    @Override
    public void setDados(){
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\alunos.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                String[] dados = linha.split(",");
                String idCsv = dados[0];

                if (idCsv.equals(String.valueOf(this.getId()))){
                    this.matricula = dados[1];
                    // this.curso = dados[2];
                    
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){

        return this.nome + ", " + this.matricula;
    }
    
    @Override
    public void cancelarMatricula(Disciplina disciplina, Aluno aluno){}
    public void matricularDisciplina(Disciplina disciplina) {}

    
}