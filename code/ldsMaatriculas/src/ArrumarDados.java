
import java.util.ArrayList;
import java.util.List;


public class ArrumarDados {
    public static List<Disciplina> listarDisciplinas(List<String> linhas){
        List<Disciplina> disciplinas = new ArrayList<>();
        for (String linha : linhas){
            String[] dados = linha.split(",");
            Disciplina disciplina = new Disciplina(
                dados[0],
                dados[1],
                Integer.parseInt(dados[2]),
                Boolean.parseBoolean(dados[3]),
                dados[4]
            );

            disciplinas.add(disciplina);
        }

        return disciplinas;
    }
}

