import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Secretaria extends Usuario implements ResponsavelMatricula {

    public Secretaria(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.SECRETARIA); // Define o tipo como SECRETARIA
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

    @Override
    public void cadastrar() {
        super.cadastrar(); // Chama o método cadastrar da classe Usuario para cadastrar no usuarios.csv
        try (FileWriter writer = new FileWriter("LDS-Sistema-de-matriculas\\code\\ldsMaatriculas\\src\\csv\\secretarias.csv",
                true)) {
            writer.append("\n")
                    .append(String.valueOf(id)).append(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gerarCurriculo() {
    }

    public void attInformacoesAluno() {
    }

    public void attInformacoesDisciplina() {
    }

    public void attInformacoesProfessor() {
    }

    @Override
    public void setDados(){
        return;
    }
}