import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario {
    private List<Disciplina> disciplinas;


    public Professor(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.PROFESSOR); 
        this.disciplinas = new ArrayList<Disciplina>();
    }

    public Professor(String nome, String email, String senha, String id) {
        super(nome, email, senha, TipoUsuario.PROFESSOR, Integer.parseInt(id)); 
        this.disciplinas = new ArrayList<Disciplina>();
    }

    public Professor(String nome, String email, String senha, String id, List<Disciplina> disciplinas) {
        super(nome, email, senha, TipoUsuario.PROFESSOR, Integer.parseInt(id)); 
        this.disciplinas = disciplinas;
    }

    public Professor(String nome, String email, String senha, List<Disciplina> disciplinas) {
        super(nome, email, senha, TipoUsuario.PROFESSOR); 
        this.disciplinas = disciplinas;
    }

     @Override
    public void cadastrar() {
        super.cadastrar(); // Chama o método cadastrar da classe Usuario para cadastrar no usuarios.csv
        String codigosDisciplinas = "";
        for (Disciplina disciplina : this.disciplinas){
            codigosDisciplinas += disciplina.getCodigo();
            codigosDisciplinas += "|";
        }
        try (FileWriter writer = new FileWriter("code\\ldsMaatriculas\\src\\csv\\professores.csv", true)) {
            writer.append("\n")
                  .append(String.valueOf(id)).append(",")
                  .append(codigosDisciplinas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> visualizarAlunos(Disciplina disciplina) {
        Disciplina disciplinaProcurada = procurarDisciplina(disciplina);
        if (disciplinaProcurada != null){
            List<Aluno> alunos = disciplina.alunosMatriculados();
            return alunos;
        }
        return null;
    }

    private Disciplina procurarDisciplina(Disciplina disciplinaProcurada){
        for (Disciplina disciplina : disciplinas){
            if (disciplina.equals(disciplinaProcurada)){
                return disciplina;
            }
        }
        return null;
    }

    @Override
    public void setDados(){
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\professores.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                String[] dados = linha.split(",");
                String idCsv = dados[0];

                if (idCsv.equals(String.valueOf(this.getId()))){
                    String stringDisciplinas = dados[1];
                    String[] codigosDisciplinas = stringDisciplinas.split("\\|");
                    
                    for (String codigoDisciplina : codigosDisciplinas){
                        Disciplina disciplina = new Disciplina(codigoDisciplina);
                        try {
                            disciplina.preencherComDadosCsv();
                            disciplinas.add(disciplina);
                            
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }  
                    
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}