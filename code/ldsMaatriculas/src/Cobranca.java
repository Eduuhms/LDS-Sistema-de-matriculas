import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Cobranca {

    public float calcularValorTotal(String matricula) {
        Map<String, String> alunos = new HashMap<>();
        Map<String, Integer> disciplinas = new HashMap<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\alunos.csv"))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 6) {
                    alunos.put(dados[1], dados[0] + "," + dados[4] + "," + dados[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\Disciplinas.csv"))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 3) {
                    try {
                        disciplinas.put(dados[1], Integer.parseInt(dados[2]));
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter créditos para número: " + dados[2]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        String alunoInfo = alunos.get(matricula);
        if (alunoInfo == null) {
            System.out.println("Aluno não encontrado.");
            return 0;
        }
        String[] alunoDados = alunoInfo.split(",");
        String disciplinasObrigatorias = alunoDados[1];
        String disciplinasOptativas = alunoDados[2];

        float somaCreditos = 0;
        if (!disciplinasObrigatorias.isEmpty() && !disciplinasObrigatorias.equals("null")) {
            String[] codigosObrigatorias = disciplinasObrigatorias.split(";");
            for (String codigo : codigosObrigatorias) {
                if (!codigo.isEmpty()) {
                    Integer creditos = disciplinas.get(codigo);
                    if (creditos != null) {
                        somaCreditos += creditos;
                    }
                }
            }
        }
        if (!disciplinasOptativas.isEmpty() && !disciplinasOptativas.equals("null")) {
            String[] codigosOptativas = disciplinasOptativas.split(";");
            for (String codigo : codigosOptativas) {
                if (!codigo.isEmpty()) {
                    Integer creditos = disciplinas.get(codigo);
                    if (creditos != null) {
                        somaCreditos += creditos;
                    }
                }
            }
        }
    
        return somaCreditos;
    }
}