import java.io.FileWriter;
import java.io.IOException;

public class Usuario {
    private static int proximoId = 1; // Variável estática para controlar o próximo ID

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

    public void entrar() {
        // Implementação do login
    }

    public void sair() {
        // Implementação do logout
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }
}