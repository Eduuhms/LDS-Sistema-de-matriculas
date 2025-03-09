import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public void gerarCurriculo(String matricula) {
        Map<String, String> alunos = new HashMap<>();
        Map<String, String> usuarios = new HashMap<>();
        Map<String, String> cursos = new HashMap<>();
        Map<String, List<String>> matriculas = new HashMap<>();
        Map<String, String> disciplinas = new HashMap<>();

        // Ler o arquivo alunos.csv
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\alunos.csv"))) {
            String linha;
            br.readLine(); // Pular o cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 3) {
                    alunos.put(dados[1], dados[0] + "," + dados[2]); // matricula -> id,curso_id
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler o arquivo usuarios.csv
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\usuarios.csv"))) {
            String linha;
            br.readLine(); // Pular o cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    usuarios.put(dados[0], dados[1]); // id -> nome
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler o arquivo curso.csv
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\cursos.csv"))) {
            String linha;
            br.readLine(); // Pular o cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    cursos.put(dados[0], dados[1]); // idCurso -> nome
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler o arquivo matriculas.csv
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\matriculas.csv"))) {
            String linha;
            br.readLine(); // Pular o cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    String alunoId = dados[0];
                    String disciplinaCodigo = dados[1];
                    matriculas.computeIfAbsent(alunoId, k -> new ArrayList<>()).add(disciplinaCodigo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler o arquivo disciplinas.csv
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv"))) {
            String linha;
            br.readLine(); // Pular o cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    disciplinas.put(dados[1], dados[0]); // codigo -> nome
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Encontrar o id e curso_id do aluno com base na matrícula
        String alunoInfo = alunos.get(matricula);
        if (alunoInfo == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }
        String[] alunoDados = alunoInfo.split(",");
        String alunoId = alunoDados[0];
        String cursoId = alunoDados[1];

        // Encontrar o nome do aluno
        String nomeAluno = usuarios.get(alunoId);
        if (nomeAluno == null) {
            System.out.println("Nome do aluno não encontrado.");
            return;
        }

        // Encontrar o nome do curso
        String nomeCurso = cursos.get(cursoId);
        if (nomeCurso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        // Encontrar as disciplinas matriculadas
        List<String> disciplinasDoAluno = matriculas.get(alunoId);
        List<String> nomesDisciplinas = new ArrayList<>();
        if (disciplinasDoAluno != null) {
            for (String codigoDisciplina : disciplinasDoAluno) {
                String nomeDisciplina = disciplinas.get(codigoDisciplina);
                if (nomeDisciplina != null) {
                    nomesDisciplinas.add(nomeDisciplina);
                }
            }
        }

        // Exibir o currículo
        System.out.println("Nome do Aluno: " + nomeAluno);
        System.out.println("Curso: " + nomeCurso);
        System.out.println("Disciplinas Matriculadas: " + String.join(", ", nomesDisciplinas));
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

    @Override
    public void setDados(Boolean classes){
        return;
    }
}