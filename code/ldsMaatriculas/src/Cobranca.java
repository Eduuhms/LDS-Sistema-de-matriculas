import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cobranca {

    public float calcularValorTotal(String matricula) {
        Map<String, String> alunos = new HashMap<>();
        Map<String, List<String>> matriculas = new HashMap<>(); // Agora armazena uma lista de disciplinas por aluno
        Map<String, Integer> disciplinas = new HashMap<>();

        // Ler o arquivo alunos.csv
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\alunos.csv"))) {
            String linha;
            br.readLine(); // Pular o cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) { // Verifica se há pelo menos 2 colunas
                    alunos.put(dados[1], dados[0]); // matricula -> id
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
                if (dados.length >= 2) { // Verifica se há pelo menos 2 colunas
                    String alunoId = dados[0];
                    String disciplinaCodigo = dados[1];

                    // Adiciona a disciplina à lista de disciplinas do aluno
                    matriculas.computeIfAbsent(alunoId, k -> new ArrayList<>()).add(disciplinaCodigo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler o arquivo Disciplinas.csv
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv"))) {
            String linha;
            br.readLine(); // Pular o cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 3) { // Verifica se há pelo menos 3 colunas
                    try {
                        disciplinas.put(dados[1], Integer.parseInt(dados[2])); // codigo -> creditos
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter créditos para número: " + dados[2]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Encontrar o id do aluno com base na matrícula
        String alunoId = alunos.get(matricula);
        if (alunoId == null) {
            System.out.println("Aluno não encontrado.");
            return 0;
        }

        // Encontrar as disciplinas em que o aluno está matriculado
        float somaCreditos = 0;
        List<String> disciplinasDoAluno = matriculas.get(alunoId);
        if (disciplinasDoAluno != null) {
            for (String codigoDisciplina : disciplinasDoAluno) {
                Integer creditos = disciplinas.get(codigoDisciplina);
                if (creditos != null) {
                    somaCreditos += creditos;
                }
            }
        }

        return somaCreditos;
    }
}