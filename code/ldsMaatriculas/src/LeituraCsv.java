import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeituraCsv {
    public static List<String> lerCsv(String path){
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String linha;
            boolean primeiraLinha = true; // Flag para identificar a primeira linha
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // Pula a primeira linha
                    continue;
                }
                if (linha.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                linhas.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return linhas;
    }
}
