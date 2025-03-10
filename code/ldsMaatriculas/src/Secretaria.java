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
        super(nome, email, senha, TipoUsuario.SECRETARIA); 
    }

    @Override
    public void cancelarMatricula(String matriculaAluno, String codigoDisciplina) {
        String arquivoAlunos = "code\\ldsMaatriculas\\src\\csv\\alunos.csv";
        String arquivoDisciplinas = "code\\ldsMaatriculas\\src\\csv\\disciplinas.csv";
        List<String> linhasAlunos = new ArrayList<>();
        List<String> linhasDisciplinas = new ArrayList<>();
        String alunoId = null;

        try (BufferedReader brAlunos = new BufferedReader(new FileReader(arquivoAlunos))) {
            String linha;
            brAlunos.readLine(); 
            while ((linha = brAlunos.readLine()) != null) {
                String[] dados = linha.split(",");
                String id = dados[0].trim();
                String matricula = dados[1].trim();
    
                if (matricula.equals(matriculaAluno)) {
                    alunoId = id;
                    String disciplinasObrigatorias = dados[4].trim();
                    String disciplinasOptativas = dados[5].trim();
    
                    if (disciplinasObrigatorias.contains(codigoDisciplina)) {
                        disciplinasObrigatorias = disciplinasObrigatorias.replace(codigoDisciplina + ";", "").trim();
                    }

                    if (disciplinasOptativas.contains(codigoDisciplina)) {
                        disciplinasOptativas = disciplinasOptativas.replace(codigoDisciplina + ";", "").trim();
                    }

                    linha = id + "," + matricula + "," + dados[2] + "," + dados[3] + "," + disciplinasObrigatorias + "," + disciplinasOptativas;
                }
                linhasAlunos.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (alunoId == null) {
            System.out.println("Aluno com matrícula " + matriculaAluno + " não encontrado.");
            return;
        }

        try (BufferedReader brDisciplinas = new BufferedReader(new FileReader(arquivoDisciplinas))) {
            String linha;
            brDisciplinas.readLine();
            while ((linha = brDisciplinas.readLine()) != null) {
                String[] dados = linha.split(",");
                String codigo = dados[1].trim();
                String alunos = dados[5].trim();
    
                if (codigo.equals(codigoDisciplina)) {
                    if (alunos.contains(alunoId)) {
                        alunos = alunos.replace(alunoId + ";", "").trim();
                    }
                    linha = dados[0] + "," + codigo + "," + dados[2] + "," + dados[3] + "," + dados[4] + "," + alunos;
                }
                linhasDisciplinas.add(linha); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (BufferedWriter bwAlunos = new BufferedWriter(new FileWriter(arquivoAlunos))) {
            bwAlunos.write("id,matricula,nome,curso_id,disciplinas_obrigatorias,disciplinas_optativas"); 
            bwAlunos.newLine();
            for (String linha : linhasAlunos) {
                bwAlunos.write(linha);
                bwAlunos.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (BufferedWriter bwDisciplinas = new BufferedWriter(new FileWriter(arquivoDisciplinas))) {
            bwDisciplinas.write("nome,codigo,creditos,ehObrigatoria,status,alunos"); 
            bwDisciplinas.newLine();
            for (String linha : linhasDisciplinas) {
                bwDisciplinas.write(linha);
                bwDisciplinas.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        System.out.println("Matrícula cancelada: Aluno " + matriculaAluno + ", Disciplina " + codigoDisciplina);
    }

    @Override
    public void cadastrar() {
        super.cadastrar(); 
        try (FileWriter writer = new FileWriter("code\\ldsMaatriculas\\src\\csv\\secretarias.csv",
                true)) {
            writer.append("\n")
                    .append(String.valueOf(id)).append(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gerarCurriculo(String matricula) {
        Map<String, String> alunos = new HashMap<>();
        Map<String, String> cursos = new HashMap<>();
        Map<String, String> disciplinas = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\alunos.csv"))) {
            String linha;
            br.readLine(); 
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 6) { 
                    alunos.put(dados[1], dados[0] + "," + dados[2] + "," + dados[3] + "," + dados[4] + "," + dados[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\cursos.csv"))) {
            String linha;
            br.readLine(); 
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    cursos.put(dados[0], dados[1]);
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
                if (dados.length >= 2) {
                    disciplinas.put(dados[1], dados[0]); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String alunoInfo = alunos.get(matricula);
        if (alunoInfo == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }
        String[] alunoDados = alunoInfo.split(",");
        String alunoId = alunoDados[0];
        String nomeAluno = alunoDados[1]; 
        String cursoId = alunoDados[2];
        String disciplinasObrigatorias = alunoDados[3];
        String disciplinasOptativas = alunoDados[4];

        String nomeCurso = cursos.get(cursoId);
        if (nomeCurso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        List<String> nomesDisciplinas = new ArrayList<>();
        if (!disciplinasObrigatorias.isEmpty() && !disciplinasObrigatorias.equals("null")) {
            String[] codigosObrigatorias = disciplinasObrigatorias.split(";");
            for (String codigo : codigosObrigatorias) {
                if (!codigo.isEmpty()) {
                    String nomeDisciplina = disciplinas.get(codigo);
                    if (nomeDisciplina != null) {
                        nomesDisciplinas.add(nomeDisciplina);
                    }
                }
            }
        }
        if (!disciplinasOptativas.isEmpty() && !disciplinasOptativas.equals("null")) {
            String[] codigosOptativas = disciplinasOptativas.split(";");
            for (String codigo : codigosOptativas) {
                if (!codigo.isEmpty()) {
                    String nomeDisciplina = disciplinas.get(codigo);
                    if (nomeDisciplina != null) {
                        nomesDisciplinas.add(nomeDisciplina);
                    }
                }
            }
        }
    
        System.out.println("Nome do Aluno: " + nomeAluno);
        System.out.println("Curso: " + nomeCurso);
        System.out.println("Disciplinas Matriculadas: " + String.join(", ", nomesDisciplinas));
    }


    public void InformacoesAluno(String matricula) {
        String arquivoAlunos = "code\\ldsMaatriculas\\src\\csv\\alunos.csv";
        String arquivoUsuarios = "code\\ldsMaatriculas\\src\\csv\\usuarios.csv";
        String arquivoCursos = "code\\ldsMaatriculas\\src\\csv\\cursos.csv";
        String arquivoDisciplinas = "code\\ldsMaatriculas\\src\\csv\\disciplinas.csv";
    
        Map<String, String> alunos = new HashMap<>();
        Map<String, String> usuarios = new HashMap<>();
        Map<String, String> cursos = new HashMap<>();
        Map<String, String> disciplinas = new HashMap<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoAlunos))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 6) {
                    alunos.put(dados[1], dados[0] + "," + dados[2] + "," + dados[3] + "," + dados[4] + "," + dados[5]); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoUsuarios))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 5) {
                    usuarios.put(dados[0], dados[1] + "," + dados[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoCursos))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    cursos.put(dados[0], dados[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoDisciplinas))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    disciplinas.put(dados[1], dados[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        String alunoInfo = alunos.get(matricula);
        if (alunoInfo == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }
        String[] alunoDados = alunoInfo.split(",");
        String alunoId = alunoDados[0];
        String nomeAluno = alunoDados[1];
        String cursoId = alunoDados[2];
        String disciplinasObrigatorias = alunoDados[3];
        String disciplinasOptativas = alunoDados[4];
    
        String usuarioInfo = usuarios.get(alunoId);
        if (usuarioInfo == null) {
            System.out.println("Email do aluno não encontrado.");
            return;
        }
        String[] usuarioDados = usuarioInfo.split(",");
        String emailAluno = usuarioDados[1];
    
        String nomeCurso = cursos.get(cursoId);
        if (nomeCurso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }
    
        List<String> nomesDisciplinas = new ArrayList<>();
        if (!disciplinasObrigatorias.isEmpty() && !disciplinasObrigatorias.equals("null")) {
            String[] codigosObrigatorias = disciplinasObrigatorias.split(";");
            for (String codigo : codigosObrigatorias) {
                if (!codigo.isEmpty()) {
                    String nomeDisciplina = disciplinas.get(codigo);
                    if (nomeDisciplina != null) {
                        nomesDisciplinas.add(nomeDisciplina);
                    }
                }
            }
        }
        if (!disciplinasOptativas.isEmpty() && !disciplinasOptativas.equals("null")) {
            String[] codigosOptativas = disciplinasOptativas.split(";");
            for (String codigo : codigosOptativas) {
                if (!codigo.isEmpty()) {
                    String nomeDisciplina = disciplinas.get(codigo);
                    if (nomeDisciplina != null) {
                        nomesDisciplinas.add(nomeDisciplina);
                    }
                }
            }
        }
    
        System.out.println("Nome do Aluno: " + nomeAluno);
        System.out.println("Matrícula: " + matricula);
        System.out.println("Email: " + emailAluno);
        System.out.println("Curso: " + nomeCurso);
        System.out.println("Disciplinas Matriculadas: " + String.join(", ", nomesDisciplinas));
    }

    public void InformacoesDisciplina(String nomeDisciplina) {
        String arquivoDisciplinas = "code\\ldsMaatriculas\\src\\csv\\disciplinas.csv";
    
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoDisciplinas))) {
            String linha;
            br.readLine();
            boolean disciplinaEncontrada = false;
    
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 6 && dados[0].trim().equalsIgnoreCase(nomeDisciplina)) {
                    String codigo = dados[1].trim();
                    String creditos = dados[2].trim();
                    String status = dados[4].trim();
    
                    System.out.println("Nome da Disciplina: " + nomeDisciplina);
                    System.out.println("Código: " + codigo);
                    System.out.println("Valor: " + creditos);
                    System.out.println("Status: " + status);
    
                    disciplinaEncontrada = true;
                    break;
                }
            }
    
            if (!disciplinaEncontrada) {
                System.out.println("Disciplina não encontrada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void InformacoesProfessor(String nomeProfessor) {
        String arquivoProfessores = "code\\ldsMaatriculas\\src\\csv\\professores.csv";
        String arquivoUsuarios = "code\\ldsMaatriculas\\src\\csv\\usuarios.csv";
        String arquivoDisciplinas = "code\\ldsMaatriculas\\src\\csv\\disciplinas.csv";
    
        Map<String, String> professores = new HashMap<>();
        Map<String, String> usuarios = new HashMap<>();
        Map<String, String> disciplinas = new HashMap<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoProfessores))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    professores.put(dados[0], dados[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoUsuarios))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 5) {
                    usuarios.put(dados[1], dados[0] + "," + dados[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoDisciplinas))) {
            String linha;
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    disciplinas.put(dados[1], dados[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        String usuarioInfo = usuarios.get(nomeProfessor);
        if (usuarioInfo == null) {
            System.out.println("Professor não encontrado.");
            return;
        }
        String[] usuarioDados = usuarioInfo.split(",");
        String professorId = usuarioDados[0];
        String emailProfessor = usuarioDados[1];
    
        String disciplinasProfessor = professores.get(professorId);
        List<String> nomesDisciplinas = new ArrayList<>();
        if (!disciplinasProfessor.isEmpty() && !disciplinasProfessor.equals("null")) {
            String[] codigosDisciplinas = disciplinasProfessor.split(";");
            for (String codigo : codigosDisciplinas) {
                if (!codigo.isEmpty()) {
                    String nomeDisciplina = disciplinas.get(codigo);
                    if (nomeDisciplina != null) {
                        nomesDisciplinas.add(nomeDisciplina);
                    }
                }
            }
        }
    
        System.out.println("Nome do Professor: " + nomeProfessor);
        System.out.println("Email: " + emailProfessor);
        System.out.println("Disciplinas Ministradas: " + String.join(", ", nomesDisciplinas));
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