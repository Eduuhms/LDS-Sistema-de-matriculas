import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Usuario {
    private static int proximoId = 1; 

    protected int id;
    protected String nome;
    protected String email;
    protected String senha;
    protected final TipoUsuario tipo;

    public Usuario(String nome, String email, String senha, TipoUsuario tipo) {
        this.id = proximoId++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public void cadastrar() {
        try (FileWriter writer = new FileWriter("code\\ldsMaatriculas\\src\\csv\\usuarios.csv", true)) {
            writer.append(String.valueOf(id)).append(",")
                  .append(nome).append(",")
                  .append(email).append(",")
                  .append(senha).append(",")
                  .append(tipo.name()).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public static Usuario entrar(String email, String senha) {
        try (BufferedReader reader = new BufferedReader(new FileReader("code\\ldsMaatriculas\\src\\csv\\usuarios.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados[2].equals(email) && dados[3].equals(senha)) {

                    int id = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    TipoUsuario tipo = TipoUsuario.valueOf(dados[4]);

                    switch (tipo) {
                        case ALUNO:
                            return new Aluno(nome, email, senha, "", null); 
                        case PROFESSOR:
                            return new Professor(nome, email, senha);
                        case SECRETARIA:
                            return new Secretaria(nome, email, senha);
                        default:
                            throw new IllegalArgumentException("Tipo de usuário desconhecido: " + tipo);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; 
    }

    public void sair() {
        System.out.println("Usuário " + nome + " saiu do sistema.");
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }
}