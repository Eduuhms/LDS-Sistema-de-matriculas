import java.io.BufferedReader;
import java.io.BufferedWriter;
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

    public List<Disciplina> getDisciplinas(){
        return disciplinas;
    }

     @Override
    public void cadastrar() {
        super.cadastrar(); // Chama o método cadastrar da classe Usuario para cadastrar no usuarios.csv
        String codigosDisciplinas = "";
        for (Disciplina disciplina : this.disciplinas){
            codigosDisciplinas += disciplina.getCodigo();
            codigosDisciplinas += ";";
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
            List<Aluno> alunos = disciplinaProcurada.alunosMatriculados();
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

    public void addDisciplina(Disciplina disciplina) throws Exception{
        for (Disciplina disciplinaCadastrada : disciplinas){
            if (disciplinaCadastrada.getCodigo().equals(disciplina.getCodigo())){
                throw new Exception("Disciplina já ministrada pelo professor.");
            }
        }
        disciplina.preencherComDadosCsv(true);
        this.disciplinas.add(disciplina);
        atualizarRegistroCsv();
    }

    public void removeDisciplina(Disciplina disciplinaRemover) throws Exception{
        int indice, cont;
        cont = 0; indice = 0;
        boolean encontrado = false;

        for (Disciplina disciplinaCadastrada : disciplinas){
            if (disciplinaCadastrada.getCodigo().equals(disciplinaRemover.getCodigo())){
                indice = cont;
                encontrado = true;
            }
            cont ++;
        }

        if (encontrado){
            disciplinas.remove(indice);
            atualizarRegistroCsv();
            return;
        }

        throw new Exception("Disciplina não encontrada!");
    }

    @Override
    public void setDados(){
        setDados(false);
    }

    @Override
    public void setDados(Boolean classes){
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\professores.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                String[] dados = linha.split(",");
                String idCsv = dados[0];

                if (idCsv.equals(String.valueOf(this.getId()))){
                    if (classes){
                        String stringDisciplinas = dados[1];
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

    public void atualizarRegistroCsv(){
        String arquivoCSV = "code\\ldsMaatriculas\\src\\csv\\professores.csv";
        List<String> linhas = new ArrayList<>();
        boolean encontrada = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoCSV))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length > 1 && dados[0].equals(String.valueOf(this.id))) {
                    String codigosDisciplinas = "";
                    for (Disciplina disciplinaRegristada : this.disciplinas){
                        codigosDisciplinas += disciplinaRegristada.getCodigo();
                        codigosDisciplinas += ";";
                    }

                    codigosDisciplinas = codigosDisciplinas.equals("") ? null : codigosDisciplinas;

                    StringBuilder novaLinha = new StringBuilder();
                    novaLinha.append(this.id).append(",")
                             .append(codigosDisciplinas).append("");

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
            System.out.println("Erro: Professor com id " + this.id + " não encontrada!");
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


}