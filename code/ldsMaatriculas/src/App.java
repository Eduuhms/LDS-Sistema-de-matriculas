import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        String nome;
        String email;
        String senha;
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("1 - Login");
            System.out.println("2 - Cadastrar aluno");
            System.out.println("3 - Cadastrar professor");
            System.out.println("4 - Cadastrar secretario(a)");
            System.out.println("5 - Cadastrar responsavel financeiro");
            System.out.println("6 - Cadastrar e listar cursos"); // Novo caso
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
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
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);

        scanner.close();
    }

    private static void Login(Scanner scanner) {
        System.out.println("Email:");
        String email = scanner.next();
        System.out.println("Senha:");
        String senha = scanner.next();
        Usuario usuario = Usuario.entrar(email, senha);
        if (usuario != null) {
            System.out.println("Bem Vindo, " + usuario.getNome());
        } else {
            System.out.println("Email ou senha incorretos.");
        }
    }

    private static void cadastrarAluno(Scanner scanner) {
        String[] dados = lerDadosUsuario(scanner);
        Aluno aluno = new Aluno(dados[0], dados[1], dados[2]);
        aluno.cadastrar();
    }

    private static void cadastrarProfessor(Scanner scanner) {
        String[] dados = lerDadosUsuario(scanner);
        Professor professor = new Professor(dados[0], dados[1], dados[2]);
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

    // Novo método para manipular cursos
    private static void manipularCursos(Scanner scanner) {
        System.out.println("1 - Cadastrar curso");
        System.out.println("2 - Listar cursos");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                cadastrarCurso(scanner);
                break;
            case 2:
                listarCursos();
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Método para cadastrar um novo curso
    private static void cadastrarCurso(Scanner scanner) {
        System.out.println("ID do curso:");
        int idCurso = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha
        System.out.println("Nome do curso:");
        String nome = scanner.nextLine();
        System.out.println("Créditos do curso:");
        int creditos = scanner.nextInt();

        Curso curso = new Curso(idCurso, nome, creditos);
        curso.salvarCurso();
        System.out.println("Curso cadastrado com sucesso!");
    }

    // Método para listar todos os cursos cadastrados
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