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
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    Usuario usuario = Usuario.entrar("gglage11@gmail.com", "123");
                    System.out.println("Usuário " + usuario.getNome() + " entrou no sistema.");
                break;
                case 2:
                String nome = scanner.next();
                String email = scanner.next();
                String senha = scanner.next();
                    Aluno aluno = new Aluno(nome, email, senha);
                    aluno.cadastrar();
                break;
                case 3:
                    Professor professor = new Professor("Cleiton", "cleiton@gmail.com", "123");
                    professor.cadastrar();
                break;
                case 4:
                    Secretaria secretario = new Secretaria("Sinval", "sinval@gmail.com", "123");
                    secretario.cadastrar();
                    break;
                case 5:
                   ResponsavelFinanceiro responsavel = new ResponsavelFinanceiro("Gabito", "gglage11@gmail.com", "123");
                   responsavel.cadastrar();
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
    
}
