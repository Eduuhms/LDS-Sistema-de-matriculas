
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

    // public static List<Curso> listarCursos(List<String> linhas, List<Curso> cursos){
    //     List<Disciplina> cursos = new ArrayList<>();
    //     for (String linha : linhas){
    //         String[] dados = linha.split(",");
    //         Disciplina disciplina = new Disciplina(
    //             dados[0],
    //             dados[1],
    //             Integer.parseInt(dados[2]),
    //             Boolean.parseBoolean(dados[3]),
    //             dados[4]
    //         );

    //         disciplinas.add(disciplina);
    //     }

    //     return disciplinas;
    // }

    // public static List<Aluno> listarAlunos(List<String> linhas, List<Disciplina> disciplinas){
    //     List<Aluno> alunos = new ArrayList<>();
    //     for (String linha : linhas){
    //         List<Disciplina> listaDisciplinasObrigatorias = new ArrayList<>();
    //         List<Disciplina> listaDisciplinasOptativas = new ArrayList<>();
    //         String[] dados = linha.split(",");
            

    //         for (Disciplina disciplina : disciplinas){
    //             for (String disciplinaObrigatoria: dados[4].split(";")){
    //                 if (disciplina.getNome().equalsIgnoreCase(disciplinaObrigatoria)){
    //                     listaDisciplinasObrigatorias.add(disciplina);
    //                 }
    //             }

    //             for (String listaDisciplinasOptativa : dados[5].split(";")){
    //                 if (disciplina.getNome().equalsIgnoreCase(listaDisciplinasOptativa)){
    //                     listaDisciplinasOptativas.add(disciplina);
    //                 }
    //             }

    //             Aluno aluno = new Aluno(
    //                 dados[1],
    //                 dados[2],
    //                 dados[3],
    //                 listaDisciplinasObrigatorias,
    //                 listaDisciplinasOptativas
    //                 );
    //         }
            

    //         alunos.add(aluno);
    //     }

        // return alunos;
    // }
}

