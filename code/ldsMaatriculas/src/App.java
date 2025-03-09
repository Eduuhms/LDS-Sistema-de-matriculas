import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("1 - Login");
            System.out.println("2 - Cadastrar aluno");
            System.out.println("3 - Cadastrar professor");
            System.out.println("4 - Cadastrar secretario(a)");
            System.out.println("5 - Cadastrar responsavel financeiro");
            System.out.println("6 - Cadastrar e listar cursos");
            System.out.println("7 - Manipular disciplinas"); 
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    // Secretaria secretaria = new Secretaria("Patricia", "patricia@gmail.com", "patricia");
                    // secretaria.gerarCurriculo("291266"); 
                    Login(scanner);
                    break;
                case 2:
                    cadastrarAluno(scanner);
                    break;
                case 3:
                    cadastrarProfessor(scanner);
                    break;
                case 4:
                    cadastrarSecretario(scanner);
                    break;
                case 5:
                    cadastrarResponsavelFinanceiro(scanner);
                    break;
                case 6:
                    manipularCursos(scanner);
                    break;
                case 7: 
                    manipularDisciplinas(scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);

        scanner.close();
    }

    private static List<String> lerCsv(String caminhoCsv){
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoCsv))) {
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
    private static void manipularDisciplinas(Scanner scanner) {
        System.out.println("1 - Cadastrar disciplina");
        System.out.println("2 - Matricular aluno em disciplina");
        System.out.println("3 - Verificar status da disciplina");
        System.out.println("4 - Listar alunos matriculados");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                cadastrarDisciplina(scanner);
                break;
            // case 2:
            //     matricularAluno(scanner);
            //     break;
            case 3:
                verificarStatusDisciplina(scanner);
                break;
            // case 4:
            //     listarAlunosMatriculados(scanner);
            //     break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void cadastrarDisciplina(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Nome da disciplina:");
        String nome = scanner.nextLine();
        System.out.println("Código da disciplina:");
        String codigo = scanner.nextLine();
        System.out.println("Créditos da disciplina:");
        int creditos = scanner.nextInt();
        System.out.println("É obrigatória? (true/false):");
        boolean ehObrigatoria = scanner.nextBoolean();
    
        Disciplina disciplina = new Disciplina(nome, codigo, creditos, ehObrigatoria);
    
        disciplina.salvarDisciplina();
    
        System.out.println("Disciplina cadastrada com sucesso!");
        System.out.println(disciplina);
    }

    // private static void matricularAluno(Scanner scanner) {
    //     scanner.nextLine(); 
    //     System.out.println("Código da disciplina:");
    //     String codigoDisciplina = scanner.nextLine();
    //     System.out.println("Nome do aluno:");
    //     String nomeAluno = scanner.nextLine();
    //     System.out.println("Email do aluno:");
    //     String emailAluno = scanner.nextLine();
    //     System.out.println("Senha do aluno:");
    //     String senhaAluno = scanner.nextLine();

    //     Disciplina disciplina = new Disciplina(codigoDisciplina);
    //     try {
    //         disciplina.preencherComDadosCsv(); 
    //     } catch (Exception e) {
    //         System.err.println("Erro ao carregar disciplina: " + e.getMessage());
    //         return;
    //     }

    //     Aluno aluno = new Aluno(nomeAluno, emailAluno, senhaAluno);
    //     disciplina.addAluno(aluno); 
    // }

    private static void verificarStatusDisciplina(Scanner scanner) {
        scanner.nextLine(); 
        System.out.println("Código da disciplina:");
        String codigoDisciplina = scanner.nextLine();

        Disciplina disciplina = new Disciplina(codigoDisciplina);
        try {
            disciplina.preencherComDadosCsv();
            System.out.println("Status da disciplina: " + disciplina.statusDisciplina());
        } catch (Exception e) {
            System.err.println("Erro ao carregar disciplina: " + e.getMessage());
        }
    }

    // private static void listarAlunosMatriculados(Scanner scanner) {
    //     scanner.nextLine(); 
    //     System.out.println("Código da disciplina:");
    //     String codigoDisciplina = scanner.nextLine();

    //     Disciplina disciplina = new Disciplina(codigoDisciplina);
    //     try {
    //         disciplina.preencherComDadosCsv(); 
    //         List<Aluno> alunos = disciplina.alunosMatriculados();
    //         if (alunos.isEmpty()) {
    //             System.out.println("Nenhum aluno matriculado na disciplina.");
    //         } else {
    //             System.out.println("Alunos matriculados na disciplina:");
    //             for (Aluno aluno : alunos) {
    //                 System.out.println(aluno.getNome());
    //             }
    //         }
    //     } catch (Exception e) {
    //         System.err.println("Erro ao carregar disciplina: " + e.getMessage());
    //     }
    // }

    private static void Login(Scanner scanner) {
        System.out.println("Email:");
        String email = scanner.next();
        System.out.println("Senha:");
        String senha = scanner.next();
        Usuario usuario = Usuario.entrar(email, senha);
        if (usuario != null) {
            usuario.setDados(true);
            System.out.println("Bem Vindo, " + usuario.getNome());

            switch(usuario.getTipo()){
                case PROFESSOR:
                    Professor professor = (Professor) usuario;
                    funcoesProfessor(professor, scanner);
                    break;
                case ALUNO: 
                    Aluno aluno = (Aluno) usuario;
                    funcoesAluno(aluno, scanner);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            
        } else {
            System.out.println("Email ou senha incorretos.");
        }
    }

    private static void funcoesAluno(Aluno aluno, Scanner scanner){
        int opcao;
        do { 
            System.out.println("1 - Se matricular em uma disciplina");
            System.out.println("2 - Se matricular em um curso");
            System.out.println("3 - Cancelar matrícula do curso");
            System.out.println("0 - Sair");
            opcao = scanner.nextInt();
            
            switch(opcao) {
                case 1:
                    try {
                        if (aluno.getCurso() == null){
                            throw new Exception("Se matricule em um curso antes de escolher as disciplinas!");
                        }
                        List<Disciplina> disciplinasCurso = aluno.getCurso().getDisciplinas();
                        System.out.println("Disciplinas disponíveis: ");
                        int numeroDisciplina = 0;
                        for (Disciplina disciplinaCurso : disciplinasCurso){
                            System.out.println(numeroDisciplina  + " - " + disciplinaCurso.getNome() + " (" + (disciplinaCurso.isEhObrigatoria()? "OBRIGATÓRIA" : "OPTATIVA") + ")");
                            numeroDisciplina ++;
                        }

                        System.out.println("Digite o número da discplina: ");
                        numeroDisciplina = scanner.nextInt();

                        Disciplina disciplinaEscolhida = disciplinasCurso.get(numeroDisciplina);
                        
                        aluno.matricularDisciplina(disciplinaEscolhida);
                        System.out.println("Matrícula realizada com sucesso!");
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                        System.out.println("");
                    }
                    break;

                case 2:
                    try {
                        if (aluno.getCurso() != null){
                            throw new Exception("Entre com um pedido de cancelamento da matrícula no curso para poder escolher outro!");
                        }

                        List<Curso> cursosDisponiveis = Curso.carregarCursos();
                        
                        System.out.println("Cursos disponíveis: ");
                        int numeroCurso = 0;
                        for (Curso cursoDisponivel : cursosDisponiveis){
                            System.out.println(numeroCurso  + " - " + cursoDisponivel.getNome());
                            numeroCurso ++;
                        }

                        System.out.println("Digite o número do curso: ");
                        numeroCurso = scanner.nextInt();
                        Curso cursoEscolhido = cursosDisponiveis.get(numeroCurso);
                        aluno.setCurso(cursoEscolhido);
                        System.out.println("Matrícula realizada com sucesso!.");
                    
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        System.out.println("");
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            
        } while (opcao != 0);
    }

    private static void funcoesProfessor(Professor professor, Scanner scanner){
        int opcao;
        do { 
            System.out.println("1 - Pesquisar alunos de uma disciplina");
            System.out.println("0 - Sair");
            opcao = scanner.nextInt();
            
            switch(opcao) {
                case 1:
                    System.out.println("Digite o nome da disciplina:");
                    String nomeDisciplina;
                    nomeDisciplina = scanner.nextLine();
                    nomeDisciplina = scanner.nextLine();
                    Disciplina disciplinaComparacao = new Disciplina("0", nomeDisciplina);
                    List<Aluno> alunos = professor.visualizarAlunos(disciplinaComparacao);
                    System.out.println(alunos);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            
        } while (opcao != 0);
    }




    private static void cadastrarAluno(Scanner scanner) {
        String[] dados = lerDadosUsuario(scanner);
        Aluno aluno = new Aluno(dados[0], dados[1], dados[2]);
        aluno.cadastrar();
    }

    private static void cadastrarProfessor(Scanner scanner) {
        String[] dados = lerDadosUsuario(scanner);
        String codigo;
        System.out.println("Quantidade disciplinas: ");
        List<Disciplina> disciplinas = new ArrayList<>();
        int qnt = scanner.nextInt();
        while (qnt != 0) {
            codigo = scanner.next();
            Disciplina disciplina = new Disciplina(codigo);
            try {
                disciplina.preencherComDadosCsv();
                disciplinas.add(disciplina);
                qnt--;
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        Professor professor = new Professor(dados[0], dados[1], dados[2], disciplinas);
        professor.cadastrar();
    }

    private static void cadastrarSecretario(Scanner scanner) {
        String[] dados = lerDadosUsuario(scanner);
        Secretaria secretario = new Secretaria(dados[0], dados[1], dados[2]);
        secretario.cadastrar();
    }

    private static void cadastrarResponsavelFinanceiro(Scanner scanner) {
        String[] dados = lerDadosUsuario(scanner);
        ResponsavelFinanceiro responsavel = new ResponsavelFinanceiro(dados[0], dados[1], dados[2]);
        responsavel.cadastrar();
    }

    private static String[] lerDadosUsuario(Scanner scanner) {
        System.out.println("Nome:");
        String nome = scanner.next();
        System.out.println("Email:");
        String email = scanner.next();
        System.out.println("Senha:");
        String senha = scanner.next();
        return new String[]{nome, email, senha};
    }

    private static void manipularCursos(Scanner scanner) {
        System.out.println("1 - Cadastrar curso");
        System.out.println("2 - Listar cursos");
        System.out.println("3 - Incluir disciplina em um curso");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                cadastrarCurso(scanner);
                break;
            case 2:
                listarCursos();
                break;
            case 3:
            try {
                List<Curso> cursosDisponiveis = Curso.carregarCursos();
                System.out.println("Cursos existentes: ");
                int numeroCurso = 0;
                for (Curso cursoDisponivel : cursosDisponiveis){
                    System.out.println(numeroCurso  + " - " + cursoDisponivel.getNome());
                    numeroCurso ++;
                }

                System.out.println("Digite o número do curso: ");
                numeroCurso = scanner.nextInt();
                Curso cursoEscolhido = cursosDisponiveis.get(numeroCurso);
                cursoEscolhido.setDados(true);
                System.out.println("Disciplinas existentes: ");
                List<Disciplina> disciplinasDisponiveis = Disciplina.carregarDisciplinas();
                int numeroDisciplina = 0;
                for (Disciplina disciplinaDisponivel : disciplinasDisponiveis){
                    System.out.println(numeroDisciplina  + " - " + disciplinaDisponivel.getNome());
                    numeroDisciplina ++;
                }
                System.out.println("Digite o número da disciplina ");
                numeroDisciplina = scanner.nextInt();
                Disciplina disciplinaEscolhida = disciplinasDisponiveis.get(numeroDisciplina);
                disciplinaEscolhida.preencherComDadosCsv();
                cursoEscolhido.adicionarDisciplina(disciplinaEscolhida);
                System.out.println("Disciplina cadastrada no curso com sucesso!");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
                

                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void cadastrarCurso(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Nome do curso:");
        String nome = scanner.nextLine();
        System.out.println("Créditos do curso:");
        int creditos = scanner.nextInt();

        Curso curso = new Curso(nome, creditos);
        curso.salvarCurso();
        System.out.println("Curso cadastrado com sucesso!");
    }

    private static void listarCursos() {
        List<Curso> cursos = Curso.carregarCursos();
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        } else {
            System.out.println("Cursos cadastrados:");
            for (Curso curso : cursos) {
                System.out.println(curso);
            }
        }
    }
}